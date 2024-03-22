package neverwintertoolkit.model.uts

// Generated 2024-01-31T19:41:50.067152
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.model.annotation.NwnField
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import java.io.OutputStream

class Uts : GffObj {
    companion object {
        val factory: GffFactory<Uts> = GenericGffFactory(Uts::class.java, ".uts")
    }
    override fun writeGff(output: OutputStream) = GenericGffWriter(this, ".uts").writeGff(output)

    @get:NwnField(name = "Sounds", type = "List", structType = 0)
    @get:JsonProperty("Sounds")
    @set:JsonProperty("Sounds")
    var sounds: List<UtsSounds>? = null

    @get:NwnField(name = "Active", type = "BYTE")
    @get:JsonProperty("Active")
    @set:JsonProperty("Active")
    var active: UByte? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "RandomRangeY", type = "FLOAT")
    @get:JsonProperty("RandomRangeY")
    @set:JsonProperty("RandomRangeY")
    var randomRangeY: Float? = null

    @get:NwnField(name = "Hours", type = "DWORD")
    @get:JsonProperty("Hours")
    @set:JsonProperty("Hours")
    var hours: UInt? = null

    @get:NwnField(name = "Looping", type = "BYTE")
    @get:JsonProperty("Looping")
    @set:JsonProperty("Looping")
    var looping: UByte? = null

    @get:NwnField(name = "Times", type = "BYTE")
    @get:JsonProperty("Times")
    @set:JsonProperty("Times")
    var times: UByte? = null

    @get:NwnField(name = "Interval", type = "DWORD")
    @get:JsonProperty("Interval")
    @set:JsonProperty("Interval")
    var interval: UInt? = null

    @get:NwnField(name = "PaletteID", type = "BYTE")
    @get:JsonProperty("PaletteID")
    @set:JsonProperty("PaletteID")
    var paletteID: UByte? = null

    @get:NwnField(name = "LocName", type = "CExoLocString")
    @get:JsonProperty("LocName")
    @set:JsonProperty("LocName")
    var locName: CExoLocString? = null

    @get:NwnField(name = "IntervalVrtn", type = "DWORD")
    @get:JsonProperty("IntervalVrtn")
    @set:JsonProperty("IntervalVrtn")
    var intervalVrtn: UInt? = null

    @get:NwnField(name = "Random", type = "BYTE")
    @get:JsonProperty("Random")
    @set:JsonProperty("Random")
    var random: UByte? = null

    @get:NwnField(name = "RandomRangeX", type = "FLOAT")
    @get:JsonProperty("RandomRangeX")
    @set:JsonProperty("RandomRangeX")
    var randomRangeX: Float? = null

    @get:NwnField(name = "Volume", type = "BYTE")
    @get:JsonProperty("Volume")
    @set:JsonProperty("Volume")
    var volume: UByte? = null

    @get:NwnField(name = "VolumeVrtn", type = "BYTE")
    @get:JsonProperty("VolumeVrtn")
    @set:JsonProperty("VolumeVrtn")
    var volumeVrtn: UByte? = null

    @get:NwnField(name = "Priority", type = "BYTE")
    @get:JsonProperty("Priority")
    @set:JsonProperty("Priority")
    var priority: UByte? = null

    @get:NwnField(name = "Continuous", type = "BYTE")
    @get:JsonProperty("Continuous")
    @set:JsonProperty("Continuous")
    var continuous: UByte? = null

    @get:NwnField(name = "PitchVariation", type = "FLOAT")
    @get:JsonProperty("PitchVariation")
    @set:JsonProperty("PitchVariation")
    var pitchVariation: Float? = null

    @get:NwnField(name = "RandomPosition", type = "BYTE")
    @get:JsonProperty("RandomPosition")
    @set:JsonProperty("RandomPosition")
    var randomPosition: UByte? = null

    @get:NwnField(name = "Elevation", type = "FLOAT")
    @get:JsonProperty("Elevation")
    @set:JsonProperty("Elevation")
    var elevation: Float? = null

    @get:NwnField(name = "MaxDistance", type = "FLOAT")
    @get:JsonProperty("MaxDistance")
    @set:JsonProperty("MaxDistance")
    var maxDistance: Float? = null

    @get:NwnField(name = "Positional", type = "BYTE")
    @get:JsonProperty("Positional")
    @set:JsonProperty("Positional")
    var positional: UByte? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "MinDistance", type = "FLOAT")
    @get:JsonProperty("MinDistance")
    @set:JsonProperty("MinDistance")
    var minDistance: Float? = null
}

class UtsSounds {

    @get:NwnField(name = "Sound", type = "ResRef")
    @get:JsonProperty("Sound")
    @set:JsonProperty("Sound")
    var sound: String? = null
}
