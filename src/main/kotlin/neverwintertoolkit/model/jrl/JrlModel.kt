package neverwintertoolkit.model.jrl


// Generated 2024-01-31T19:16:11.453258
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.model.annotation.NwnField
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import java.io.OutputStream

class Jrl : GffObj {
    companion object {
        val factory: GffFactory<Jrl> = GenericGffFactory(Jrl::class.java, ".jrl")
    }
    override fun writeGff(output: OutputStream) = GenericGffWriter(this, ".jrl").writeGff(output)

    @get:NwnField(name = "Categories", type = "List", indexedStructId = true)
    @get:JsonProperty("Categories")
    @set:JsonProperty("Categories")
    var categories: List<JrlCategories>? = null
}

class JrlCategories {

    @get:NwnField(name = "Picture", type = "WORD")
    @get:JsonProperty("Picture")
    @set:JsonProperty("Picture")
    var picture: UShort? = null

    @get:NwnField(name = "EntryList", type = "List", indexedStructId = true)
    @get:JsonProperty("EntryList")
    @set:JsonProperty("EntryList")
    var entryList: List<JrlEntry>? = null

    @get:NwnField(name = "XP", type = "DWORD")
    @get:JsonProperty("XP")
    @set:JsonProperty("XP")
    var xP: UInt? = null

    @get:NwnField(name = "Priority", type = "DWORD")
    @get:JsonProperty("Priority")
    @set:JsonProperty("Priority")
    var priority: UInt? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "Name", type = "CExoLocString")
    @get:JsonProperty("Name")
    @set:JsonProperty("Name")
    var name: CExoLocString? = null
}

class JrlEntry {

    @get:NwnField(name = "End", type = "WORD")
    @get:JsonProperty("End")
    @set:JsonProperty("End")
    var end: UShort? = null

    @get:NwnField(name = "Text", type = "CExoLocString")
    @get:JsonProperty("Text")
    @set:JsonProperty("Text")
    var text: CExoLocString? = null

    @get:NwnField(name = "ID", type = "DWORD")
    @get:JsonProperty("ID")
    @set:JsonProperty("ID")
    var iD: UInt? = null
}
