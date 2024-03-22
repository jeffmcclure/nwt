package neverwintertoolkit.model.utw

// Generated 2024-01-31T19:16:51.977002
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.model.annotation.NwnField
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import java.io.OutputStream

class Utw : GffObj {
    companion object {
        val factory: GffFactory<Utw> = GenericGffFactory(Utw::class.java, ".utw")
    }
    override fun writeGff(output: OutputStream) = GenericGffWriter(this, ".utw").writeGff(output)

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "HasMapNote", type = "BYTE")
    @get:JsonProperty("HasMapNote")
    @set:JsonProperty("HasMapNote")
    var hasMapNote: UByte? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "Appearance", type = "BYTE")
    @get:JsonProperty("Appearance")
    @set:JsonProperty("Appearance")
    var appearance: UByte? = null

    @get:NwnField(name = "LocalizedName", type = "CExoLocString")
    @get:JsonProperty("LocalizedName")
    @set:JsonProperty("LocalizedName")
    var localizedName: CExoLocString? = null

    @get:NwnField(name = "Description", type = "CExoLocString")
    @get:JsonProperty("Description")
    @set:JsonProperty("Description")
    var description: CExoLocString? = null

    @get:NwnField(name = "LinkedTo", type = "CExoString")
    @get:JsonProperty("LinkedTo")
    @set:JsonProperty("LinkedTo")
    var linkedTo: String? = null

    @get:NwnField(name = "PaletteID", type = "BYTE")
    @get:JsonProperty("PaletteID")
    @set:JsonProperty("PaletteID")
    var paletteID: UByte? = null

    @get:NwnField(name = "MapNoteEnabled", type = "BYTE")
    @get:JsonProperty("MapNoteEnabled")
    @set:JsonProperty("MapNoteEnabled")
    var mapNoteEnabled: UByte? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "MapNote", type = "CExoLocString")
    @get:JsonProperty("MapNote")
    @set:JsonProperty("MapNote")
    var mapNote: CExoLocString? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<UtwVarTable>? = null
}

class UtwVarTable {

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
