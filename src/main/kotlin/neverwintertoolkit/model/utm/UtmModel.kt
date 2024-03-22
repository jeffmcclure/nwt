package neverwintertoolkit.model.utm

// Generated 2024-01-31T19:17:57.602344
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.model.annotation.NwnField
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import neverwintertoolkit.model.git.HasStructId
import java.io.OutputStream

class Utm : GffObj {
    companion object {
        val factory: GffFactory<Utm> = GenericGffFactory(Utm::class.java, ".utm")
    }
    override fun writeGff(output: OutputStream) = GenericGffWriter(this, ".utm").writeGff(output)

    @get:NwnField(name = "OnStoreClosed", type = "ResRef")
    @get:JsonProperty("OnStoreClosed")
    @set:JsonProperty("OnStoreClosed")
    var onStoreClosed: String? = null

    @get:NwnField(name = "MarkUp", type = "INT")
    @get:JsonProperty("MarkUp")
    @set:JsonProperty("MarkUp")
    var markUp: Int? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "LocName", type = "CExoLocString")
    @get:JsonProperty("LocName")
    @set:JsonProperty("LocName")
    var locName: CExoLocString? = null

    @get:NwnField(name = "MarkDown", type = "INT")
    @get:JsonProperty("MarkDown")
    @set:JsonProperty("MarkDown")
    var markDown: Int? = null

    @get:NwnField(name = "MaxBuyPrice", type = "INT")
    @get:JsonProperty("MaxBuyPrice")
    @set:JsonProperty("MaxBuyPrice")
    var maxBuyPrice: Int? = null

    @get:NwnField(name = "StoreGold", type = "INT")
    @get:JsonProperty("StoreGold")
    @set:JsonProperty("StoreGold")
    var storeGold: Int? = null

    @get:NwnField(name = "WillNotBuy", type = "List", structType = 97869)
    @get:JsonProperty("WillNotBuy")
    @set:JsonProperty("WillNotBuy")
    var willNotBuy: List<UtmWillNotBuy>? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "StoreList", type = "List", structType = 0)
    @get:JsonProperty("StoreList")
    @set:JsonProperty("StoreList")
    var storeList: List<UtmStore>? = null

    @get:NwnField(name = "BlackMarket", type = "BYTE")
    @get:JsonProperty("BlackMarket")
    @set:JsonProperty("BlackMarket")
    var blackMarket: UByte? = null

    @get:NwnField(name = "IdentifyPrice", type = "INT")
    @get:JsonProperty("IdentifyPrice")
    @set:JsonProperty("IdentifyPrice")
    var identifyPrice: Int? = null

    @get:NwnField(name = "WillOnlyBuy", type = "List", structType = 97869)
    @get:JsonProperty("WillOnlyBuy")
    @set:JsonProperty("WillOnlyBuy")
    var willOnlyBuy: List<UtmWillOnlyBuy>? = null

    @get:NwnField(name = "ResRef", type = "ResRef")
    @get:JsonProperty("ResRef")
    @set:JsonProperty("ResRef")
    var resRef: String? = null

    @get:NwnField(name = "BM_MarkDown", type = "INT")
    @get:JsonProperty("BMMarkDown")
    @set:JsonProperty("BMMarkDown")
    var bMMarkDown: Int? = null

    @get:NwnField(name = "ID", type = "BYTE")
    @get:JsonProperty("ID")
    @set:JsonProperty("ID")
    var iD: UByte? = null

    @get:NwnField(name = "OnOpenStore", type = "ResRef")
    @get:JsonProperty("OnOpenStore")
    @set:JsonProperty("OnOpenStore")
    var onOpenStore: String? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<UtmVarTable>? = null
}

class UtmItem {

    @get:NwnField(name = "Repos_Posy", type = "WORD")
    @get:JsonProperty("ReposPosy")
    @set:JsonProperty("ReposPosy")
    var reposPosy: UShort? = null

    @get:NwnField(name = "InventoryRes", type = "ResRef")
    @get:JsonProperty("InventoryRes")
    @set:JsonProperty("InventoryRes")
    var inventoryRes: String? = null

    @get:NwnField(name = "Repos_PosX", type = "WORD")
    @get:JsonProperty("ReposPosX")
    @set:JsonProperty("ReposPosX")
    var reposPosX: UShort? = null

    @get:NwnField(name = "Infinite", type = "BYTE")
    @get:JsonProperty("Infinite")
    @set:JsonProperty("Infinite")
    var infinite: UByte? = null
}

class UtmStore : HasStructId {

    @get:JsonProperty("StructId")
    @set:JsonProperty("StructId")
    override var structId: UInt? = null

    @get:NwnField(name = "ItemList", type = "List", indexedStructId = true)
    @get:JsonProperty("ItemList")
    @set:JsonProperty("ItemList")
    var itemList: List<UtmItem>? = null
}

class UtmVarTable {

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

class UtmWillNotBuy {

    @get:NwnField(name = "BaseItem", type = "INT")
    @get:JsonProperty("BaseItem")
    @set:JsonProperty("BaseItem")
    var baseItem: Int? = null
}

class UtmWillOnlyBuy {

    @get:NwnField(name = "BaseItem", type = "INT")
    @get:JsonProperty("BaseItem")
    @set:JsonProperty("BaseItem")
    var baseItem: Int? = null
}
