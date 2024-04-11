package neverwintertoolkit.model.utp

// Generated 2024-01-31T19:08:10.150181
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.model.annotation.NwnField
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import java.io.OutputStream

class Utp : GffObj {
    companion object {
        val factory: GffFactory<Utp> = GenericGffFactory(Utp::class.java, ".utp")
    }
    override fun writeGff(output: OutputStream) {
        GenericGffWriter(this, ".utp").writeGff(output)
    }

    @get:NwnField(name = "TrapDetectable", type = "BYTE")
    @get:JsonProperty("TrapDetectable")
    @set:JsonProperty("TrapDetectable")
    var trapDetectable: UByte? = null

    @get:NwnField(name = "OnUsed", type = "ResRef")
    @get:JsonProperty("OnUsed")
    @set:JsonProperty("OnUsed")
    var onUsed: String? = null

    @get:NwnField(name = "OnDeath", type = "ResRef")
    @get:JsonProperty("OnDeath")
    @set:JsonProperty("OnDeath")
    var onDeath: String? = null

    @get:NwnField(name = "Will", type = "BYTE")
    @get:JsonProperty("Will")
    @set:JsonProperty("Will")
    var will: UByte? = null

    @get:NwnField(name = "Ref", type = "BYTE")
    @get:JsonProperty("Ref")
    @set:JsonProperty("Ref")
    var ref: UByte? = null

    @get:NwnField(name = "Hardness", type = "BYTE")
    @get:JsonProperty("Hardness")
    @set:JsonProperty("Hardness")
    var hardness: UByte? = null

    @get:NwnField(name = "Lockable", type = "BYTE")
    @get:JsonProperty("Lockable")
    @set:JsonProperty("Lockable")
    var lockable: UByte? = null

    @get:NwnField(name = "Description", type = "CExoLocString")
    @get:JsonProperty("Description")
    @set:JsonProperty("Description")
    var description: CExoLocString? = null

    @get:NwnField(name = "OnInvDisturbed", type = "ResRef")
    @get:JsonProperty("OnInvDisturbed")
    @set:JsonProperty("OnInvDisturbed")
    var onInvDisturbed: String? = null

    @get:NwnField(name = "TrapDetectDC", type = "BYTE")
    @get:JsonProperty("TrapDetectDC")
    @set:JsonProperty("TrapDetectDC")
    var trapDetectDC: UByte? = null

    @get:NwnField(name = "OpenLockDC", type = "BYTE")
    @get:JsonProperty("OpenLockDC")
    @set:JsonProperty("OpenLockDC")
    var openLockDC: UByte? = null

    @get:NwnField(name = "Faction", type = "DWORD")
    @get:JsonProperty("Faction")
    @set:JsonProperty("Faction")
    var faction: UInt? = null

    @get:NwnField(name = "KeyRequired", type = "BYTE")
    @get:JsonProperty("KeyRequired")
    @set:JsonProperty("KeyRequired")
    var keyRequired: UByte? = null

    @get:NwnField(name = "Appearance", type = "DWORD")
    @get:JsonProperty("Appearance")
    @set:JsonProperty("Appearance")
    var appearance: UInt? = null

    @get:NwnField(name = "Locked", type = "BYTE")
    @get:JsonProperty("Locked")
    @set:JsonProperty("Locked")
    var locked: UByte? = null

    @get:NwnField(name = "Interruptable", type = "BYTE")
    @get:JsonProperty("Interruptable")
    @set:JsonProperty("Interruptable")
    var interruptable: UByte? = null

