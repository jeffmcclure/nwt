package neverwintertoolkit.model.gic

// Generated 2024-01-31T10:17:46.673651
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.model.annotation.NwnField
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import java.io.OutputStream

class Gic : GffObj {
    companion object {
        val factory: GffFactory<Gic> = GenericGffFactory(Gic::class.java, ".gic")
    }

    override fun writeGff(output: OutputStream) {
        GenericGffWriter(this, factory.extension).writeGff(output)
    }

    @get:NwnField(name = "List", type = "List", structType = 0)
    @get:JsonProperty("List")
    @set:JsonProperty("List")
    var list: List<GicSome>? = null

    @get:NwnField(name = "TriggerList", type = "List", structType = 1)
    @get:JsonProperty("TriggerList")
    @set:JsonProperty("TriggerList")
    var triggerList: List<GicTrigger>? = null

    @get:NwnField(name = "Placeable List", type = "List", structType = 9)
    @get:JsonProperty("PlaceableList")
    @set:JsonProperty("PlaceableList")
    var placeableList: List<GicPlaceable>? = null

    @get:NwnField(name = "Encounter List", type = "List", structType = 7)
    @get:JsonProperty("EncounterList")
    @set:JsonProperty("EncounterList")
    var encounterList: List<GicEncounter>? = null

    @get:NwnField(name = "Creature List", type = "List", structType = 4)
    @get:JsonProperty("CreatureList")
    @set:JsonProperty("CreatureList")
    var creatureList: List<GicCreature>? = null

    @get:NwnField(name = "Door List", type = "List", structType = 8)
    @get:JsonProperty("DoorList")
    @set:JsonProperty("DoorList")
    var doorList: List<GicDoor>? = null

    @get:NwnField(name = "StoreList", type = "List", structType = 11)
    @get:JsonProperty("StoreList")
    @set:JsonProperty("StoreList")
    var storeList: List<GicStore>? = null

    @get:NwnField(name = "SoundList", type = "List", structType = 6)
    @get:JsonProperty("SoundList")
    @set:JsonProperty("SoundList")
    var soundList: List<GicSound>? = null

    @get:NwnField(name = "WaypointList", type = "List", structType = 5)
    @get:JsonProperty("WaypointList")
    @set:JsonProperty("WaypointList")
    var waypointList: List<GicWaypoint>? = null
}

class GicCreature {

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GicDoor {

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GicEncounter {
}

class GicPlaceable {

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GicSome {

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GicSound {

    @get:NwnField(name = "PlayInToolset", type = "BYTE")
    @get:JsonProperty("PlayInToolset")
    @set:JsonProperty("PlayInToolset")
    var playInToolset: UByte? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GicStore {

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GicTrigger {

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GicWaypoint {

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}
