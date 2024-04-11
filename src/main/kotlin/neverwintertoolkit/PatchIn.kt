package neverwintertoolkit

import neverwintertoolkit.model.dlg.Dlg
import neverwintertoolkit.model.dlg.DlgFoo
import neverwintertoolkit.model.dlg.DlgReply
import neverwintertoolkit.model.dlg.DlgsNpcResponse
import neverwintertoolkit.model.dlg.DlgsPcChoice

fun patchIn(firstx: DlgReply, userChoiceList: MutableList<DlgsPcChoice>, adria: Dlg, obj: DlgsNpcResponse) {

    // look for top-level starting entry nodes
    val index0 = adria.startingList!!.firstOrNull {
        (it.active ?: "") == (obj.appearIfScript ?: "") && // allow blank to match null
                adria.entryList!![it.index!!.toInt()].text?.strings?.first()?.string == obj.npcSays
    }?.index?.toInt()

    val index = index0 ?: adria.replyList!!.firstNotNullOfOrNull {
        it.entriesList?.firstOrNull { dlgFoo: DlgFoo ->
            (dlgFoo.active ?: "") == (obj.appearIfScript ?: "") && // allow blank to match null
                    adria.entryList!![dlgFoo.index!!.toInt()].text?.strings?.first()?.string == obj.npcSays
        }
    }?.index?.toInt() ?: throw RuntimeException("Could not find NPC starting statement '${obj.npcSays}' with active='${obj.appearIfScript}'")

    val foo = DlgFoo()
    foo.index = firstx.replyIndex!!.toUInt()
    val userChoice = userChoiceList.firstOrNull { it.index == firstx.replyIndex!! }
    foo.active = userChoice?.appearIfScript

    val foo2 = adria.entryList!![index]
    foo2.repliesList = foo2.repliesList!! + listOf(foo)
}