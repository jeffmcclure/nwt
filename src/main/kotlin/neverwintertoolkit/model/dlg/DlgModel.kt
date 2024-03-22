package neverwintertoolkit.model.dlg

// Generated 2024-02-01T15:21:55.550017
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import neverwintertoolkit.model.annotation.BlankBehavior
import neverwintertoolkit.model.annotation.NwnField
import java.io.OutputStream

class Dlg : GffObj {
    companion object {
        val factory: GffFactory<Dlg> = GenericGffFactory(Dlg::class.java, ".dlg")
    }

    override fun writeGff(output: OutputStream) = GenericGffWriter(this, ".dlg").writeGff(output)

    @get:NwnField(name = "EntryList", type = "List", indexedStructId = true, gffOrder = 7)
    @get:JsonProperty("EntryList")
    @set:JsonProperty("EntryList")
    var entryList: List<DlgEntry>? = null

    @get:NwnField(name = "NumWords", type = "DWORD", gffOrder = 3)
    @get:JsonProperty("NumWords")
    @set:JsonProperty("NumWords")
    var numWords: UInt? = null

    @get:NwnField(name = "EndConversation", type = "ResRef", gffOrder = 4)
    @get:JsonProperty("EndConversation")
    @set:JsonProperty("EndConversation")
    var endConversation: String? = null

    @get:NwnField(name = "EndConverAbort", type = "ResRef", gffOrder = 5)
    @get:JsonProperty("EndConverAbort")
    @set:JsonProperty("EndConverAbort")
    var endConverAbort: String? = null

    @get:NwnField(name = "DelayReply", type = "DWORD", gffOrder = 2)
    @get:JsonProperty("DelayReply")
    @set:JsonProperty("DelayReply")
    var delayReply: UInt? = null

    @get:NwnField(name = "DelayEntry", type = "DWORD", gffOrder = 1)
    @get:JsonProperty("DelayEntry")
    @set:JsonProperty("DelayEntry")
    var delayEntry: UInt? = null

    @get:NwnField(name = "StartingList", type = "List", indexedStructId = true, gffOrder = 9)
    @get:JsonProperty("StartingList")
    @set:JsonProperty("StartingList")
    var startingList: List<DlgStarting>? = null

    @get:NwnField(name = "ReplyList", type = "List", indexedStructId = true, gffOrder = 8)
    @get:JsonProperty("ReplyList")
    @set:JsonProperty("ReplyList")
    var replyList: List<DlgReply>? = null

    @get:NwnField(name = "PreventZoomIn", type = "BYTE", gffOrder = 6)
    @get:JsonProperty("PreventZoomIn")
    @set:JsonProperty("PreventZoomIn")
    var preventZoomIn: UByte? = null
}

class DlgActionParams {
}

class DlgConditionParams {
    @get:NwnField(name = "Key", type = "CExoString")
    @get:JsonProperty("Key")
    @set:JsonProperty("Key")
    var key: String? = null

    @get:NwnField(name = "Value", type = "CExoString")
    @get:JsonProperty("Value")
    @set:JsonProperty("Value")
    var value: String? = null
}

class DlgEntry {

    @get:NwnField(name = "RepliesList", type = "List", indexedStructId = true, gffOrder = 11)
    @get:JsonProperty("RepliesList")
    @set:JsonProperty("RepliesList")
    var repliesList: List<DlgFoo>? = null

    @get:NwnField(name = "Delay", type = "DWORD", gffOrder = 7)
    @get:JsonProperty("Delay")
    @set:JsonProperty("Delay")
    var delay: UInt? = null

    @get:NwnField(name = "Sound", type = "ResRef", blankBehavior = BlankBehavior.GENERATE, gffOrder = 9)
    @get:JsonProperty("Sound")
    @set:JsonProperty("Sound")
    var sound: String? = null

    @get:NwnField(name = "Speaker", type = "CExoString", blankBehavior = BlankBehavior.GENERATE, gffOrder = 1)
    @get:JsonProperty("Speaker")
    @set:JsonProperty("Speaker")
    var speaker: String? = null

    @get:NwnField(name = "Comment", type = "CExoString", blankBehavior = BlankBehavior.GENERATE, gffOrder = 8)
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "AnimLoop", type = "BYTE", gffOrder = 3)
    @get:JsonProperty("AnimLoop")
    @set:JsonProperty("AnimLoop")
    var animLoop: UByte? = null

    @get:NwnField(name = "Text", type = "CExoLocString", gffOrder = 4)
    @get:JsonProperty("Text")
    @set:JsonProperty("Text")
    var text: CExoLocString? = null

    @get:NwnField(name = "Script", type = "ResRef", gffOrder = 5, blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("Script")
    @set:JsonProperty("Script")
    var script: String? = null

    @get:NwnField(name = "Quest", type = "CExoString", blankBehavior = BlankBehavior.GENERATE, gffOrder = 10)
    @get:JsonProperty("Quest")
    @set:JsonProperty("Quest")
    var quest: String? = null

    @get:NwnField(name = "Animation", type = "DWORD", gffOrder = 2)
    @get:JsonProperty("Animation")
    @set:JsonProperty("Animation")
    var animation: UInt? = null

    @get:NwnField(name = "QuestEntry", type = "DWORD")
    @get:JsonProperty("QuestEntry")
    @set:JsonProperty("QuestEntry")
    var questEntry: UInt? = null

    @get:NwnField(name = "ActionParams", type = "List", indexedStructId = true, gffOrder = 6)
    @get:JsonProperty("ActionParams")
    @set:JsonProperty("ActionParams")
    var actionParams: List<DlgActionParams>? = null
}