    @get:NwnField(name = "DisarmDC", type = "BYTE")
    @get:JsonProperty("DisarmDC")
    @set:JsonProperty("DisarmDC")
    var disarmDC: UByte? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "OnOpen", type = "ResRef")
    @get:JsonProperty("OnOpen")
    @set:JsonProperty("OnOpen")
    var onOpen: String? = null

    @get:NwnField(name = "HP", type = "SHORT")
    @get:JsonProperty("HP")
    @set:JsonProperty("HP")
    var hP: Short? = null

    @get:NwnField(name = "AutoRemoveKey", type = "BYTE")
    @get:JsonProperty("AutoRemoveKey")
    @set:JsonProperty("AutoRemoveKey")
    var autoRemoveKey: UByte? = null

    @get:NwnField(name = "HasInventory", type = "BYTE")
    @get:JsonProperty("HasInventory")
    @set:JsonProperty("HasInventory")
    var hasInventory: UByte? = null

    @get:NwnField(name = "OnDisarm", type = "ResRef")
    @get:JsonProperty("OnDisarm")
    @set:JsonProperty("OnDisarm")
    var onDisarm: String? = null

    @get:NwnField(name = "TrapFlag", type = "BYTE")
    @get:JsonProperty("TrapFlag")
    @set:JsonProperty("TrapFlag")
    var trapFlag: UByte? = null

    @get:NwnField(name = "Conversation", type = "ResRef")
    @get:JsonProperty("Conversation")
    @set:JsonProperty("Conversation")
    var conversation: String? = null

    @get:NwnField(name = "TrapOneShot", type = "BYTE")
    @get:JsonProperty("TrapOneShot")
    @set:JsonProperty("TrapOneShot")
    var trapOneShot: UByte? = null

    @get:NwnField(name = "Plot", type = "BYTE")
    @get:JsonProperty("Plot")
    @set:JsonProperty("Plot")
    var plot: UByte? = null

    @get:NwnField(name = "TrapType", type = "BYTE")
    @get:JsonProperty("TrapType")
    @set:JsonProperty("TrapType")
    var trapType: UByte? = null

    @get:NwnField(name = "Type", type = "BYTE")
    @get:JsonProperty("Type")
    @set:JsonProperty("Type")
    var type: UByte? = null

    @get:NwnField(name = "OnClosed", type = "ResRef")
    @get:JsonProperty("OnClosed")
    @set:JsonProperty("OnClosed")
    var onClosed: String? = null

    @get:NwnField(name = "OnLock", type = "ResRef")
    @get:JsonProperty("OnLock")
    @set:JsonProperty("OnLock")
    var onLock: String? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "OnUnlock", type = "ResRef")
    @get:JsonProperty("OnUnlock")
    @set:JsonProperty("OnUnlock")
    var onUnlock: String? = null

    @get:NwnField(name = "OnTrapTriggered", type = "ResRef")
    @get:JsonProperty("OnTrapTriggered")
    @set:JsonProperty("OnTrapTriggered")
    var onTrapTriggered: String? = null

    @get:NwnField(name = "AnimationState", type = "BYTE")
    @get:JsonProperty("AnimationState")
    @set:JsonProperty("AnimationState")
    var animationState: UByte? = null

    @get:NwnField(name = "OnDamaged", type = "ResRef")
    @get:JsonProperty("OnDamaged")
    @set:JsonProperty("OnDamaged")
    var onDamaged: String? = null

    @get:NwnField(name = "CurrentHP", type = "SHORT")
    @get:JsonProperty("CurrentHP")
    @set:JsonProperty("CurrentHP")
    var currentHP: Short? = null

    @get:NwnField(name = "Static", type = "BYTE")
    @get:JsonProperty("Static")
    @set:JsonProperty("Static")
    var static: UByte? = null

    @get:NwnField(name = "OnSpellCastAt", type = "ResRef")
    @get:JsonProperty("OnSpellCastAt")
    @set:JsonProperty("OnSpellCastAt")
    var onSpellCastAt: String? = null

    @get:NwnField(name = "OnUserDefined", type = "ResRef")
    @get:JsonProperty("OnUserDefined")
    @set:JsonProperty("OnUserDefined")
    var onUserDefined: String? = null

    @get:NwnField(name = "Fort", type = "BYTE")
    @get:JsonProperty("Fort")
    @set:JsonProperty("Fort")
    var fort: UByte? = null

    @get:NwnField(name = "BodyBag", type = "BYTE")
    @get:JsonProperty("BodyBag")
    @set:JsonProperty("BodyBag")
    var bodyBag: UByte? = null

    @get:NwnField(name = "OnHeartbeat", type = "ResRef")
    @get:JsonProperty("OnHeartbeat")
    @set:JsonProperty("OnHeartbeat")
    var onHeartbeat: String? = null

    @get:NwnField(name = "LocName", type = "CExoLocString")
    @get:JsonProperty("LocName")
    @set:JsonProperty("LocName")
    var locName: CExoLocString? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "PortraitId", type = "WORD")
    @get:JsonProperty("PortraitId")
    @set:JsonProperty("PortraitId")
    var portraitId: UShort? = null

    @get:NwnField(name = "PaletteID", type = "BYTE")
    @get:JsonProperty("PaletteID")
    @set:JsonProperty("PaletteID")
    var paletteID: UByte? = null

    @get:NwnField(name = "Useable", type = "BYTE")
    @get:JsonProperty("Useable")
    @set:JsonProperty("Useable")
    var useable: UByte? = null

    @get:NwnField(name = "KeyName", type = "CExoString")
    @get:JsonProperty("KeyName")
    @set:JsonProperty("KeyName")
    var keyName: String? = null

    @get:NwnField(name = "OnMeleeAttacked", type = "ResRef")
    @get:JsonProperty("OnMeleeAttacked")
    @set:JsonProperty("OnMeleeAttacked")
    var onMeleeAttacked: String? = null

    @get:NwnField(name = "CloseLockDC", type = "BYTE")
    @get:JsonProperty("CloseLockDC")
    @set:JsonProperty("CloseLockDC")
    var closeLockDC: UByte? = null

    @get:NwnField(name = "TrapDisarmable", type = "BYTE")
    @get:JsonProperty("TrapDisarmable")
    @set:JsonProperty("TrapDisarmable")
    var trapDisarmable: UByte? = null

    @get:NwnField(name = "OnClick", type = "ResRef")
    @get:JsonProperty("OnClick")
    @set:JsonProperty("OnClick")
    var onClick: String? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<UtpVarTable>? = null

    @get:NwnField(name = "ItemList", type = "List", indexedStructId = true)
    @get:JsonProperty("ItemList")
    @set:JsonProperty("ItemList")
    var itemList: List<UtpItem>? = null

    @get:NwnField(name = "Portrait", type = "ResRef")
    @get:JsonProperty("Portrait")
    @set:JsonProperty("Portrait")
    var portrait: String? = null
}

class UtpItem {

    @get:NwnField(name = "Repos_Posy", type = "WORD")
    @get:JsonProperty("ReposPosy")
    @set:JsonProperty("ReposPosy")
    var reposPosy: UShort? = null

    @get:NwnField(name = "Repos_PosX", type = "WORD")
    @get:JsonProperty("ReposPosX")
    @set:JsonProperty("ReposPosX")
    var reposPosX: UShort? = null

    @get:NwnField(name = "InventoryRes", type = "ResRef")
    @get:JsonProperty("InventoryRes")
    @set:JsonProperty("InventoryRes")
    var inventoryRes: String? = null
}

class UtpVarTable {

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
