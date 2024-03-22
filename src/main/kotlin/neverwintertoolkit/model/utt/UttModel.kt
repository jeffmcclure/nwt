package neverwintertoolkit.model.utt


// Generated 2024-01-31T19:17:23.418512
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import neverwintertoolkit.model.annotation.NwnField
import java.io.OutputStream

class Utt : GffObj {
    companion object {
        val factory: GffFactory<Utt> = GenericGffFactory(Utt::class.java, ".utt")
    }
    override fun writeGff(output: OutputStream) = GenericGffWriter(this, ".utt").writeGff(output)

    @get:NwnField(name = "Type", type = "INT")
    @get:JsonProperty("Type")
    @set:JsonProperty("Type")
    var type: Int? = null

    @get:NwnField(name = "DisarmDC", type = "BYTE")
    @get:JsonProperty("DisarmDC")
    @set:JsonProperty("DisarmDC")
    var disarmDC: UByte? = null

    @get:NwnField(name = "OnTrapTriggered", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnTrapTriggered")
    @set:JsonProperty("OnTrapTriggered")
    var onTrapTriggered: String? = null

    @get:NwnField(name = "TrapDetectDC", type = "BYTE")
    @get:JsonProperty("TrapDetectDC")
    @set:JsonProperty("TrapDetectDC")
    var trapDetectDC: UByte? = null

    @get:NwnField(name = "KeyName", type = "CExoString", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("KeyName")
    @set:JsonProperty("KeyName")
    var keyName: String? = null

    @get:NwnField(name = "OnClick", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnClick")
    @set:JsonProperty("OnClick")
    var onClick: String? = null

    @get:NwnField(name = "Faction", type = "DWORD")
    @get:JsonProperty("Faction")
    @set:JsonProperty("Faction")
    var faction: UInt? = null

    @get:NwnField(name = "TrapDisarmable", type = "BYTE")
    @get:JsonProperty("TrapDisarmable")
    @set:JsonProperty("TrapDisarmable")
    var trapDisarmable: UByte? = null

    @get:NwnField(name = "ScriptHeartbeat", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("ScriptHeartbeat")
    @set:JsonProperty("ScriptHeartbeat")
    var scriptHeartbeat: String? = null

    @get:NwnField(name = "TrapType", type = "BYTE")
    @get:JsonProperty("TrapType")
    @set:JsonProperty("TrapType")
    var trapType: UByte? = null

    @get:NwnField(name = "ScriptOnEnter", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("ScriptOnEnter")
    @set:JsonProperty("ScriptOnEnter")
    var scriptOnEnter: String? = null

    @get:NwnField(name = "ScriptOnExit", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("ScriptOnExit")
    @set:JsonProperty("ScriptOnExit")
    var scriptOnExit: String? = null

    @get:NwnField(name = "PortraitId", type = "WORD")
    @get:JsonProperty("PortraitId")
    @set:JsonProperty("PortraitId")
    var portraitId: UShort? = null

    @get:NwnField(name = "Cursor", type = "BYTE")
    @get:JsonProperty("Cursor")
    @set:JsonProperty("Cursor")
    var cursor: UByte? = null

    @get:NwnField(name = "AutoRemoveKey", type = "BYTE")
    @get:JsonProperty("AutoRemoveKey")
    @set:JsonProperty("AutoRemoveKey")
    var autoRemoveKey: UByte? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "HighlightHeight", type = "FLOAT")
    @get:JsonProperty("HighlightHeight")
    @set:JsonProperty("HighlightHeight")
    var highlightHeight: Float? = null

    @get:NwnField(name = "LinkedToFlags", type = "BYTE")
    @get:JsonProperty("LinkedToFlags")
    @set:JsonProperty("LinkedToFlags")
    var linkedToFlags: UByte? = null

    @get:NwnField(name = "TrapFlag", type = "BYTE")
    @get:JsonProperty("TrapFlag")
    @set:JsonProperty("TrapFlag")
    var trapFlag: UByte? = null

    @get:NwnField(name = "TrapDetectable", type = "BYTE")
    @get:JsonProperty("TrapDetectable")
    @set:JsonProperty("TrapDetectable")
    var trapDetectable: UByte? = null

    @get:NwnField(name = "LocalizedName", type = "CExoLocString")
    @get:JsonProperty("LocalizedName")
    @set:JsonProperty("LocalizedName")
    var localizedName: CExoLocString? = null

    @get:NwnField(name = "LinkedTo", type = "CExoString", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("LinkedTo")
    @set:JsonProperty("LinkedTo")
    var linkedTo: String? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "ScriptUserDefine", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("ScriptUserDefine")
    @set:JsonProperty("ScriptUserDefine")
    var scriptUserDefine: String? = null

    @get:NwnField(name = "PaletteID", type = "BYTE")
    @get:JsonProperty("PaletteID")
    @set:JsonProperty("PaletteID")
    var paletteID: UByte? = null

    @get:NwnField(name = "LoadScreenID", type = "WORD")
    @get:JsonProperty("LoadScreenID")
    @set:JsonProperty("LoadScreenID")
    var loadScreenID: UShort? = null

    @get:NwnField(name = "OnDisarm", type = "ResRef")
    @get:JsonProperty("OnDisarm")
    @set:JsonProperty("OnDisarm")
    var onDisarm: String? = null

    @get:NwnField(name = "TrapOneShot", type = "BYTE")
    @get:JsonProperty("TrapOneShot")
    @set:JsonProperty("TrapOneShot")
    var trapOneShot: UByte? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<UttVarTable>? = null
}

class UttVarTable {

    @get:NwnField(name = "Name", type = "CExoString")
    @get:JsonProperty("Name")
    @set:JsonProperty("Name")
    var name: String? = null

    @get:NwnField(name = "Type", type = "DWORD")
    @get:JsonProperty("Type")
    @set:JsonProperty("Type")
    var type: UInt? = null

    @get:NwnField(name = "Value", type = "")
    @get:JsonProperty("Value")
    @set:JsonProperty("Value")
    var value: Any? = null
}