class DlgFoo {

    @get:NwnField(name = "IsChild", type = "BYTE", gffOrder = 4)
    @get:JsonProperty("IsChild")
    @set:JsonProperty("IsChild")
    var isChild: Boolean = false

    @get:NwnField(name = "Active", type = "ResRef", gffOrder = 2, blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("Active")
    @set:JsonProperty("Active")
    var active: String? = null

    @get:NwnField(name = "LinkComment", type = "CExoString", blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("LinkComment")
    @set:JsonProperty("LinkComment")
    var linkComment: String? = null

    @get:NwnField(name = "Index", type = "DWORD", gffOrder = 1)
    @get:JsonProperty("Index")
    @set:JsonProperty("Index")
    var index: UInt? = null

    @get:NwnField(name = "ConditionParams", type = "List", indexedStructId = true, gffOrder = 3, blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("ConditionParams")
    @set:JsonProperty("ConditionParams")
    var conditionParams: List<DlgConditionParams>? = null

    fun clone(isChild: Boolean = this.isChild): DlgFoo {
        return DlgFoo().also { it ->
            it.isChild = isChild
            it.active = this.active
            it.linkComment = this.linkComment
            it.index = this.index
            it.conditionParams = this.conditionParams
        }
    }
}

class DlgReply {

    fun removeDefaultValues(): DlgReply {
        if (delay == 4294967295u) delay = null
        if (animation == 0u) animation = null
        if (animLoop == 1u.toUByte()) animLoop = null
        return this
    }

    fun populateDefaultValues(): DlgReply {
        if (delay == null) delay = 4294967295u
        if (animation == null) animation = 0u
        if (animLoop == null) animLoop = 1u.toUByte()
        return this
    }

    fun clone(): DlgReply {
        val that = this
        return DlgReply().also {
            it.sound = that.sound
            it.comment = that.comment
            it.entriesList = that.entriesList
            it.animation = that.animation
            it.delay = that.delay
            it.script = that.script
            it.text = that.text
            it.animLoop = that.animLoop
            it.quest = that.quest
            it.questEntry = that.questEntry
            it.actionParams = that.actionParams
        }
    }


    @get:NwnField(name = "Sound", type = "ResRef", gffOrder = 8, blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("Sound")
    @set:JsonProperty("Sound")
    var sound: String? = null

    @get:NwnField(name = "Comment", type = "CExoString", gffOrder = 7, blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "EntriesList", type = "List", indexedStructId = true, gffOrder = 10, blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("EntriesList")
    @set:JsonProperty("EntriesList")
    var entriesList: List<DlgFoo>? = null

    @get:NwnField(name = "Animation", type = "DWORD", gffOrder = 1)
    @get:JsonProperty("Animation")
    @set:JsonProperty("Animation")
    var animation: UInt? = null

    @get:NwnField(name = "Delay", type = "DWORD", gffOrder = 6)
    @get:JsonProperty("Delay")
    @set:JsonProperty("Delay")
    var delay: UInt? = null

    @get:NwnField(name = "Script", type = "ResRef", blankBehavior = BlankBehavior.GENERATE, gffOrder = 4)
    @get:JsonProperty("Script")
    @set:JsonProperty("Script")
    var script: String? = null

    @get:NwnField(name = "Text", type = "CExoLocString", gffOrder = 3, blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("Text")
    @set:JsonProperty("Text")
    var text: CExoLocString? = null

    @get:NwnField(name = "AnimLoop", type = "BYTE", gffOrder = 2)
    @get:JsonProperty("AnimLoop")
    @set:JsonProperty("AnimLoop")
    var animLoop: UByte? = null

    @get:NwnField(name = "Quest", type = "CExoString", gffOrder = 9, blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("Quest")
    @set:JsonProperty("Quest")
    var quest: String? = null

    @get:NwnField(name = "QuestEntry", type = "DWORD", blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("QuestEntry")
    @set:JsonProperty("QuestEntry")
    var questEntry: UInt? = null

    @get:NwnField(name = "ActionParams", type = "List", indexedStructId = true, gffOrder = 5)
    @get:JsonProperty("ActionParams")
    @set:JsonProperty("ActionParams")
    var actionParams: List<DlgActionParams>? = null
}

class DlgStarting {

    @get:NwnField(name = "Active", type = "ResRef", gffOrder = 2, blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("Active")
    @set:JsonProperty("Active")
    var active: String? = null

    @get:NwnField(name = "Index", type = "DWORD", gffOrder = 1)
    @get:JsonProperty("Index")
    @set:JsonProperty("Index")
    var index: UInt? = null

    @get:NwnField(name = "ConditionParams", type = "List", indexedStructId = true, gffOrder = 3, blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("ConditionParams")
    @set:JsonProperty("ConditionParams")
    var conditionParams: List<DlgConditionParams>? = null
}
