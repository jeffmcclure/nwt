package neverwintertoolkit.model.ifo


// Generated 2024-01-27T18:41:41.344268
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import neverwintertoolkit.model.annotation.BlankBehavior
import neverwintertoolkit.model.annotation.NwnField
import java.io.OutputStream

//@JsonPropertyOrder(value = ["ModEntryArea", "ModEntryX", "ModEntryY", "ModEntryZ"])
class Ifo : GffObj {
    companion object {
        val factory: GffFactory<Ifo> = GenericGffFactory(Ifo::class.java, ".ifo")
    }

    override fun writeGff(output: OutputStream) = GenericGffWriter(this, ".ifo").writeGff(output)

    @get:NwnField(name = "Mod_OnPlrUnEqItm", type = "ResRef")
    @get:JsonProperty("ModOnPlrUnEqItm")
    @set:JsonProperty("ModOnPlrUnEqItm")
    var modOnPlrUnEqItm: String? = null

    @get:NwnField(name = "Mod_Description", type = "CExoLocString")
    @get:JsonProperty("ModDescription")
    @set:JsonProperty("ModDescription")
    var modDescription: CExoLocString? = null

    @get:NwnField(name = "Mod_OnHeartbeat", type = "ResRef", blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("ModOnHeartbeat")
    @set:JsonProperty("ModOnHeartbeat")
    var modOnHeartbeat: String? = null

    @get:NwnField(name = "Mod_StartHour", type = "BYTE")
    @get:JsonProperty("ModStartHour")
    @set:JsonProperty("ModStartHour")
    var modStartHour: UByte? = null

    @get:NwnField(name = "Mod_StartMonth", type = "BYTE")
    @get:JsonProperty("ModStartMonth")
    @set:JsonProperty("ModStartMonth")
    var modStartMonth: UByte? = null

    @get:NwnField(name = "Mod_OnPlrDying", type = "ResRef")
    @get:JsonProperty("ModOnPlrDying")
    @set:JsonProperty("ModOnPlrDying")
    var modOnPlrDying: String? = null

    @get:NwnField(name = "Mod_OnAcquirItem", type = "ResRef")
    @get:JsonProperty("ModOnAcquirItem")
    @set:JsonProperty("ModOnAcquirItem")
    var modOnAcquirItem: String? = null

    @get:NwnField(name = "Mod_CutSceneList", type = "List")
    @get:JsonProperty("ModCutSceneList")
    @set:JsonProperty("ModCutSceneList")
    var modCutSceneList: List<IfoModCutScene>? = null

    @get:NwnField(name = "Mod_Entry_Dir_X", type = "FLOAT")
    @get:JsonProperty("ModEntryDirX")
    @set:JsonProperty("ModEntryDirX")
    var modEntryDirX: Float? = null

    @get:NwnField(name = "Mod_Name", type = "CExoLocString")
    @get:JsonProperty("ModName")
    @set:JsonProperty("ModName")
    var modName: CExoLocString? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<IfoVarTable>? = null

    @get:NwnField(name = "Mod_OnModLoad", type = "ResRef")
    @get:JsonProperty("ModOnModLoad")
    @set:JsonProperty("ModOnModLoad")
    var modOnModLoad: String? = null

    @get:NwnField(name = "Mod_StartMovie", type = "ResRef")
    @get:JsonProperty("ModStartMovie")
    @set:JsonProperty("ModStartMovie")
    var modStartMovie: String? = null

    @get:NwnField(name = "Mod_OnUnAqreItem", type = "ResRef")
    @get:JsonProperty("ModOnUnAqreItem")
    @set:JsonProperty("ModOnUnAqreItem")
    var modOnUnAqreItem: String? = null

    @get:NwnField(name = "Mod_MinGameVer", type = "CExoString")
    @get:JsonProperty("ModMinGameVer")
    @set:JsonProperty("ModMinGameVer")
    var modMinGameVer: String? = null

    @get:NwnField(name = "Mod_OnPlrLvlUp", type = "ResRef")
    @get:JsonProperty("ModOnPlrLvlUp")
    @set:JsonProperty("ModOnPlrLvlUp")
    var modOnPlrLvlUp: String? = null

    @get:NwnField(name = "Mod_OnPlrRest", type = "ResRef")
    @get:JsonProperty("ModOnPlrRest")
    @set:JsonProperty("ModOnPlrRest")
    var modOnPlrRest: String? = null

    @get:NwnField(name = "Mod_OnModStart", type = "ResRef", blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("ModOnModStart")
    @set:JsonProperty("ModOnModStart")
    var modOnModStart: String? = null

    @get:NwnField(name = "Mod_Entry_Area", type = "ResRef")
    @get:JsonProperty("ModEntryArea")
    @set:JsonProperty("ModEntryArea")
    var modEntryArea: String? = null

    @get:NwnField(name = "Mod_Entry_Dir_Y", type = "FLOAT")
    @get:JsonProperty("ModEntryDirY")
    @set:JsonProperty("ModEntryDirY")
    var modEntryDirY: Float? = null

    @get:NwnField(name = "Expansion_Pack", type = "WORD")
    @get:JsonProperty("ExpansionPack")
    @set:JsonProperty("ExpansionPack")
    var expansionPack: UShort? = null

    @get:NwnField(name = "Mod_OnClientLeav", type = "ResRef")
    @get:JsonProperty("ModOnClientLeav")
    @set:JsonProperty("ModOnClientLeav")
    var modOnClientLeav: String? = null

    @get:NwnField(name = "Mod_Area_list", type = "List", structType = 6, elementName = "Area_Name", elementType = "ResRef")
    @get:JsonProperty("ModAreaList")
    @set:JsonProperty("ModAreaList")
    var modAreaList: List<String>? = null

    @get:NwnField(name = "Mod_GVar_List", type = "List")
    @get:JsonProperty("ModGVarList")
    @set:JsonProperty("ModGVarList")
    var modGVarList: List<IfoModGVar>? = null

    @get:NwnField(name = "Mod_DuskHour", type = "BYTE")
    @get:JsonProperty("ModDuskHour")
    @set:JsonProperty("ModDuskHour")
    var modDuskHour: UByte? = null

    @get:NwnField(name = "Mod_Tag", type = "CExoString")
    @get:JsonProperty("ModTag")
    @set:JsonProperty("ModTag")
    var modTag: String? = null

    @get:NwnField(name = "Mod_CustomTlk", type = "CExoString")
    @get:JsonProperty("ModCustomTlk")
    @set:JsonProperty("ModCustomTlk")
    var modCustomTlk: String? = null

    @get:NwnField(name = "Mod_OnCutsnAbort", type = "ResRef", blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("ModOnCutsnAbort")
    @set:JsonProperty("ModOnCutsnAbort")
    var modOnCutsnAbort: String? = null

    @get:NwnField(name = "Mod_MinPerHour", type = "BYTE")
    @get:JsonProperty("ModMinPerHour")
    @set:JsonProperty("ModMinPerHour")
    var modMinPerHour: UByte? = null

    @get:NwnField(name = "Mod_XPScale", type = "BYTE")
    @get:JsonProperty("ModXPScale")
    @set:JsonProperty("ModXPScale")
    var modXPScale: UByte? = null

    @get:NwnField(name = "Mod_OnPlrEqItm", type = "ResRef")
    @get:JsonProperty("ModOnPlrEqItm")
    @set:JsonProperty("ModOnPlrEqItm")
    var modOnPlrEqItm: String? = null

    @get:NwnField(name = "Mod_OnPlrDeath", type = "ResRef")
    @get:JsonProperty("ModOnPlrDeath")
    @set:JsonProperty("ModOnPlrDeath")
    var modOnPlrDeath: String? = null

    @get:NwnField(name = "Mod_Expan_List", type = "List")
    @get:JsonProperty("ModExpanList")
    @set:JsonProperty("ModExpanList")
    var modExpanList: List<IfoModExpan>? = null

    @get:NwnField(name = "Mod_StartDay", type = "BYTE")
    @get:JsonProperty("ModStartDay")
    @set:JsonProperty("ModStartDay")
    var modStartDay: UByte? = null

    @get:NwnField(name = "Mod_Entry_Z", type = "FLOAT")
    @get:JsonProperty("ModEntryZ")
    @set:JsonProperty("ModEntryZ")
    var modEntryZ: Float? = null

    @get:NwnField(name = "Mod_Entry_X", type = "FLOAT")
    @get:JsonProperty("ModEntryX")
    @set:JsonProperty("ModEntryX")
    var modEntryX: Float? = null

    @get:NwnField(name = "Mod_OnPlrChat", type = "ResRef", blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("ModOnPlrChat")
    @set:JsonProperty("ModOnPlrChat")
    var modOnPlrChat: String? = null

    @get:NwnField(name = "Mod_Version", type = "DWORD")
    @get:JsonProperty("ModVersion")
    @set:JsonProperty("ModVersion")
    var modVersion: UInt? = null

    @get:NwnField(name = "Mod_Entry_Y", type = "FLOAT")
    @get:JsonProperty("ModEntryY")
    @set:JsonProperty("ModEntryY")
    var modEntryY: Float? = null

    @get:NwnField(name = "Mod_OnActvtItem", type = "ResRef")
    @get:JsonProperty("ModOnActvtItem")
    @set:JsonProperty("ModOnActvtItem")
    var modOnActvtItem: String? = null

    @get:NwnField(name = "Mod_HakList", type = "List", structType = 8, elementName = "Mod_Hak", elementType = "CExoString")
    @get:JsonProperty("ModHakList")
    @set:JsonProperty("ModHakList")
    var modHakList: List<String>? = null

    @get:NwnField(name = "Mod_ID", type = "VOID")
    @get:JsonProperty("ModID")
    @set:JsonProperty("ModID")
    var modID: ByteArray? = null

    @get:NwnField(name = "Mod_DawnHour", type = "BYTE")
    @get:JsonProperty("ModDawnHour")
    @set:JsonProperty("ModDawnHour")
    var modDawnHour: UByte? = null

    @get:NwnField(name = "Mod_Creator_ID", type = "INT")
    @get:JsonProperty("ModCreatorID")
    @set:JsonProperty("ModCreatorID")
    var modCreatorID: Int? = null

    @get:NwnField(name = "Mod_OnSpawnBtnDn", type = "ResRef")
    @get:JsonProperty("ModOnSpawnBtnDn")
    @set:JsonProperty("ModOnSpawnBtnDn")
    var modOnSpawnBtnDn: String? = null

    @get:NwnField(name = "Mod_IsSaveGame", type = "BYTE")
    @get:JsonProperty("ModIsSaveGame")
    @set:JsonProperty("ModIsSaveGame")
    var modIsSaveGame: UByte? = null

    @get:NwnField(name = "Mod_OnUsrDefined", type = "ResRef", blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("ModOnUsrDefined")
    @set:JsonProperty("ModOnUsrDefined")
    var modOnUsrDefined: String? = null

    @get:NwnField(name = "Mod_StartYear", type = "DWORD")
    @get:JsonProperty("ModStartYear")
    @set:JsonProperty("ModStartYear")
    var modStartYear: UInt? = null

    @get:NwnField(name = "Mod_OnClientEntr", type = "ResRef", blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("ModOnClientEntr")
    @set:JsonProperty("ModOnClientEntr")
    var modOnClientEntr: String? = null

    @get:NwnField(name = "Mod_CacheNSSList", type = "List", structType = 9, elementName = "ResRef", elementType = "ResRef")
    @get:JsonProperty("ModCacheNSSList")
    @set:JsonProperty("ModCacheNSSList")
    var modCacheNSSList: List<String>? = null

    @get:NwnField(name = "Mod_DefaultBic", type = "ResRef", blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("ModDefaultBic")
    @set:JsonProperty("ModDefaultBic")
    var modDefaultBic: String? = null

    @get:NwnField(name = "Mod_OnNuiEvent", type = "ResRef", blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("ModOnNuiEvent")
    @set:JsonProperty("ModOnNuiEvent")
    var modOnNuiEvent: String? = null

    @get:NwnField(name = "Mod_OnPlrGuiEvt", type = "ResRef", blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("ModOnPlrGuiEvt")
    @set:JsonProperty("ModOnPlrGuiEvt")
    var modOnPlrGuiEvt: String? = null

    @get:NwnField(name = "Mod_OnPlrTileAct", type = "ResRef", blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("ModOnPlrTileAct")
    @set:JsonProperty("ModOnPlrTileAct")
    var modOnPlrTileAct: String? = null

    @get:NwnField(name = "Mod_PartyControl", type = "INT")
    @get:JsonProperty("ModPartyControl")
    @set:JsonProperty("ModPartyControl")
    var modPartyControl: Int? = null

    @get:NwnField(name = "Mod_OnPlrTarget", type = "ResRef", blankBehavior = BlankBehavior.GENERATE)
    @get:JsonProperty("ModOnPlrTarget")
    @set:JsonProperty("ModOnPlrTarget")
    var modOnPlrTarget: String? = null

    @get:NwnField(name = "Mod_UUID", type = "CExoString")
    @get:JsonProperty("ModUUID")
    @set:JsonProperty("ModUUID")
    var modUUID: String? = null

    @get:NwnField(name = "Mod_Hak", type = "CExoString", blankBehavior = BlankBehavior.SUPPRESS)
    @get:JsonProperty("ModHak")
    @set:JsonProperty("ModHak")
    var modHak: String? = null
}

class IfoModCutScene

class IfoModExpan

class IfoModGVar

class IfoVarTable {

    @get:NwnField(name = "Type", type = "DWORD")
    @get:JsonProperty("Type")
    @set:JsonProperty("Type")
    var type: UInt? = null

    @get:NwnField(name = "Name", type = "CExoString")
    @get:JsonProperty("Name")
    @set:JsonProperty("Name")
    var name: String? = null

    @get:NwnField(name = "Value", type = "")
    @get:JsonProperty("Value")
    @set:JsonProperty("Value")
    var value: Any? = null
}
