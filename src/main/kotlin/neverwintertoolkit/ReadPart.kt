package neverwintertoolkit

import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.model.dlg.Dlg
import neverwintertoolkit.model.dlg.DlgEntry
import neverwintertoolkit.model.dlg.DlgFoo
import neverwintertoolkit.model.dlg.DlgReply
import neverwintertoolkit.model.dlg.Dlgs
import neverwintertoolkit.model.dlg.DlgsNpcResponse
import neverwintertoolkit.model.dlg.DlgsPcChoice
import java.net.URL
import java.util.ArrayList

class ReadPart {

    fun readPart(adria: Dlg, vararg resList: URL) {
//        println("readPart " + resList.map { it.toString() })
        val mapper = getBaseMapper()

        val npc = mutableListOf<DlgsNpcResponse>()
        val pc = mutableListOf<DlgsPcChoice>()

        var entryNum = adria.entryList!!.size
        var replyNum = adria.replyList!!.size

        fun flatten(obj: DlgsNpcResponse) {
            npc.add(obj)
            obj.userChoices?.let { userList: ArrayList<DlgsPcChoice> ->
                userList.forEach { aUser ->
                    pc.add(aUser)
                    aUser.index = replyNum++
                }
                userList.forEach { user ->
                    user.response?.forEach { response: DlgsNpcResponse ->
                        response.index = entryNum++
                        flatten(response)
                    }
                }
            }
        }

        val rootList = resList.map { res ->
            val root = mapper.readValue(res, Dlgs::class.java)
            // find root in dlg we are merging into
            val foo2: DlgEntry = adria.entryList!!.firstOrNull { it1 ->
                if (it1.text?.strings?.first()?.string == root.npcSays) {
                    val index = adria.entryList!!.indexOf(it1).toUInt()
                    if (adria.startingList?.any { it2 -> it2.index == index && it2.active == root.appearIfScript } == true) {
                        true
                    } else {
                        adria.replyList?.any { it3 -> it3.entriesList?.any { it4 -> it4.index == index } ?: false } ?: false
                    }
                } else {
                    false
                }
            } ?: throw RuntimeException("Could not find NPC starting statement '${root.npcSays}' with active='${root.appearIfScript}'")

            root.index = adria.entryList!!.indexOf(foo2)
//        flatten(root)
            root
        }
        rootList.forEach { flatten(it) }

        val npcEntryList: List<DlgEntry> = npc.toEntryList()
        val pcReplyList: List<DlgReply> = pc.toReplyList()

        // Link referenced nodes
        pcReplyList.filter { it.includeResponseId != null }.forEach { reply: DlgReply ->
            val one: DlgsNpcResponse? =
                npc.firstOrNull { it.responseId == reply.includeResponseId }
                    //?: throw RuntimeException("could not find responseid ${reply.includeResponseId}")


            val foo = DlgFoo()
            foo.isChild = true
            if (one != null) {
                foo.index = one.index?.toUInt()
                foo.active = one.appearIfScript
            } else {
                val xxx: DlgEntry? = adria.entryList?.firstOrNull { it.responseId == reply.includeResponseId }
//                xxx?.repliesList?.firstOrNull()?.index
                foo.index = xxx?.entryIndex?.toUInt()
//                foo.active = xxx?.active
            }


            reply.entriesList = listOf(foo)
        }

        adria.entryList = adria.entryList!! + npcEntryList.drop(1)
        adria.replyList = adria.replyList!! + pcReplyList

        var index = 0
        adria.entryList?.forEach { it.entryIndex = index++ }
        index = 0
        adria.replyList?.forEach { it.replyIndex = index++ }

        rootList.forEach { root ->
            root.userChoices?.forEachIndexed { index: Int, choice ->
//                println("patch in $index ${choice.index} / ${choice.userSays}")
                val one: DlgReply = pcReplyList.first { it.replyIndex == choice.index }
//                val one: DlgReply = pcReplyList[index]
                patchIn(one, pc, adria, root)
            }
        }
    }

    companion object {
        fun List<DlgsPcChoice>.toReplyList(): List<DlgReply> {
            return this.map { xxx: DlgsPcChoice ->
                DlgReply().also { e ->
                    e.text = CExoLocString(xxx.userSays!!)
                    e.script = xxx.onSelectScript
                    if (xxx.includeResponseId != null) {
                        e.includeResponseId = xxx.includeResponseId
                    } else {
                        e.entriesList = xxx.response?.map { response: DlgsNpcResponse ->
                            val foo = DlgFoo()
                            foo.active = response.appearIfScript
                            foo.index = response.index?.toUInt()
                            foo.isChild = false
                            foo
                        }
                    }
                }
            }
        }

        fun List<DlgsNpcResponse>.toEntryList(): List<DlgEntry> {
            return this.map { response ->
                DlgEntry().also { e ->
                    val str = CExoLocString(response.npcSays!!)
                    e.text = str
                    e.script = response.onAppearScript
                    e.responseId = response.responseId
                    e.repliesList = response.userChoices?.map { dlgUserChoice ->
                        val dlgFoo = DlgFoo()
                        dlgFoo.active = dlgUserChoice.appearIfScript
                        dlgFoo.index = dlgUserChoice.index?.toUInt()
                        dlgFoo.isChild = false
                        dlgFoo
                    }
                }
            }
        }
    }
}