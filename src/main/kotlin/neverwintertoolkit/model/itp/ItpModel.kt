package neverwintertoolkit.model.itp


// Generated 2024-01-31T19:35:32.400708
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import neverwintertoolkit.model.annotation.NwnField
import java.io.OutputStream

class Itp : GffObj {
    companion object {
        val factory: GffFactory<Itp> = GenericGffFactory(Itp::class.java, ".itp")
    }
    override fun writeGff(output: OutputStream) = GenericGffWriter(this, ".itp").writeGff(output)

    @get:NwnField(name = "MAIN", type = "List", structType = 0)
    @get:JsonProperty("MAIN")
    @set:JsonProperty("MAIN")
    var mAIN: List<ItpMAIN>? = null

    @get:NwnField(name = "RESTYPE", type = "WORD")
    @get:JsonProperty("ResType")
    @set:JsonProperty("ResType")
    var resType: UShort? = null

    @get:NwnField(name = "TILESETRESREF", type = "ResRef")
    @get:JsonProperty("TileSetResRef")
    @set:JsonProperty("TileSetResRef")
    var tileSetResRef: UShort? = null

    @get:NwnField(name = "NEXT_USEABLE_ID", type = "BYTE")
    @get:JsonProperty("NextUsableId")
    @set:JsonProperty("NextUsableId")
    var nextUsableId: UByte? = null
}

class ItpMAIN {

    @get:NwnField(name = "ID", type = "BYTE")
    @get:JsonProperty("ID")
    @set:JsonProperty("ID")
    var iD: UByte? = null

    @get:NwnField(name = "STRREF", type = "DWORD")
    @get:JsonProperty("STRREF")
    @set:JsonProperty("STRREF")
    var sTRREF: UInt? = null

    @get:NwnField(name = "LIST", type = "List", structType = 0)
    @get:JsonProperty("LIST")
    @set:JsonProperty("LIST")
    var lIST: List<ItpSome>? = null

    @get:NwnField(name = "DELETE_ME", type = "CExoString")
    @get:JsonProperty("DeleteMe")
    @set:JsonProperty("DeleteMe")
    var deleteMe: String? = null

    @get:NwnField(name = "TYPE", type = "BYTE")
    @get:JsonProperty("TYPE")
    @set:JsonProperty("TYPE")
    var tYPE: UByte? = null
}

class ItpSome {

    @get:NwnField(name = "STRREF", type = "DWORD")
    @get:JsonProperty("STRREF")
    @set:JsonProperty("STRREF")
    var sTRREF: UInt? = null

    @get:NwnField(name = "ID", type = "BYTE")
    @get:JsonProperty("ID")
    @set:JsonProperty("ID")
    var iD: UByte? = null

    @get:NwnField(name = "NAME", type = "CExoString")
    @get:JsonProperty("NAME")
    @set:JsonProperty("NAME")
    var nAME: String? = null

    @get:NwnField(name = "RESREF", type = "ResRef")
    @get:JsonProperty("RESREF")
    @set:JsonProperty("RESREF")
    var rESREF: String? = null

    @get:NwnField(name = "CR", type = "FLOAT")
    @get:JsonProperty("CR")
    @set:JsonProperty("CR")
    var cR: Float? = null

    @get:NwnField(name = "FACTION", type = "CExoString")
    @get:JsonProperty("FACTION")
    @set:JsonProperty("FACTION")
    var fACTION: String? = null

    @get:NwnField(name = "LIST", type = "List", structType = 0)
    @get:JsonProperty("LIST")
    @set:JsonProperty("LIST")
    var lIST: List<ItpSome>? = null

    @get:NwnField(name = "DELETE_ME", type = "CExoString")
    @get:JsonProperty("DELETEME")
    @set:JsonProperty("DELETEME")
    var dELETEME: String? = null
}
