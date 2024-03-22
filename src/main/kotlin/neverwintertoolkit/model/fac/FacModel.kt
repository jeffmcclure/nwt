package neverwintertoolkit.model.fac

// Generated 2024-01-31T19:15:25.810955
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.model.annotation.NwnField
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import java.io.OutputStream

class Fac : GffObj {
    companion object {
        val factory: GffFactory<Fac> = GenericGffFactory(Fac::class.java, ".fac")
    }
    override fun writeGff(output: OutputStream) = GenericGffWriter(this, ".fac").writeGff(output)

    @get:NwnField(name = "RepList", type = "List", indexedStructId = true)
    @get:JsonProperty("RepList")
    @set:JsonProperty("RepList")
    var repList: List<FacRep>? = null

    @get:NwnField(name = "FactionList", type = "List", indexedStructId = true)
    @get:JsonProperty("FactionList")
    @set:JsonProperty("FactionList")
    var factionList: List<FacFaction>? = null
}

class FacFaction {

    @get:NwnField(name = "FactionParentID", type = "DWORD")
    @get:JsonProperty("FactionParentID")
    @set:JsonProperty("FactionParentID")
    var factionParentID: UInt? = null

    @get:NwnField(name = "FactionName", type = "CExoString")
    @get:JsonProperty("FactionName")
    @set:JsonProperty("FactionName")
    var factionName: String? = null

    @get:NwnField(name = "FactionGlobal", type = "WORD")
    @get:JsonProperty("FactionGlobal")
    @set:JsonProperty("FactionGlobal")
    var factionGlobal: UShort? = null
}

class FacRep {

    @get:NwnField(name = "FactionRep", type = "DWORD")
    @get:JsonProperty("FactionRep")
    @set:JsonProperty("FactionRep")
    var factionRep: UInt? = null

    @get:NwnField(name = "FactionID1", type = "DWORD")
    @get:JsonProperty("FactionID1")
    @set:JsonProperty("FactionID1")
    var factionID1: UInt? = null

    @get:NwnField(name = "FactionID2", type = "DWORD")
    @get:JsonProperty("FactionID2")
    @set:JsonProperty("FactionID2")
    var factionID2: UInt? = null
}
