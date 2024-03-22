package neverwintertoolkit.model.ute

// Generated 2024-01-31T18:38:18.892732
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.model.annotation.NwnField
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import java.io.OutputStream

class Ute : GffObj {
    companion object {
        val factory: GffFactory<Ute> = GenericGffFactory(Ute::class.java, ".ute")
    }
    override fun writeGff(output: OutputStream) {
        GenericGffWriter(this, ".ute").writeGff(output)
    }

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "Active", type = "BYTE")
    @get:JsonProperty("Active")
    @set:JsonProperty("Active")
    var active: UByte? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "MaxCreatures", type = "INT")
    @get:JsonProperty("MaxCreatures")
    @set:JsonProperty("MaxCreatures")
    var maxCreatures: Int? = null

    @get:NwnField(name = "OnHeartbeat", type = "ResRef")
    @get:JsonProperty("OnHeartbeat")
    @set:JsonProperty("OnHeartbeat")
    var onHeartbeat: String? = null

    @get:NwnField(name = "Difficulty", type = "INT")
    @get:JsonProperty("Difficulty")
    @set:JsonProperty("Difficulty")
    var difficulty: Int? = null

    @get:NwnField(name = "OnEntered", type = "ResRef")
    @get:JsonProperty("OnEntered")
    @set:JsonProperty("OnEntered")
    var onEntered: String? = null

    @get:NwnField(name = "PlayerOnly", type = "BYTE")
    @get:JsonProperty("PlayerOnly")
    @set:JsonProperty("PlayerOnly")
    var playerOnly: UByte? = null

    @get:NwnField(name = "Faction", type = "DWORD")
    @get:JsonProperty("Faction")
    @set:JsonProperty("Faction")
    var faction: UInt? = null

    @get:NwnField(name = "ResetTime", type = "INT")
    @get:JsonProperty("ResetTime")
    @set:JsonProperty("ResetTime")
    var resetTime: Int? = null

    @get:NwnField(name = "OnExhausted", type = "ResRef")
    @get:JsonProperty("OnExhausted")
    @set:JsonProperty("OnExhausted")
    var onExhausted: String? = null

    @get:NwnField(name = "RecCreatures", type = "INT")
    @get:JsonProperty("RecCreatures")
    @set:JsonProperty("RecCreatures")
    var recCreatures: Int? = null

    @get:NwnField(name = "PaletteID", type = "BYTE")
    @get:JsonProperty("PaletteID")
    @set:JsonProperty("PaletteID")
    var paletteID: UByte? = null

    @get:NwnField(name = "CreatureList", type = "List", structType = 0)
    @get:JsonProperty("CreatureList")
    @set:JsonProperty("CreatureList")
    var creatureList: List<UteCreature>? = null

    @get:NwnField(name = "OnExit", type = "ResRef")
    @get:JsonProperty("OnExit")
    @set:JsonProperty("OnExit")
    var onExit: String? = null

    @get:NwnField(name = "SpawnOption", type = "INT")
    @get:JsonProperty("SpawnOption")
    @set:JsonProperty("SpawnOption")
    var spawnOption: Int? = null

    @get:NwnField(name = "OnUserDefined", type = "ResRef")
    @get:JsonProperty("OnUserDefined")
    @set:JsonProperty("OnUserDefined")
    var onUserDefined: String? = null

    @get:NwnField(name = "Reset", type = "BYTE")
    @get:JsonProperty("Reset")
    @set:JsonProperty("Reset")
    var reset: UByte? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "DifficultyIndex", type = "INT")
    @get:JsonProperty("DifficultyIndex")
    @set:JsonProperty("DifficultyIndex")
    var difficultyIndex: Int? = null

    @get:NwnField(name = "Respawns", type = "INT")
    @get:JsonProperty("Respawns")
    @set:JsonProperty("Respawns")
    var respawns: Int? = null

    @get:NwnField(name = "LocalizedName", type = "CExoLocString")
    @get:JsonProperty("LocalizedName")
    @set:JsonProperty("LocalizedName")
    var localizedName: CExoLocString? = null
}

class UteCreature {

    @get:NwnField(name = "Appearance", type = "INT")
    @get:JsonProperty("Appearance")
    @set:JsonProperty("Appearance")
    var appearance: Int? = null

    @get:NwnField(name = "ResRef", type = "ResRef")
    @get:JsonProperty("ResRef")
    @set:JsonProperty("ResRef")
    var resRef: String? = null

    @get:NwnField(name = "CR", type = "FLOAT")
    @get:JsonProperty("CR")
    @set:JsonProperty("CR")
    var cR: Float? = null

    @get:NwnField(name = "SingleSpawn", type = "BYTE")
    @get:JsonProperty("SingleSpawn")
    @set:JsonProperty("SingleSpawn")
    var singleSpawn: UByte? = null
}
