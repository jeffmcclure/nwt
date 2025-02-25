package neverwintertoolkit.model.git

// Generated 2024-01-29T13:59:36.830099
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import neverwintertoolkit.command.GlobalOptions
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import neverwintertoolkit.model.annotation.NwnField
import neverwintertoolkit.model.gic.Gic
import java.io.OutputStream
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.name

class Git : GffObj {
    companion object {
        val factory: GffFactory<Git> = GenericGffFactory(Git::class.java, ".git")
    }

    //override fun writeGff(output: OutputStream) = GenericGffWriter(this, ".git").writeGff(output)
    override fun writeGff(output: OutputStream) = throw UnsupportedOperationException()

    override fun writeGff(file: Path, globalOptions: GlobalOptions?) {
        globalOptions?.logInfo { "Writing $file" }
        GenericGffWriter(this, ".git").writeGff(file)
        val target = file.parent?.resolve(file.name + ".1.gic") ?: Path.of(file.name + ".1.gic")
        globalOptions?.logInfo { "Writing $target" }
        GenericGffWriter(Gic(), ".gic").writeGff(target)
    }

    @get:NwnField(name = "Door List", type = "List", structType = 8)
    @get:JsonProperty("DoorList")
    @set:JsonProperty("DoorList")
    var doorList: List<GitDoor>? = null

    @get:NwnField(name = "Creature List", type = "List", structType = 4)
    @get:JsonProperty("CreatureList")
    @set:JsonProperty("CreatureList")
    var creatureList: List<GitCreature>? = null

    @get:NwnField(name = "TriggerList", type = "List", structType = 1)
    @get:JsonProperty("TriggerList")
    @set:JsonProperty("TriggerList")
    var triggerList: List<GitTrigger>? = null

    @get:NwnField(name = "AreaProperties", type = "Struct", structType = 100)
    @get:JsonProperty("AreaProperties")
    @set:JsonProperty("AreaProperties")
    var areaProperties: GitAreaProperties? = null

    @get:NwnField(name = "Placeable List", type = "List", structType = 9)
    @get:JsonProperty("PlaceableList")
    @set:JsonProperty("PlaceableList")
    var placeableList: List<GitPlaceable>? = null

    @get:NwnField(name = "SoundList", type = "List", structType = 6)
    @get:JsonProperty("SoundList")
    @set:JsonProperty("SoundList")
    var soundList: List<GitSound>? = null

    @get:NwnField(name = "List", type = "List", structType = 0)
    @get:JsonProperty("List")
    @set:JsonProperty("List")
    var list: List<GitSome>? = null

    @get:NwnField(name = "StoreList", type = "List", structType = 11)
    @get:JsonProperty("StoreList")
    @set:JsonProperty("StoreList")
    var storeList: List<GitStore>? = null

    @get:NwnField(name = "WaypointList", type = "List", structType = 5)
    @get:JsonProperty("WaypointList")
    @set:JsonProperty("WaypointList")
    var waypointList: List<GitWaypoint>? = null

    @get:NwnField(name = "Encounter List", type = "List", structType = 7)
    @get:JsonProperty("EncounterList")
    @set:JsonProperty("EncounterList")
    var encounterList: List<GitEncounter>? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<GitVarTable>? = null
}

class GitTranslate {
    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "ValueTo", type = "FLOAT")
    @get:JsonProperty("ValueTo")
    @set:JsonProperty("ValueTo")
    var valueTo: Float? = null

    @get:NwnField(name = "LerpType", type = "INT")
    @get:JsonProperty("LerpType")
    @set:JsonProperty("LerpType")
    var lerpType: Int? = null

    @get:NwnField(name = "TimerType", type = "INT")
    @get:JsonProperty("TimerType")
    @set:JsonProperty("TimerType")
    var timerType: Int? = null
}

class GitAreaProperties {

    @get:NwnField(name = "MusicBattle", type = "INT")
    @get:JsonProperty("MusicBattle")
    @set:JsonProperty("MusicBattle")
    var musicBattle: Int? = null

    @get:NwnField(name = "AmbientSndDayVol", type = "INT")
    @get:JsonProperty("AmbientSndDayVol")
    @set:JsonProperty("AmbientSndDayVol")
    var ambientSndDayVol: Int? = null

    @get:NwnField(name = "MusicNight", type = "INT")
    @get:JsonProperty("MusicNight")
    @set:JsonProperty("MusicNight")
    var musicNight: Int? = null

    @get:NwnField(name = "AmbientSndNitVol", type = "INT")
    @get:JsonProperty("AmbientSndNitVol")
    @set:JsonProperty("AmbientSndNitVol")
    var ambientSndNitVol: Int? = null

    @get:NwnField(name = "MusicDelay", type = "INT")
    @get:JsonProperty("MusicDelay")
    @set:JsonProperty("MusicDelay")
    var musicDelay: Int? = null

    @get:NwnField(name = "AmbientSndNight", type = "INT")
    @get:JsonProperty("AmbientSndNight")
    @set:JsonProperty("AmbientSndNight")
    var ambientSndNight: Int? = null

    @get:NwnField(name = "EnvAudio", type = "INT")
    @get:JsonProperty("EnvAudio")
    @set:JsonProperty("EnvAudio")
    var envAudio: Int? = null

    @get:NwnField(name = "MusicDay", type = "INT")
    @get:JsonProperty("MusicDay")
    @set:JsonProperty("MusicDay")
    var musicDay: Int? = null

    @get:NwnField(name = "AmbientSndDay", type = "INT")
    @get:JsonProperty("AmbientSndDay")
    @set:JsonProperty("AmbientSndDay")
    var ambientSndDay: Int? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GitClass {

    @get:NwnField(name = "Class", type = "INT")
    @get:JsonProperty("ClassX")
    @set:JsonProperty("ClassX")
    var classX: Int? = null

    @get:NwnField(name = "ClassLevel", type = "SHORT")
    @get:JsonProperty("ClassLevel")
    @set:JsonProperty("ClassLevel")
    var classLevel: Short? = null

    @get:NwnField(name = "MemorizedList0", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList0")
    @set:JsonProperty("MemorizedList0")
    var memorizedList0: List<GitMemorized>? = null

    @get:NwnField(name = "MemorizedList2", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList2")
    @set:JsonProperty("MemorizedList2")
    var memorizedList2: List<GitMemorized>? = null

    @get:NwnField(name = "MemorizedList6", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList6")
    @set:JsonProperty("MemorizedList6")
    var memorizedList6: List<GitMemorized>? = null

    @get:NwnField(name = "MemorizedList5", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList5")
    @set:JsonProperty("MemorizedList5")
    var memorizedList5: List<GitMemorized>? = null

    @get:NwnField(name = "MemorizedList3", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList3")
    @set:JsonProperty("MemorizedList3")
    var memorizedList3: List<GitMemorized>? = null

    @get:NwnField(name = "KnownList9", type = "List", structType = 3)
    @get:JsonProperty("KnownList9")
    @set:JsonProperty("KnownList9")
    var knownList9: List<GitKnown>? = null

    @get:NwnField(name = "KnownList3", type = "List", structType = 3)
    @get:JsonProperty("KnownList3")
    @set:JsonProperty("KnownList3")
    var knownList3: List<GitKnown>? = null

    @get:NwnField(name = "MemorizedList9", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList9")
    @set:JsonProperty("MemorizedList9")
    var memorizedList9: List<GitMemorized>? = null

    @get:NwnField(name = "MemorizedList1", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList1")
    @set:JsonProperty("MemorizedList1")
    var memorizedList1: List<GitMemorized>? = null

    @get:NwnField(name = "KnownList7", type = "List", structType = 3)
    @get:JsonProperty("KnownList7")
    @set:JsonProperty("KnownList7")
    var knownList7: List<GitKnown>? = null

    @get:NwnField(name = "KnownList1", type = "List", structType = 3)
    @get:JsonProperty("KnownList1")
    @set:JsonProperty("KnownList1")
    var knownList1: List<GitKnown>? = null

    @get:NwnField(name = "KnownList0", type = "List", structType = 3)
    @get:JsonProperty("KnownList0")
    @set:JsonProperty("KnownList0")
    var knownList0: List<GitKnown>? = null

    @get:NwnField(name = "KnownList2", type = "List", structType = 3)
    @get:JsonProperty("KnownList2")
    @set:JsonProperty("KnownList2")
    var knownList2: List<GitKnown>? = null

    @get:NwnField(name = "KnownList8", type = "List", structType = 3)
    @get:JsonProperty("KnownList8")
    @set:JsonProperty("KnownList8")
    var knownList8: List<GitKnown>? = null

    @get:NwnField(name = "KnownList5", type = "List", structType = 3)
    @get:JsonProperty("KnownList5")
    @set:JsonProperty("KnownList5")
    var knownList5: List<GitKnown>? = null

    @get:NwnField(name = "MemorizedList7", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList7")
    @set:JsonProperty("MemorizedList7")
    var memorizedList7: List<GitMemorized>? = null

    @get:NwnField(name = "MemorizedList4", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList4")
    @set:JsonProperty("MemorizedList4")
    var memorizedList4: List<GitMemorized>? = null

    @get:NwnField(name = "KnownList4", type = "List", structType = 3)
    @get:JsonProperty("KnownList4")
    @set:JsonProperty("KnownList4")
    var knownList4: List<GitKnown>? = null

    @get:NwnField(name = "KnownList6", type = "List", structType = 3)
    @get:JsonProperty("KnownList6")
    @set:JsonProperty("KnownList6")
    var knownList6: List<GitKnown>? = null

    @get:NwnField(name = "MemorizedList8", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList8")
    @set:JsonProperty("MemorizedList8")
    var memorizedList8: List<GitMemorized>? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GitCreature {

    @get:NwnField(name = "ScriptSpellAt", type = "ResRef")
    @get:JsonProperty("ScriptSpellAt")
    @set:JsonProperty("ScriptSpellAt")
    var scriptSpellAt: String? = null

    @get:NwnField(name = "IsImmortal", type = "BYTE")
    @get:JsonProperty("IsImmortal")
    @set:JsonProperty("IsImmortal")
    var isImmortal: UByte? = null

    @get:NwnField(name = "Int", type = "BYTE")
    @get:JsonProperty("Int")
    @set:JsonProperty("Int")
    var int: UByte? = null

    @get:NwnField(name = "Deity", type = "CExoString")
    @get:JsonProperty("Deity")
    @set:JsonProperty("Deity")
    var deity: String? = null

    @get:NwnField(name = "MaxHitPoints", type = "SHORT")
    @get:JsonProperty("MaxHitPoints")
    @set:JsonProperty("MaxHitPoints")
    var maxHitPoints: Short? = null

    @get:NwnField(name = "ScriptRested", type = "ResRef")
    @get:JsonProperty("ScriptRested")
    @set:JsonProperty("ScriptRested")
    var scriptRested: String? = null

    @get:NwnField(name = "Interruptable", type = "BYTE")
    @get:JsonProperty("Interruptable")
    @set:JsonProperty("Interruptable")
    var interruptable: UByte? = null

    @get:NwnField(name = "BodyPart_RShin", type = "BYTE")
    @get:JsonProperty("BodyPartRShin")
    @set:JsonProperty("BodyPartRShin")
    var bodyPartRShin: UByte? = null

    @get:NwnField(name = "Dex", type = "BYTE")
    @get:JsonProperty("Dex")
    @set:JsonProperty("Dex")
    var dex: UByte? = null

    @get:NwnField(name = "ClassList", type = "List", structType = 2)
    @get:JsonProperty("ClassList")
    @set:JsonProperty("ClassList")
    var classList: List<GitClass>? = null

    @get:NwnField(name = "Appearance", type = "INT")
    @get:JsonProperty("Appearance")
    @set:JsonProperty("Appearance")
    var appearance: Int? = null

    @get:NwnField(name = "NaturalAC", type = "BYTE")
    @get:JsonProperty("NaturalAC")
    @set:JsonProperty("NaturalAC")
    var naturalAC: UByte? = null

    @get:NwnField(name = "Lootable", type = "BYTE")
    @get:JsonProperty("Lootable")
    @set:JsonProperty("Lootable")
    var lootable: UByte? = null

    @get:NwnField(name = "ScriptDisturbed", type = "ResRef")
    @get:JsonProperty("ScriptDisturbed")
    @set:JsonProperty("ScriptDisturbed")
    var scriptDisturbed: String? = null

    @get:NwnField(name = "Wis", type = "BYTE")
    @get:JsonProperty("Wis")
    @set:JsonProperty("Wis")
    var wis: UByte? = null

    @get:NwnField(name = "Gender", type = "BYTE")
    @get:JsonProperty("Gender")
    @set:JsonProperty("Gender")
    var gender: UByte? = null

    @get:NwnField(name = "BodyPart_LHand", type = "BYTE")
    @get:JsonProperty("BodyPartLHand")
    @set:JsonProperty("BodyPartLHand")
    var bodyPartLHand: UByte? = null

    @get:NwnField(name = "Subrace", type = "CExoString")
    @get:JsonProperty("Subrace")
    @set:JsonProperty("Subrace")
    var subrace: String? = null

    @get:NwnField(name = "SingleSpawn", type = "BYTE")
    @get:JsonProperty("SingleSpawn")
    @set:JsonProperty("SingleSpawn")
    var singleSpawn: UByte? = null

    @get:NwnField(name = "YOrientation", type = "FLOAT")
    @get:JsonProperty("YOrientation")
    @set:JsonProperty("YOrientation")
    var yOrientation: Float? = null

    @get:NwnField(name = "BodyPart_RFArm", type = "BYTE")
    @get:JsonProperty("BodyPartRFArm")
    @set:JsonProperty("BodyPartRFArm")
    var bodyPartRFArm: UByte? = null

    @get:NwnField(name = "Con", type = "BYTE")
    @get:JsonProperty("Con")
    @set:JsonProperty("Con")
    var con: UByte? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<GitVarTable>? = null

    @get:NwnField(name = "Cha", type = "BYTE")
    @get:JsonProperty("Cha")
    @set:JsonProperty("Cha")
    var cha: UByte? = null

    @get:NwnField(name = "FactionID", type = "WORD")
    @get:JsonProperty("FactionID")
    @set:JsonProperty("FactionID")
    var factionID: UShort? = null

    @get:NwnField(name = "CR", type = "FLOAT")
    @get:JsonProperty("CR")
    @set:JsonProperty("CR")
    var cR: Float? = null

    @get:NwnField(name = "Conversation", type = "ResRef")
    @get:JsonProperty("Conversation")
    @set:JsonProperty("Conversation")
    var conversation: String? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "NoPermDeath", type = "BYTE")
    @get:JsonProperty("NoPermDeath")
    @set:JsonProperty("NoPermDeath")
    var noPermDeath: UByte? = null

    @get:NwnField(name = "StartingPackage", type = "BYTE")
    @get:JsonProperty("StartingPackage")
    @set:JsonProperty("StartingPackage")
    var startingPackage: UByte? = null

    @get:NwnField(name = "ScriptEndRound", type = "ResRef")
    @get:JsonProperty("ScriptEndRound")
    @set:JsonProperty("ScriptEndRound")
    var scriptEndRound: String? = null

    @get:NwnField(name = "BodyPart_RThigh", type = "BYTE")
    @get:JsonProperty("BodyPartRThigh")
    @set:JsonProperty("BodyPartRThigh")
    var bodyPartRThigh: UByte? = null

    @get:NwnField(name = "Str", type = "BYTE")
    @get:JsonProperty("Str")
    @set:JsonProperty("Str")
    var str: UByte? = null

    @get:NwnField(name = "Wings_New", type = "DWORD")
    @get:JsonProperty("WingsNew")
    @set:JsonProperty("WingsNew")
    var wingsNew: UInt? = null

    @get:NwnField(name = "BodyBag", type = "BYTE")
    @get:JsonProperty("BodyBag")
    @set:JsonProperty("BodyBag")
    var bodyBag: UByte? = null

    @get:NwnField(name = "ResRef", type = "ResRef")
    @get:JsonProperty("ResRef")
    @set:JsonProperty("ResRef")
    var resRef: String? = null

    @get:NwnField(name = "Tail_New", type = "DWORD")
    @get:JsonProperty("TailNew")
    @set:JsonProperty("TailNew")
    var tailNew: UInt? = null

    @get:NwnField(name = "ScriptDeath", type = "ResRef")
    @get:JsonProperty("ScriptDeath")
    @set:JsonProperty("ScriptDeath")
    var scriptDeath: String? = null

    @get:NwnField(name = "ScriptAttacked", type = "ResRef")
    @get:JsonProperty("ScriptAttacked")
    @set:JsonProperty("ScriptAttacked")
    var scriptAttacked: String? = null

    @get:NwnField(name = "SpecAbilityList", type = "List", structType = 4)
    @get:JsonProperty("SpecAbilityList")
    @set:JsonProperty("SpecAbilityList")
    var specAbilityList: List<GitSpecAbility>? = null

    @get:NwnField(name = "PortraitId", type = "WORD")
    @get:JsonProperty("PortraitId")
    @set:JsonProperty("PortraitId")
    var portraitId: UShort? = null

    @get:NwnField(name = "XOrientation", type = "FLOAT")
    @get:JsonProperty("XOrientation")
    @set:JsonProperty("XOrientation")
    var xOrientation: Float? = null

    @get:NwnField(name = "ScriptDamaged", type = "ResRef")
    @get:JsonProperty("ScriptDamaged")
    @set:JsonProperty("ScriptDamaged")
    var scriptDamaged: String? = null

    @get:NwnField(name = "FirstName", type = "CExoLocString")
    @get:JsonProperty("FirstName")
    @set:JsonProperty("FirstName")
    var firstName: CExoLocString? = null

    @get:NwnField(name = "ArmorPart_RFoot", type = "BYTE")
    @get:JsonProperty("ArmorPartRFoot")
    @set:JsonProperty("ArmorPartRFoot")
    var armorPartRFoot: UByte? = null

    @get:NwnField(name = "BodyPart_LBicep", type = "BYTE")
    @get:JsonProperty("BodyPartLBicep")
    @set:JsonProperty("BodyPartLBicep")
    var bodyPartLBicep: UByte? = null

    @get:NwnField(name = "XPosition", type = "FLOAT")
    @get:JsonProperty("XPosition")
    @set:JsonProperty("XPosition")
    var xPosition: Float? = null

    @get:NwnField(name = "ScriptUserDefine", type = "ResRef")
    @get:JsonProperty("ScriptUserDefine")
    @set:JsonProperty("ScriptUserDefine")
    var scriptUserDefine: String? = null

    @get:NwnField(name = "ChallengeRating", type = "FLOAT")
    @get:JsonProperty("ChallengeRating")
    @set:JsonProperty("ChallengeRating")
    var challengeRating: Float? = null

    @get:NwnField(name = "CurrentHitPoints", type = "SHORT")
    @get:JsonProperty("CurrentHitPoints")
    @set:JsonProperty("CurrentHitPoints")
    var currentHitPoints: Short? = null

    @get:NwnField(name = "BodyPart_LShin", type = "BYTE")
    @get:JsonProperty("BodyPartLShin")
    @set:JsonProperty("BodyPartLShin")
    var bodyPartLShin: UByte? = null

    @get:NwnField(name = "ZPosition", type = "FLOAT")
    @get:JsonProperty("ZPosition")
    @set:JsonProperty("ZPosition")
    var zPosition: Float? = null

    @get:NwnField(name = "ScriptOnNotice", type = "ResRef")
    @get:JsonProperty("ScriptOnNotice")
    @set:JsonProperty("ScriptOnNotice")
    var scriptOnNotice: String? = null

    @get:NwnField(name = "CRAdjust", type = "INT")
    @get:JsonProperty("CRAdjust")
    @set:JsonProperty("CRAdjust")
    var cRAdjust: Int? = null

    @get:NwnField(name = "ScriptHeartbeat", type = "ResRef")
    @get:JsonProperty("ScriptHeartbeat")
    @set:JsonProperty("ScriptHeartbeat")
    var scriptHeartbeat: String? = null

    @get:NwnField(name = "Race", type = "BYTE")
    @get:JsonProperty("Race")
    @set:JsonProperty("Race")
    var race: UByte? = null

    @get:NwnField(name = "Appearance_Type", type = "WORD")
    @get:JsonProperty("AppearanceType")
    @set:JsonProperty("AppearanceType")
    var appearanceType: UShort? = null

    @get:NwnField(name = "BodyPart_Neck", type = "BYTE")
    @get:JsonProperty("BodyPartNeck")
    @set:JsonProperty("BodyPartNeck")
    var bodyPartNeck: UByte? = null

    @get:NwnField(name = "Disarmable", type = "BYTE")
    @get:JsonProperty("Disarmable")
    @set:JsonProperty("Disarmable")
    var disarmable: UByte? = null

    @get:NwnField(name = "SkillList", type = "List", structType = 0)
    @get:JsonProperty("SkillList")
    @set:JsonProperty("SkillList")
    var skillList: List<GitSkill>? = null

    @get:NwnField(name = "willbonus", type = "SHORT")
    @get:JsonProperty("Willbonus")
    @set:JsonProperty("Willbonus")
    var willbonus: Short? = null

    @get:NwnField(name = "TemplateList", type = "List", structType = 5)
    @get:JsonProperty("TemplateList")
    @set:JsonProperty("TemplateList")
    var templateList: List<GitTemplate>? = null

    @get:NwnField(name = "GoodEvil", type = "BYTE")
    @get:JsonProperty("GoodEvil")
    @set:JsonProperty("GoodEvil")
    var goodEvil: UByte? = null

    @get:NwnField(name = "FeatList", type = "List", structType = 1)
    @get:JsonProperty("FeatList")
    @set:JsonProperty("FeatList")
    var featList: List<GitFeat>? = null

    @get:NwnField(name = "BodyPart_Pelvis", type = "BYTE")
    @get:JsonProperty("BodyPartPelvis")
    @set:JsonProperty("BodyPartPelvis")
    var bodyPartPelvis: UByte? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "PerceptionRange", type = "BYTE")
    @get:JsonProperty("PerceptionRange")
    @set:JsonProperty("PerceptionRange")
    var perceptionRange: UByte? = null

    @get:NwnField(name = "LawfulChaotic", type = "BYTE")
    @get:JsonProperty("LawfulChaotic")
    @set:JsonProperty("LawfulChaotic")
    var lawfulChaotic: UByte? = null

    @get:NwnField(name = "WalkRate", type = "INT")
    @get:JsonProperty("WalkRate")
    @set:JsonProperty("WalkRate")
    var walkRate: Int? = null

    @get:NwnField(name = "DecayTime", type = "DWORD")
    @get:JsonProperty("DecayTime")
    @set:JsonProperty("DecayTime")
    var decayTime: UInt? = null

    @get:NwnField(name = "YPosition", type = "FLOAT")
    @get:JsonProperty("YPosition")
    @set:JsonProperty("YPosition")
    var yPosition: Float? = null

    @get:NwnField(name = "Description", type = "CExoLocString")
    @get:JsonProperty("Description")
    @set:JsonProperty("Description")
    var description: CExoLocString? = null

    @get:NwnField(name = "Phenotype", type = "INT")
    @get:JsonProperty("Phenotype")
    @set:JsonProperty("Phenotype")
    var phenotype: Int? = null

    @get:NwnField(name = "Color_Tattoo2", type = "BYTE")
    @get:JsonProperty("ColorTattoo2")
    @set:JsonProperty("ColorTattoo2")
    var colorTattoo2: UByte? = null

    @get:NwnField(name = "ItemList", type = "List", indexedStructId = true)
    @get:JsonProperty("ItemList")
    @set:JsonProperty("ItemList")
    var itemList: List<GitItem>? = null

    @get:NwnField(name = "BodyPart_LShoul", type = "BYTE")
    @get:JsonProperty("BodyPartLShoul")
    @set:JsonProperty("BodyPartLShoul")
    var bodyPartLShoul: UByte? = null

    @get:NwnField(name = "BodyPart_Torso", type = "BYTE")
    @get:JsonProperty("BodyPartTorso")
    @set:JsonProperty("BodyPartTorso")
    var bodyPartTorso: UByte? = null

    @get:NwnField(name = "Plot", type = "BYTE")
    @get:JsonProperty("Plot")
    @set:JsonProperty("Plot")
    var plot: UByte? = null

    @get:NwnField(name = "Color_Tattoo1", type = "BYTE")
    @get:JsonProperty("ColorTattoo1")
    @set:JsonProperty("ColorTattoo1")
    var colorTattoo1: UByte? = null

    @get:NwnField(name = "IsPC", type = "BYTE")
    @get:JsonProperty("IsPC")
    @set:JsonProperty("IsPC")
    var isPC: UByte? = null

    @get:NwnField(name = "HitPoints", type = "SHORT")
    @get:JsonProperty("HitPoints")
    @set:JsonProperty("HitPoints")
    var hitPoints: Short? = null

    @get:NwnField(name = "SoundSetFile", type = "WORD")
    @get:JsonProperty("SoundSetFile")
    @set:JsonProperty("SoundSetFile")
    var soundSetFile: UShort? = null

    @get:NwnField(name = "refbonus", type = "SHORT")
    @get:JsonProperty("Refbonus")
    @set:JsonProperty("Refbonus")
    var refbonus: Short? = null

    @get:NwnField(name = "Wings", type = "BYTE")
    @get:JsonProperty("Wings")
    @set:JsonProperty("Wings")
    var wings: UByte? = null

    @get:NwnField(name = "ScriptDialogue", type = "ResRef")
    @get:JsonProperty("ScriptDialogue")
    @set:JsonProperty("ScriptDialogue")
    var scriptDialogue: String? = null

    @get:NwnField(name = "BodyPart_RBicep", type = "BYTE")
    @get:JsonProperty("BodyPartRBicep")
    @set:JsonProperty("BodyPartRBicep")
    var bodyPartRBicep: UByte? = null

    @get:NwnField(name = "LastName", type = "CExoLocString")
    @get:JsonProperty("LastName")
    @set:JsonProperty("LastName")
    var lastName: CExoLocString? = null

    @get:NwnField(name = "Equip_ItemList", type = "List")
    @get:JsonProperty("EquipItemList")
    @set:JsonProperty("EquipItemList")
    var equipItemList: List<GitEquipItem>? = null

    @get:NwnField(name = "ScriptOnBlocked", type = "ResRef")
    @get:JsonProperty("ScriptOnBlocked")
    @set:JsonProperty("ScriptOnBlocked")
    var scriptOnBlocked: String? = null

    @get:NwnField(name = "BodyPart_RShoul", type = "BYTE")
    @get:JsonProperty("BodyPartRShoul")
    @set:JsonProperty("BodyPartRShoul")
    var bodyPartRShoul: UByte? = null

    @get:NwnField(name = "BodyPart_Belt", type = "BYTE")
    @get:JsonProperty("BodyPartBelt")
    @set:JsonProperty("BodyPartBelt")
    var bodyPartBelt: UByte? = null

    @get:NwnField(name = "BodyPart_LFoot", type = "BYTE")
    @get:JsonProperty("BodyPartLFoot")
    @set:JsonProperty("BodyPartLFoot")
    var bodyPartLFoot: UByte? = null

    @get:NwnField(name = "BodyPart_RHand", type = "BYTE")
    @get:JsonProperty("BodyPartRHand")
    @set:JsonProperty("BodyPartRHand")
    var bodyPartRHand: UByte? = null

    @get:NwnField(name = "Appearance_Head", type = "BYTE")
    @get:JsonProperty("AppearanceHead")
    @set:JsonProperty("AppearanceHead")
    var appearanceHead: UByte? = null

    @get:NwnField(name = "BodyPart_LFArm", type = "BYTE")
    @get:JsonProperty("BodyPartLFArm")
    @set:JsonProperty("BodyPartLFArm")
    var bodyPartLFArm: UByte? = null

    @get:NwnField(name = "Color_Hair", type = "BYTE")
    @get:JsonProperty("ColorHair")
    @set:JsonProperty("ColorHair")
    var colorHair: UByte? = null

    @get:NwnField(name = "BodyPart_LThigh", type = "BYTE")
    @get:JsonProperty("BodyPartLThigh")
    @set:JsonProperty("BodyPartLThigh")
    var bodyPartLThigh: UByte? = null

    @get:NwnField(name = "fortbonus", type = "SHORT")
    @get:JsonProperty("Fortbonus")
    @set:JsonProperty("Fortbonus")
    var fortbonus: Short? = null

    @get:NwnField(name = "ScriptSpawn", type = "ResRef")
    @get:JsonProperty("ScriptSpawn")
    @set:JsonProperty("ScriptSpawn")
    var scriptSpawn: String? = null

    @get:NwnField(name = "Color_Skin", type = "BYTE")
    @get:JsonProperty("ColorSkin")
    @set:JsonProperty("ColorSkin")
    var colorSkin: UByte? = null

    @get:NwnField(name = "Tail", type = "BYTE")
    @get:JsonProperty("Tail")
    @set:JsonProperty("Tail")
    var tail: UByte? = null

    @get:NwnField(name = "Comment", type = "CExoString", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "VisualTransform", type = "Struct", structType = 6)
    @get:JsonProperty("VisualTransform")
    @set:JsonProperty("VisualTransform")
    var visualTransform: GitVisualTransform? = null

    @get:NwnField(name = "VisTransformList", type = "List", structType = 6)
    @get:JsonProperty("VisTransformList")
    @set:JsonProperty("VisTransformList")
    var visTransformList: List<GitVisTransform>? = null
}

class GitVisTransform {

    @get:NwnField(name = "ScaleZ", type = "Struct", structType = 0)
    @get:JsonProperty("ScaleZ")
    @set:JsonProperty("ScaleZ")
    var scaleZ: GitScale? = null

    @get:NwnField(name = "TranslateX", type = "Struct", structType = 0)
    @get:JsonProperty("TranslateX")
    @set:JsonProperty("TranslateX")
    var translateX: GitTranslate? = null

    @get:NwnField(name = "TranslateY", type = "Struct", structType = 0)
    @get:JsonProperty("TranslateY")
    @set:JsonProperty("TranslateY")
    var translateY: GitTranslate? = null

    @get:NwnField(name = "ScaleX", type = "Struct", structType = 0)
    @get:JsonProperty("ScaleX")
    @set:JsonProperty("ScaleX")
    var scaleX: GitScale? = null

    @get:NwnField(name = "ScaleY", type = "Struct", structType = 0)
    @get:JsonProperty("ScaleY")
    @set:JsonProperty("ScaleY")
    var scaleY: GitScale? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "Scope", type = "INT")
    @get:JsonProperty("Scope")
    @set:JsonProperty("Scope")
    var scope: Int? = null

    @get:NwnField(name = "RotateX", type = "Struct", structType = 0)
    @get:JsonProperty("RotateX")
    @set:JsonProperty("RotateX")
    var rotateX: GitRotate? = null

    @get:NwnField(name = "RotateZ", type = "Struct", structType = 0)
    @get:JsonProperty("RotateZ")
    @set:JsonProperty("RotateZ")
    var rotateZ: GitRotate? = null

    @get:NwnField(name = "TranslateZ", type = "Struct", structType = 0)
    @get:JsonProperty("TranslateZ")
    @set:JsonProperty("TranslateZ")
    var translateZ: GitTranslate? = null

    @get:NwnField(name = "AnimationSpeed", type = "Struct", structType = 0)
    @get:JsonProperty("AnimationSpeed")
    @set:JsonProperty("AnimationSpeed")
    var animationSpeed: GitAnimationSpeed? = null

    @get:NwnField(name = "RotateY", type = "Struct", structType = 0)
    @get:JsonProperty("RotateY")
    @set:JsonProperty("RotateY")
    var rotateY: GitRotate? = null
}

class GitAnimationSpeed {

    @get:NwnField(name = "TimerType", type = "INT")
    @get:JsonProperty("TimerType")
    @set:JsonProperty("TimerType")
    var timerType: Int? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "ValueTo", type = "FLOAT")
    @get:JsonProperty("ValueTo")
    @set:JsonProperty("ValueTo")
    var valueTo: Float? = null

    @get:NwnField(name = "LerpType", type = "INT")
    @get:JsonProperty("LerpType")
    @set:JsonProperty("LerpType")
    var lerpType: Int? = null
}

class GitRotate {

    @get:NwnField(name = "TimerType", type = "INT")
    @get:JsonProperty("TimerType")
    @set:JsonProperty("TimerType")
    var timerType: Int? = null

    @get:NwnField(name = "LerpType", type = "INT")
    @get:JsonProperty("LerpType")
    @set:JsonProperty("LerpType")
    var lerpType: Int? = null

    @get:NwnField(name = "ValueTo", type = "FLOAT")
    @get:JsonProperty("ValueTo")
    @set:JsonProperty("ValueTo")
    var valueTo: Float? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GitScale {

    @get:NwnField(name = "ValueTo", type = "FLOAT")
    @get:JsonProperty("ValueTo")
    @set:JsonProperty("ValueTo")
    var valueTo: Float? = null

    @get:NwnField(name = "LerpType", type = "INT")
    @get:JsonProperty("LerpType")
    @set:JsonProperty("LerpType")
    var lerpType: Int? = null

    @get:NwnField(name = "TimerType", type = "INT")
    @get:JsonProperty("TimerType")
    @set:JsonProperty("TimerType")
    var timerType: Int? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GitDoor {

    @get:NwnField(name = "PortraitId", type = "WORD")
    @get:JsonProperty("PortraitId")
    @set:JsonProperty("PortraitId")
    var portraitId: UShort? = null

    @get:NwnField(name = "CloseLockDC", type = "BYTE")
    @get:JsonProperty("CloseLockDC")
    @set:JsonProperty("CloseLockDC")
    var closeLockDC: UByte? = null

    @get:NwnField(name = "Appearance", type = "DWORD")
    @get:JsonProperty("Appearance")
    @set:JsonProperty("Appearance")
    var appearance: UInt? = null

    @get:NwnField(name = "Hardness", type = "BYTE")
    @get:JsonProperty("Hardness")
    @set:JsonProperty("Hardness")
    var hardness: UByte? = null

    @get:NwnField(name = "Plot", type = "BYTE")
    @get:JsonProperty("Plot")
    @set:JsonProperty("Plot")
    var plot: UByte? = null

    @get:NwnField(name = "DisarmDC", type = "BYTE")
    @get:JsonProperty("DisarmDC")
    @set:JsonProperty("DisarmDC")
    var disarmDC: UByte? = null

    @get:NwnField(name = "TrapDetectable", type = "BYTE")
    @get:JsonProperty("TrapDetectable")
    @set:JsonProperty("TrapDetectable")
    var trapDetectable: UByte? = null

    @get:NwnField(name = "OnClosed", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnClosed")
    @set:JsonProperty("OnClosed")
    var onClosed: String? = null

    @get:NwnField(name = "Faction", type = "DWORD")
    @get:JsonProperty("Faction")
    @set:JsonProperty("Faction")
    var faction: UInt? = null

    @get:NwnField(name = "OnOpen", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnOpen")
    @set:JsonProperty("OnOpen")
    var onOpen: String? = null

    @get:NwnField(name = "CurrentHP", type = "SHORT")
    @get:JsonProperty("CurrentHP")
    @set:JsonProperty("CurrentHP")
    var currentHP: Short? = null

    @get:NwnField(name = "Will", type = "BYTE")
    @get:JsonProperty("Will")
    @set:JsonProperty("Will")
    var will: UByte? = null

    @get:NwnField(name = "LinkedTo", type = "CExoString")
    @get:JsonProperty("LinkedTo")
    @set:JsonProperty("LinkedTo")
    var linkedTo: String? = null

    @get:NwnField(name = "AutoRemoveKey", type = "BYTE")
    @get:JsonProperty("AutoRemoveKey")
    @set:JsonProperty("AutoRemoveKey")
    var autoRemoveKey: UByte? = null

    @get:NwnField(name = "KeyName", type = "CExoString", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("KeyName")
    @set:JsonProperty("KeyName")
    var keyName: String? = null

    @get:NwnField(name = "KeyRequired", type = "BYTE")
    @get:JsonProperty("KeyRequired")
    @set:JsonProperty("KeyRequired")
    var keyRequired: UByte? = null

    @get:NwnField(name = "LocName", type = "CExoLocString")
    @get:JsonProperty("LocName")
    @set:JsonProperty("LocName")
    var locName: CExoLocString? = null

    @get:NwnField(name = "LoadScreenID", type = "WORD")
    @get:JsonProperty("LoadScreenID")
    @set:JsonProperty("LoadScreenID")
    var loadScreenID: UShort? = null

    @get:NwnField(name = "OnTrapTriggered", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnTrapTriggered")
    @set:JsonProperty("OnTrapTriggered")
    var onTrapTriggered: String? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "Bearing", type = "FLOAT")
    @get:JsonProperty("Bearing")
    @set:JsonProperty("Bearing")
    var bearing: Float? = null

    @get:NwnField(name = "Ref", type = "BYTE")
    @get:JsonProperty("Ref")
    @set:JsonProperty("Ref")
    var ref: UByte? = null

    @get:NwnField(name = "OnHeartbeat", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnHeartbeat")
    @set:JsonProperty("OnHeartbeat")
    var onHeartbeat: String? = null

    @get:NwnField(name = "OnDeath", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnDeath")
    @set:JsonProperty("OnDeath")
    var onDeath: String? = null

    @get:NwnField(name = "AnimationState", type = "BYTE")
    @get:JsonProperty("AnimationState")
    @set:JsonProperty("AnimationState")
    var animationState: UByte? = null

    @get:NwnField(name = "TrapDisarmable", type = "BYTE")
    @get:JsonProperty("TrapDisarmable")
    @set:JsonProperty("TrapDisarmable")
    var trapDisarmable: UByte? = null

    @get:NwnField(name = "OnUnlock", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnUnlock")
    @set:JsonProperty("OnUnlock")
    var onUnlock: String? = null

    @get:NwnField(name = "HP", type = "SHORT")
    @get:JsonProperty("HP")
    @set:JsonProperty("HP")
    var hP: Short? = null

    @get:NwnField(name = "OnUserDefined", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnUserDefined")
    @set:JsonProperty("OnUserDefined")
    var onUserDefined: String? = null

    @get:NwnField(name = "Z", type = "FLOAT")
    @get:JsonProperty("Z")
    @set:JsonProperty("Z")
    var z: Float? = null

    @get:NwnField(name = "GenericType_New", type = "DWORD")
    @get:JsonProperty("GenericTypeNew")
    @set:JsonProperty("GenericTypeNew")
    var genericTypeNew: UInt? = null

    @get:NwnField(name = "OnFailToOpen", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnFailToOpen")
    @set:JsonProperty("OnFailToOpen")
    var onFailToOpen: String? = null

    @get:NwnField(name = "TrapFlag", type = "BYTE")
    @get:JsonProperty("TrapFlag")
    @set:JsonProperty("TrapFlag")
    var trapFlag: UByte? = null

    @get:NwnField(name = "OnClick", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnClick")
    @set:JsonProperty("OnClick")
    var onClick: String? = null

    @get:NwnField(name = "TrapDetectDC", type = "BYTE")
    @get:JsonProperty("TrapDetectDC")
    @set:JsonProperty("TrapDetectDC")
    var trapDetectDC: UByte? = null

    @get:NwnField(name = "Conversation", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("Conversation")
    @set:JsonProperty("Conversation")
    var conversation: String? = null

    @get:NwnField(name = "OnSpellCastAt", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnSpellCastAt")
    @set:JsonProperty("OnSpellCastAt")
    var onSpellCastAt: String? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "TrapType", type = "BYTE")
    @get:JsonProperty("TrapType")
    @set:JsonProperty("TrapType")
    var trapType: UByte? = null

    @get:NwnField(name = "OpenLockDC", type = "BYTE")
    @get:JsonProperty("OpenLockDC")
    @set:JsonProperty("OpenLockDC")
    var openLockDC: UByte? = null

    @get:NwnField(name = "OnMeleeAttacked", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnMeleeAttacked")
    @set:JsonProperty("OnMeleeAttacked")
    var onMeleeAttacked: String? = null

    @get:NwnField(name = "OnDamaged", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnDamaged")
    @set:JsonProperty("OnDamaged")
    var onDamaged: String? = null

    @get:NwnField(name = "OnLock", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnLock")
    @set:JsonProperty("OnLock")
    var onLock: String? = null

    @get:NwnField(name = "Lockable", type = "BYTE")
    @get:JsonProperty("Lockable")
    @set:JsonProperty("Lockable")
    var lockable: UByte? = null

    @get:NwnField(name = "LinkedToFlags", type = "BYTE")
    @get:JsonProperty("LinkedToFlags")
    @set:JsonProperty("LinkedToFlags")
    var linkedToFlags: UByte? = null

    @get:NwnField(name = "OnDisarm", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnDisarm")
    @set:JsonProperty("OnDisarm")
    var onDisarm: String? = null

    @get:NwnField(name = "Locked", type = "BYTE")
    @get:JsonProperty("Locked")
    @set:JsonProperty("Locked")
    var locked: UByte? = null

    @get:NwnField(name = "Y", type = "FLOAT")
    @get:JsonProperty("Y")
    @set:JsonProperty("Y")
    var y: Float? = null

    @get:NwnField(name = "Interruptable", type = "BYTE")
    @get:JsonProperty("Interruptable")
    @set:JsonProperty("Interruptable")
    var interruptable: UByte? = null

    @get:NwnField(name = "X", type = "FLOAT")
    @get:JsonProperty("X")
    @set:JsonProperty("X")
    var x: Float? = null

    @get:NwnField(name = "TrapOneShot", type = "BYTE")
    @get:JsonProperty("TrapOneShot")
    @set:JsonProperty("TrapOneShot")
    var trapOneShot: UByte? = null

    @get:NwnField(name = "Fort", type = "BYTE")
    @get:JsonProperty("Fort")
    @set:JsonProperty("Fort")
    var fort: UByte? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<GitVarTable>? = null

    @get:NwnField(name = "GenericType", type = "BYTE")
    @get:JsonProperty("GenericType")
    @set:JsonProperty("GenericType")
    var genericType: UByte? = null

    @get:NwnField(name = "Description", type = "CExoLocString")
    @get:JsonProperty("Description")
    @set:JsonProperty("Description")
    var description: CExoLocString? = null

    @get:NwnField(name = "Comment", type = "CExoString", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "VisualTransform", type = "Struct", structType = 6)
    @get:JsonProperty("VisualTransform")
    @set:JsonProperty("VisualTransform")
    var visualTransform: GitVisualTransform? = null
}

class GitEncounter {

    @get:NwnField(name = "OnExit", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnExit")
    @set:JsonProperty("OnExit")
    var onExit: String? = null

    @get:NwnField(name = "MaxCreatures", type = "INT")
    @get:JsonProperty("MaxCreatures")
    @set:JsonProperty("MaxCreatures")
    var maxCreatures: Int? = null

    @get:NwnField(name = "Respawns", type = "INT")
    @get:JsonProperty("Respawns")
    @set:JsonProperty("Respawns")
    var respawns: Int? = null

    @get:NwnField(name = "Geometry", type = "List", structType = 1)
    @get:JsonProperty("Geometry")
    @set:JsonProperty("Geometry")
    var geometry: List<GitGeometry>? = null

    @get:NwnField(name = "OnUserDefined", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnUserDefined")
    @set:JsonProperty("OnUserDefined")
    var onUserDefined: String? = null

    @get:NwnField(name = "ZPosition", type = "FLOAT")
    @get:JsonProperty("ZPosition")
    @set:JsonProperty("ZPosition")
    var zPosition: Float? = null

    @get:NwnField(name = "LocalizedName", type = "CExoLocString")
    @get:JsonProperty("LocalizedName")
    @set:JsonProperty("LocalizedName")
    var localizedName: CExoLocString? = null

    @get:NwnField(name = "SpawnOption", type = "INT")
    @get:JsonProperty("SpawnOption")
    @set:JsonProperty("SpawnOption")
    var spawnOption: Int? = null

    @get:NwnField(name = "Difficulty", type = "INT")
    @get:JsonProperty("Difficulty")
    @set:JsonProperty("Difficulty")
    var difficulty: Int? = null

    @get:NwnField(name = "DifficultyIndex", type = "INT")
    @get:JsonProperty("DifficultyIndex")
    @set:JsonProperty("DifficultyIndex")
    var difficultyIndex: Int? = null

    @get:NwnField(name = "XPosition", type = "FLOAT")
    @get:JsonProperty("XPosition")
    @set:JsonProperty("XPosition")
    var xPosition: Float? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "Reset", type = "BYTE")
    @get:JsonProperty("Reset")
    @set:JsonProperty("Reset")
    var reset: UByte? = null

    @get:NwnField(name = "OnExhausted", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnExhausted")
    @set:JsonProperty("OnExhausted")
    var onExhausted: String? = null

    @get:NwnField(name = "RecCreatures", type = "INT")
    @get:JsonProperty("RecCreatures")
    @set:JsonProperty("RecCreatures")
    var recCreatures: Int? = null

    @get:NwnField(name = "PlayerOnly", type = "BYTE")
    @get:JsonProperty("PlayerOnly")
    @set:JsonProperty("PlayerOnly")
    var playerOnly: UByte? = null

    @get:NwnField(name = "Faction", type = "DWORD")
    @get:JsonProperty("Faction")
    @set:JsonProperty("Faction")
    var faction: UInt? = null

    @get:NwnField(name = "OnEntered", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnEntered")
    @set:JsonProperty("OnEntered")
    var onEntered: String? = null

    @get:NwnField(name = "OnHeartbeat", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnHeartbeat")
    @set:JsonProperty("OnHeartbeat")
    var onHeartbeat: String? = null

    @get:NwnField(name = "ResetTime", type = "INT")
    @get:JsonProperty("ResetTime")
    @set:JsonProperty("ResetTime")
    var resetTime: Int? = null

    @get:NwnField(name = "CreatureList", type = "List", structType = 0)
    @get:JsonProperty("CreatureList")
    @set:JsonProperty("CreatureList")
    var creatureList: List<GitCreature>? = null

    @get:NwnField(name = "SpawnPointList", type = "List", structType = 2)
    @get:JsonProperty("SpawnPointList")
    @set:JsonProperty("SpawnPointList")
    var spawnPointList: List<GitSpawnPoint>? = null

    @get:NwnField(name = "Active", type = "BYTE")
    @get:JsonProperty("Active")
    @set:JsonProperty("Active")
    var active: UByte? = null

    @get:NwnField(name = "YPosition", type = "FLOAT")
    @get:JsonProperty("YPosition")
    @set:JsonProperty("YPosition")
    var yPosition: Float? = null

    @get:NwnField(name = "Comment", type = "CExoString", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<GitVarTable>? = null
}

interface HasStructId {
    var structId: UInt?
}

class GitEquipItem : HasStructId {

    @get:JsonProperty("StructId")
    @set:JsonProperty("StructId")
    override var structId: UInt? = null

    @get:NwnField(name = "AddCost", type = "DWORD")
    @get:JsonProperty("AddCost")
    @set:JsonProperty("AddCost")
    var addCost: UInt? = null

    @get:NwnField(name = "XPosition", type = "FLOAT")
    @get:JsonProperty("XPosition")
    @set:JsonProperty("XPosition")
    var xPosition: Float? = null

    @get:NwnField(name = "Cost", type = "DWORD")
    @get:JsonProperty("Cost")
    @set:JsonProperty("Cost")
    var cost: UInt? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "YPosition", type = "FLOAT")
    @get:JsonProperty("YPosition")
    @set:JsonProperty("YPosition")
    var yPosition: Float? = null

    @get:NwnField(name = "Plot", type = "BYTE")
    @get:JsonProperty("Plot")
    @set:JsonProperty("Plot")
    var plot: UByte? = null

    @get:NwnField(name = "Cursed", type = "BYTE")
    @get:JsonProperty("Cursed")
    @set:JsonProperty("Cursed")
    var cursed: UByte? = null

    @get:NwnField(name = "ArmorPart_LHand", type = "BYTE")
    @get:JsonProperty("ArmorPartLHand")
    @set:JsonProperty("ArmorPartLHand")
    var armorPartLHand: UByte? = null

    @get:NwnField(name = "ArmorPart_Neck", type = "BYTE")
    @get:JsonProperty("ArmorPartNeck")
    @set:JsonProperty("ArmorPartNeck")
    var armorPartNeck: UByte? = null

    @get:NwnField(name = "ArmorPart_LThigh", type = "BYTE")
    @get:JsonProperty("ArmorPartLThigh")
    @set:JsonProperty("ArmorPartLThigh")
    var armorPartLThigh: UByte? = null

    @get:NwnField(name = "ArmorPart_Robe", type = "BYTE")
    @get:JsonProperty("ArmorPartRobe")
    @set:JsonProperty("ArmorPartRobe")
    var armorPartRobe: UByte? = null

    @get:NwnField(name = "ArmorPart_LShoul", type = "BYTE")
    @get:JsonProperty("ArmorPartLShoul")
    @set:JsonProperty("ArmorPartLShoul")
    var armorPartLShoul: UByte? = null

    @get:NwnField(name = "ArmorPart_Belt", type = "BYTE")
    @get:JsonProperty("ArmorPartBelt")
    @set:JsonProperty("ArmorPartBelt")
    var armorPartBelt: UByte? = null

    @get:NwnField(name = "Cloth1Color", type = "BYTE")
    @get:JsonProperty("Cloth1Color")
    @set:JsonProperty("Cloth1Color")
    var cloth1Color: UByte? = null

    @get:NwnField(name = "Charges", type = "BYTE")
    @get:JsonProperty("Charges")
    @set:JsonProperty("Charges")
    var charges: UByte? = null

    @get:NwnField(name = "ZPosition", type = "FLOAT")
    @get:JsonProperty("ZPosition")
    @set:JsonProperty("ZPosition")
    var zPosition: Float? = null

    @get:NwnField(name = "DescIdentified", type = "CExoLocString")
    @get:JsonProperty("DescIdentified")
    @set:JsonProperty("DescIdentified")
    var descIdentified: CExoLocString? = null

    @get:NwnField(name = "ArmorPart_LFArm", type = "BYTE")
    @get:JsonProperty("ArmorPartLFArm")
    @set:JsonProperty("ArmorPartLFArm")
    var armorPartLFArm: UByte? = null

    @get:NwnField(name = "BaseItem", type = "INT")
    @get:JsonProperty("BaseItem")
    @set:JsonProperty("BaseItem")
    var baseItem: Int? = null

    @get:NwnField(name = "Description", type = "CExoLocString")
    @get:JsonProperty("Description")
    @set:JsonProperty("Description")
    var description: CExoLocString? = null

    @get:NwnField(name = "Stolen", type = "BYTE")
    @get:JsonProperty("Stolen")
    @set:JsonProperty("Stolen")
    var stolen: UByte? = null

    @get:NwnField(name = "ArmorPart_RHand", type = "BYTE")
    @get:JsonProperty("ArmorPartRHand")
    @set:JsonProperty("ArmorPartRHand")
    var armorPartRHand: UByte? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "PropertiesList", type = "List", structType = 0)
    @get:JsonProperty("PropertiesList")
    @set:JsonProperty("PropertiesList")
    var propertiesList: List<GitProperties>? = null

    @get:NwnField(name = "ArmorPart_Torso", type = "BYTE")
    @get:JsonProperty("ArmorPartTorso")
    @set:JsonProperty("ArmorPartTorso")
    var armorPartTorso: UByte? = null

    @get:NwnField(name = "YOrientation", type = "FLOAT")
    @get:JsonProperty("YOrientation")
    @set:JsonProperty("YOrientation")
    var yOrientation: Float? = null

    @get:NwnField(name = "Dropable", type = "BYTE")
    @get:JsonProperty("Dropable")
    @set:JsonProperty("Dropable")
    var dropable: UByte? = null

    @get:NwnField(name = "Identified", type = "BYTE")
    @get:JsonProperty("Identified")
    @set:JsonProperty("Identified")
    var identified: UByte? = null

    @get:NwnField(name = "LocalizedName", type = "CExoLocString")
    @get:JsonProperty("LocalizedName")
    @set:JsonProperty("LocalizedName")
    var localizedName: CExoLocString? = null

    @get:NwnField(name = "Metal1Color", type = "BYTE")
    @get:JsonProperty("Metal1Color")
    @set:JsonProperty("Metal1Color")
    var metal1Color: UByte? = null

    @get:NwnField(name = "Leather1Color", type = "BYTE")
    @get:JsonProperty("Leather1Color")
    @set:JsonProperty("Leather1Color")
    var leather1Color: UByte? = null

    @get:NwnField(name = "XOrientation", type = "FLOAT")
    @get:JsonProperty("XOrientation")
    @set:JsonProperty("XOrientation")
    var xOrientation: Float? = null

    @get:NwnField(name = "ArmorPart_RShin", type = "BYTE")
    @get:JsonProperty("ArmorPartRShin")
    @set:JsonProperty("ArmorPartRShin")
    var armorPartRShin: UByte? = null

    @get:NwnField(name = "ArmorPart_LFoot", type = "BYTE")
    @get:JsonProperty("ArmorPartLFoot")
    @set:JsonProperty("ArmorPartLFoot")
    var armorPartLFoot: UByte? = null

    @get:NwnField(name = "Metal2Color", type = "BYTE")
    @get:JsonProperty("Metal2Color")
    @set:JsonProperty("Metal2Color")
    var metal2Color: UByte? = null

    @get:NwnField(name = "ArmorPart_RBicep", type = "BYTE")
    @get:JsonProperty("ArmorPartRBicep")
    @set:JsonProperty("ArmorPartRBicep")
    var armorPartRBicep: UByte? = null

    @get:NwnField(name = "ArmorPart_RFoot", type = "BYTE")
    @get:JsonProperty("ArmorPartRFoot")
    @set:JsonProperty("ArmorPartRFoot")
    var armorPartRFoot: UByte? = null

    @get:NwnField(name = "ArmorPart_LBicep", type = "BYTE")
    @get:JsonProperty("ArmorPartLBicep")
    @set:JsonProperty("ArmorPartLBicep")
    var armorPartLBicep: UByte? = null

    @get:NwnField(name = "ModelPart1", type = "BYTE")
    @get:JsonProperty("ModelPart1")
    @set:JsonProperty("ModelPart1")
    var modelPart1: UByte? = null

    @get:NwnField(name = "ModelPart2", type = "BYTE")
    @get:JsonProperty("ModelPart2")
    @set:JsonProperty("ModelPart2")
    var modelPart2: UByte? = null

    @get:NwnField(name = "ArmorPart_Pelvis", type = "BYTE")
    @get:JsonProperty("ArmorPartPelvis")
    @set:JsonProperty("ArmorPartPelvis")
    var armorPartPelvis: UByte? = null

    @get:NwnField(name = "ModelPart3", type = "BYTE")
    @get:JsonProperty("ModelPart3")
    @set:JsonProperty("ModelPart3")
    var modelPart3: UByte? = null

    @get:NwnField(name = "Cloth2Color", type = "BYTE")
    @get:JsonProperty("Cloth2Color")
    @set:JsonProperty("Cloth2Color")
    var cloth2Color: UByte? = null

    @get:NwnField(name = "ArmorPart_LShin", type = "BYTE")
    @get:JsonProperty("ArmorPartLShin")
    @set:JsonProperty("ArmorPartLShin")
    var armorPartLShin: UByte? = null

    @get:NwnField(name = "StackSize", type = "WORD")
    @get:JsonProperty("StackSize")
    @set:JsonProperty("StackSize")
    var stackSize: UShort? = null

    @get:NwnField(name = "Leather2Color", type = "BYTE")
    @get:JsonProperty("Leather2Color")
    @set:JsonProperty("Leather2Color")
    var leather2Color: UByte? = null

    @get:NwnField(name = "ArmorPart_RShoul", type = "BYTE")
    @get:JsonProperty("ArmorPartRShoul")
    @set:JsonProperty("ArmorPartRShoul")
    var armorPartRShoul: UByte? = null

    @get:NwnField(name = "ArmorPart_RFArm", type = "BYTE")
    @get:JsonProperty("ArmorPartRFArm")
    @set:JsonProperty("ArmorPartRFArm")
    var armorPartRFArm: UByte? = null

    @get:NwnField(name = "ArmorPart_RThigh", type = "BYTE")
    @get:JsonProperty("ArmorPartRThigh")
    @set:JsonProperty("ArmorPartRThigh")
    var armorPartRThigh: UByte? = null

    @get:NwnField(name = "Pickpocketable", type = "BYTE")
    @get:JsonProperty("Pickpocketable")
    @set:JsonProperty("Pickpocketable")
    var pickpocketable: UByte? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GitFeat {

    @get:NwnField(name = "Feat", type = "WORD")
    @get:JsonProperty("Feat")
    @set:JsonProperty("Feat")
    var feat: UShort? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GitGeometry {

    @get:NwnField(name = "PointY", type = "FLOAT")
    @get:JsonProperty("PointY")
    @set:JsonProperty("PointY")
    var pointY: Float? = null

    @get:NwnField(name = "Y", type = "FLOAT")
    @get:JsonProperty("Y")
    @set:JsonProperty("Y")
    var y: Float? = null

    @get:NwnField(name = "PointZ", type = "FLOAT")
    @get:JsonProperty("PointZ")
    @set:JsonProperty("PointZ")
    var pointZ: Float? = null

    @get:NwnField(name = "Z", type = "FLOAT")
    @get:JsonProperty("Z")
    @set:JsonProperty("Z")
    var z: Float? = null

    @get:NwnField(name = "X", type = "FLOAT")
    @get:JsonProperty("X")
    @set:JsonProperty("X")
    var x: Float? = null

    @get:NwnField(name = "PointX", type = "FLOAT")
    @get:JsonProperty("PointX")
    @set:JsonProperty("PointX")
    var pointX: Float? = null

    @get:NwnField(name = "Comment", type = "CExoString", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GitItem {

    @get:NwnField(name = "XOrientation", type = "FLOAT")
    @get:JsonProperty("XOrientation")
    @set:JsonProperty("XOrientation")
    var xOrientation: Float? = null

    @get:NwnField(name = "AddCost", type = "DWORD")
    @get:JsonProperty("AddCost")
    @set:JsonProperty("AddCost")
    var addCost: UInt? = null

    @get:NwnField(name = "YOrientation", type = "FLOAT")
    @get:JsonProperty("YOrientation")
    @set:JsonProperty("YOrientation")
    var yOrientation: Float? = null

    @get:NwnField(name = "YPosition", type = "FLOAT")
    @get:JsonProperty("YPosition")
    @set:JsonProperty("YPosition")
    var yPosition: Float? = null

    @get:NwnField(name = "Metal1Color", type = "BYTE")
    @get:JsonProperty("Metal1Color")
    @set:JsonProperty("Metal1Color")
    var metal1Color: UByte? = null

    @get:NwnField(name = "Charges", type = "BYTE")
    @get:JsonProperty("Charges")
    @set:JsonProperty("Charges")
    var charges: UByte? = null

    @get:NwnField(name = "ModelPart2", type = "BYTE")
    @get:JsonProperty("ModelPart2")
    @set:JsonProperty("ModelPart2")
    var modelPart2: UByte? = null

    @get:NwnField(name = "LocalizedName", type = "CExoLocString")
    @get:JsonProperty("LocalizedName")
    @set:JsonProperty("LocalizedName")
    var localizedName: CExoLocString? = null

    @get:NwnField(name = "BaseItem", type = "INT")
    @get:JsonProperty("BaseItem")
    @set:JsonProperty("BaseItem")
    var baseItem: Int? = null

    @get:NwnField(name = "Plot", type = "BYTE")
    @get:JsonProperty("Plot")
    @set:JsonProperty("Plot")
    var plot: UByte? = null

    @get:NwnField(name = "Leather2Color", type = "BYTE")
    @get:JsonProperty("Leather2Color")
    @set:JsonProperty("Leather2Color")
    var leather2Color: UByte? = null

    @get:NwnField(name = "XPosition", type = "FLOAT")
    @get:JsonProperty("XPosition")
    @set:JsonProperty("XPosition")
    var xPosition: Float? = null

    @get:NwnField(name = "PropertiesList", type = "List", structType = 0)
    @get:JsonProperty("PropertiesList")
    @set:JsonProperty("PropertiesList")
    var propertiesList: List<GitProperties>? = null

    @get:NwnField(name = "ArmorPart_Robe", type = "BYTE")
    @get:JsonProperty("ArmorPartRobe")
    @set:JsonProperty("ArmorPartRobe")
    var armorPartRobe: UByte? = null

    @get:NwnField(name = "DescIdentified", type = "CExoLocString")
    @get:JsonProperty("DescIdentified")
    @set:JsonProperty("DescIdentified")
    var descIdentified: CExoLocString? = null

    @get:NwnField(name = "Cost", type = "DWORD")
    @get:JsonProperty("Cost")
    @set:JsonProperty("Cost")
    var cost: UInt? = null

    @get:NwnField(name = "Cursed", type = "BYTE")
    @get:JsonProperty("Cursed")
    @set:JsonProperty("Cursed")
    var cursed: UByte? = null

    @get:NwnField(name = "Repos_Posy", type = "WORD")
    @get:JsonProperty("ReposPosy")
    @set:JsonProperty("ReposPosy")
    var reposPosy: UShort? = null

    @get:NwnField(name = "ZPosition", type = "FLOAT")
    @get:JsonProperty("ZPosition")
    @set:JsonProperty("ZPosition")
    var zPosition: Float? = null

    @get:NwnField(name = "Repos_PosX", type = "WORD")
    @get:JsonProperty("ReposPosX")
    @set:JsonProperty("ReposPosX")
    var reposPosX: UShort? = null

    @get:NwnField(name = "ArmorPart_Torso", type = "BYTE")
    @get:JsonProperty("ArmorPartTorso")
    @set:JsonProperty("ArmorPartTorso")
    var armorPartTorso: UByte? = null

    @get:NwnField(name = "ModelPart1", type = "BYTE")
    @get:JsonProperty("ModelPart1")
    @set:JsonProperty("ModelPart1")
    var modelPart1: UByte? = null

    @get:NwnField(name = "Metal2Color", type = "BYTE")
    @get:JsonProperty("Metal2Color")
    @set:JsonProperty("Metal2Color")
    var metal2Color: UByte? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "Description", type = "CExoLocString")
    @get:JsonProperty("Description")
    @set:JsonProperty("Description")
    var description: CExoLocString? = null

    @get:NwnField(name = "Identified", type = "BYTE")
    @get:JsonProperty("Identified")
    @set:JsonProperty("Identified")
    var identified: UByte? = null

    @get:NwnField(name = "ArmorPart_RShoul", type = "BYTE")
    @get:JsonProperty("ArmorPartRShoul")
    @set:JsonProperty("ArmorPartRShoul")
    var armorPartRShoul: UByte? = null

    @get:NwnField(name = "ArmorPart_LThigh", type = "BYTE")
    @get:JsonProperty("ArmorPartLThigh")
    @set:JsonProperty("ArmorPartLThigh")
    var armorPartLThigh: UByte? = null

    @get:NwnField(name = "StackSize", type = "WORD")
    @get:JsonProperty("StackSize")
    @set:JsonProperty("StackSize")
    var stackSize: UShort? = null

    @get:NwnField(name = "ModelPart3", type = "BYTE")
    @get:JsonProperty("ModelPart3")
    @set:JsonProperty("ModelPart3")
    var modelPart3: UByte? = null

    @get:NwnField(name = "Stolen", type = "BYTE")
    @get:JsonProperty("Stolen")
    @set:JsonProperty("Stolen")
    var stolen: UByte? = null

    @get:NwnField(name = "Infinite", type = "BYTE")
    @get:JsonProperty("Infinite")
    @set:JsonProperty("Infinite")
    var infinite: UByte? = null

    @get:NwnField(name = "ArmorPart_LShoul", type = "BYTE")
    @get:JsonProperty("ArmorPartLShoul")
    @set:JsonProperty("ArmorPartLShoul")
    var armorPartLShoul: UByte? = null

    @get:NwnField(name = "ArmorPart_RFArm", type = "BYTE")
    @get:JsonProperty("ArmorPartRFArm")
    @set:JsonProperty("ArmorPartRFArm")
    var armorPartRFArm: UByte? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "Cloth1Color", type = "BYTE")
    @get:JsonProperty("Cloth1Color")
    @set:JsonProperty("Cloth1Color")
    var cloth1Color: UByte? = null

    @get:NwnField(name = "ArmorPart_Pelvis", type = "BYTE")
    @get:JsonProperty("ArmorPartPelvis")
    @set:JsonProperty("ArmorPartPelvis")
    var armorPartPelvis: UByte? = null

    @get:NwnField(name = "ArmorPart_LShin", type = "BYTE")
    @get:JsonProperty("ArmorPartLShin")
    @set:JsonProperty("ArmorPartLShin")
    var armorPartLShin: UByte? = null

    @get:NwnField(name = "ArmorPart_LFArm", type = "BYTE")
    @get:JsonProperty("ArmorPartLFArm")
    @set:JsonProperty("ArmorPartLFArm")
    var armorPartLFArm: UByte? = null

    @get:NwnField(name = "ArmorPart_Belt", type = "BYTE")
    @get:JsonProperty("ArmorPartBelt")
    @set:JsonProperty("ArmorPartBelt")
    var armorPartBelt: UByte? = null

    @get:NwnField(name = "Repos_PosY", type = "WORD")
    @get:JsonProperty("ReposPosY")
    @set:JsonProperty("ReposPosY")
    var reposPosY: UShort? = null

    @get:NwnField(name = "ArmorPart_RThigh", type = "BYTE")
    @get:JsonProperty("ArmorPartRThigh")
    @set:JsonProperty("ArmorPartRThigh")
    var armorPartRThigh: UByte? = null

    @get:NwnField(name = "ArmorPart_RBicep", type = "BYTE")
    @get:JsonProperty("ArmorPartRBicep")
    @set:JsonProperty("ArmorPartRBicep")
    var armorPartRBicep: UByte? = null

    @get:NwnField(name = "Leather1Color", type = "BYTE")
    @get:JsonProperty("Leather1Color")
    @set:JsonProperty("Leather1Color")
    var leather1Color: UByte? = null

    @get:NwnField(name = "Cloth2Color", type = "BYTE")
    @get:JsonProperty("Cloth2Color")
    @set:JsonProperty("Cloth2Color")
    var cloth2Color: UByte? = null

    @get:NwnField(name = "ArmorPart_LBicep", type = "BYTE")
    @get:JsonProperty("ArmorPartLBicep")
    @set:JsonProperty("ArmorPartLBicep")
    var armorPartLBicep: UByte? = null

    @get:NwnField(name = "ArmorPart_RShin", type = "BYTE")
    @get:JsonProperty("ArmorPartRShin")
    @set:JsonProperty("ArmorPartRShin")
    var armorPartRShin: UByte? = null

    @get:NwnField(name = "ArmorPart_RHand", type = "BYTE")
    @get:JsonProperty("ArmorPartRHand")
    @set:JsonProperty("ArmorPartRHand")
    var armorPartRHand: UByte? = null

    @get:NwnField(name = "ArmorPart_Neck", type = "BYTE")
    @get:JsonProperty("ArmorPartNeck")
    @set:JsonProperty("ArmorPartNeck")
    var armorPartNeck: UByte? = null

    @get:NwnField(name = "ArmorPart_LFoot", type = "BYTE")
    @get:JsonProperty("ArmorPartLFoot")
    @set:JsonProperty("ArmorPartLFoot")
    var armorPartLFoot: UByte? = null

    @get:NwnField(name = "ArmorPart_RFoot", type = "BYTE")
    @get:JsonProperty("ArmorPartRFoot")
    @set:JsonProperty("ArmorPartRFoot")
    var armorPartRFoot: UByte? = null

    @get:NwnField(name = "ArmorPart_LHand", type = "BYTE")
    @get:JsonProperty("ArmorPartLHand")
    @set:JsonProperty("ArmorPartLHand")
    var armorPartLHand: UByte? = null

    @get:NwnField(name = "Dropable", type = "BYTE")
    @get:JsonProperty("Dropable")
    @set:JsonProperty("Dropable")
    var dropable: UByte? = null

    @get:NwnField(name = "Pickpocketable", type = "BYTE")
    @get:JsonProperty("Pickpocketable")
    @set:JsonProperty("Pickpocketable")
    var pickpocketable: UByte? = null

    @get:NwnField(name = "Comment", type = "CExoString", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.SUPPRESS)
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<GitVarTable>? = null
}

class GitKnown {

    @get:NwnField(name = "Spell", type = "WORD")
    @get:JsonProperty("Spell")
    @set:JsonProperty("Spell")
    var spell: UShort? = null

    @get:NwnField(name = "SpellFlags", type = "BYTE")
    @get:JsonProperty("SpellFlags")
    @set:JsonProperty("SpellFlags")
    var spellFlags: UByte? = null

    @get:NwnField(name = "SpellMetaMagic", type = "BYTE")
    @get:JsonProperty("SpellMetaMagic")
    @set:JsonProperty("SpellMetaMagic")
    var spellMetaMagic: UByte? = null
}

class GitMemorized {

    @get:NwnField(name = "SpellFlags", type = "BYTE")
    @get:JsonProperty("SpellFlags")
    @set:JsonProperty("SpellFlags")
    var spellFlags: UByte? = null

    @get:NwnField(name = "Spell", type = "WORD")
    @get:JsonProperty("Spell")
    @set:JsonProperty("Spell")
    var spell: UShort? = null

    @get:NwnField(name = "SpellMetaMagic", type = "BYTE")
    @get:JsonProperty("SpellMetaMagic")
    @set:JsonProperty("SpellMetaMagic")
    var spellMetaMagic: UByte? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GitPlaceable {

    @get:NwnField(name = "KeyRequired", type = "BYTE")
    @get:JsonProperty("KeyRequired")
    @set:JsonProperty("KeyRequired")
    var keyRequired: UByte? = null

    @get:NwnField(name = "AnimationState", type = "BYTE")
    @get:JsonProperty("AnimationState")
    @set:JsonProperty("AnimationState")
    var animationState: UByte? = null

    @get:NwnField(name = "OnUserDefined", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnUserDefined")
    @set:JsonProperty("OnUserDefined")
    var onUserDefined: String? = null

    @get:NwnField(name = "OnHeartbeat", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnHeartbeat")
    @set:JsonProperty("OnHeartbeat")
    var onHeartbeat: String? = null

    @get:NwnField(name = "OnDamaged", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnDamaged")
    @set:JsonProperty("OnDamaged")
    var onDamaged: String? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "Description", type = "CExoLocString")
    @get:JsonProperty("Description")
    @set:JsonProperty("Description")
    var description: CExoLocString? = null

    @get:NwnField(name = "CloseLockDC", type = "BYTE")
    @get:JsonProperty("CloseLockDC")
    @set:JsonProperty("CloseLockDC")
    var closeLockDC: UByte? = null

    @get:NwnField(name = "Appearance", type = "DWORD")
    @get:JsonProperty("Appearance")
    @set:JsonProperty("Appearance")
    var appearance: UInt? = null

    @get:NwnField(name = "Ref", type = "BYTE")
    @get:JsonProperty("Ref")
    @set:JsonProperty("Ref")
    var ref: UByte? = null

    @get:NwnField(name = "AutoRemoveKey", type = "BYTE")
    @get:JsonProperty("AutoRemoveKey")
    @set:JsonProperty("AutoRemoveKey")
    var autoRemoveKey: UByte? = null

    @get:NwnField(name = "DisarmDC", type = "BYTE")
    @get:JsonProperty("DisarmDC")
    @set:JsonProperty("DisarmDC")
    var disarmDC: UByte? = null

    @get:NwnField(name = "Lockable", type = "BYTE")
    @get:JsonProperty("Lockable")
    @set:JsonProperty("Lockable")
    var lockable: UByte? = null

    @get:NwnField(name = "TrapDisarmable", type = "BYTE")
    @get:JsonProperty("TrapDisarmable")
    @set:JsonProperty("TrapDisarmable")
    var trapDisarmable: UByte? = null

    @get:NwnField(name = "PortraitId", type = "WORD")
    @get:JsonProperty("PortraitId")
    @set:JsonProperty("PortraitId")
    var portraitId: UShort? = null

    @get:NwnField(name = "LocName", type = "CExoLocString")
    @get:JsonProperty("LocName")
    @set:JsonProperty("LocName")
    var locName: CExoLocString? = null

    @get:NwnField(name = "OnMeleeAttacked", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnMeleeAttacked")
    @set:JsonProperty("OnMeleeAttacked")
    var onMeleeAttacked: String? = null

    @get:NwnField(name = "TrapDetectable", type = "BYTE")
    @get:JsonProperty("TrapDetectable")
    @set:JsonProperty("TrapDetectable")
    var trapDetectable: UByte? = null

    @get:NwnField(name = "OnLock", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnLock")
    @set:JsonProperty("OnLock")
    var onLock: String? = null

    @get:NwnField(name = "Interruptable", type = "BYTE")
    @get:JsonProperty("Interruptable")
    @set:JsonProperty("Interruptable")
    var interruptable: UByte? = null

    @get:NwnField(name = "Z", type = "FLOAT")
    @get:JsonProperty("Z")
    @set:JsonProperty("Z")
    var z: Float? = null

    @get:NwnField(name = "TrapType", type = "BYTE")
    @get:JsonProperty("TrapType")
    @set:JsonProperty("TrapType")
    var trapType: UByte? = null

    @get:NwnField(name = "TrapDetectDC", type = "BYTE")
    @get:JsonProperty("TrapDetectDC")
    @set:JsonProperty("TrapDetectDC")
    var trapDetectDC: UByte? = null

    @get:NwnField(name = "CurrentHP", type = "SHORT")
    @get:JsonProperty("CurrentHP")
    @set:JsonProperty("CurrentHP")
    var currentHP: Short? = null

    @get:NwnField(name = "OnInvDisturbed", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnInvDisturbed")
    @set:JsonProperty("OnInvDisturbed")
    var onInvDisturbed: String? = null

    @get:NwnField(name = "OpenLockDC", type = "BYTE")
    @get:JsonProperty("OpenLockDC")
    @set:JsonProperty("OpenLockDC")
    var openLockDC: UByte? = null

    @get:NwnField(name = "OnDisarm", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnDisarm")
    @set:JsonProperty("OnDisarm")
    var onDisarm: String? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "Hardness", type = "BYTE")
    @get:JsonProperty("Hardness")
    @set:JsonProperty("Hardness")
    var hardness: UByte? = null

    @get:NwnField(name = "Faction", type = "DWORD")
    @get:JsonProperty("Faction")
    @set:JsonProperty("Faction")
    var faction: UInt? = null

    @get:NwnField(name = "Bearing", type = "FLOAT")
    @get:JsonProperty("Bearing")
    @set:JsonProperty("Bearing")
    var bearing: Float? = null

    @get:NwnField(name = "TrapOneShot", type = "BYTE")
    @get:JsonProperty("TrapOneShot")
    @set:JsonProperty("TrapOneShot")
    var trapOneShot: UByte? = null

    @get:NwnField(name = "OnUnlock", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnUnlock")
    @set:JsonProperty("OnUnlock")
    var onUnlock: String? = null

    @get:NwnField(name = "Conversation", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("Conversation")
    @set:JsonProperty("Conversation")
    var conversation: String? = null

    @get:NwnField(name = "OnOpen", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnOpen")
    @set:JsonProperty("OnOpen")
    var onOpen: String? = null

    @get:NwnField(name = "OnClosed", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnClosed")
    @set:JsonProperty("OnClosed")
    var onClosed: String? = null

    @get:NwnField(name = "HasInventory", type = "BYTE")
    @get:JsonProperty("HasInventory")
    @set:JsonProperty("HasInventory")
    var hasInventory: UByte? = null

    @get:NwnField(name = "OnSpellCastAt", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnSpellCastAt")
    @set:JsonProperty("OnSpellCastAt")
    var onSpellCastAt: String? = null

    @get:NwnField(name = "X", type = "FLOAT")
    @get:JsonProperty("X")
    @set:JsonProperty("X")
    var x: Float? = null

    @get:NwnField(name = "Plot", type = "BYTE")
    @get:JsonProperty("Plot")
    @set:JsonProperty("Plot")
    var plot: UByte? = null

    @get:NwnField(name = "Fort", type = "BYTE")
    @get:JsonProperty("Fort")
    @set:JsonProperty("Fort")
    var fort: UByte? = null

    @get:NwnField(name = "TrapFlag", type = "BYTE")
    @get:JsonProperty("TrapFlag")
    @set:JsonProperty("TrapFlag")
    var trapFlag: UByte? = null

    @get:NwnField(name = "OnDeath", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnDeath")
    @set:JsonProperty("OnDeath")
    var onDeath: String? = null

    @get:NwnField(name = "Type", type = "BYTE")
    @get:JsonProperty("Type")
    @set:JsonProperty("Type")
    var type: UByte? = null

    @get:NwnField(name = "Useable", type = "BYTE")
    @get:JsonProperty("Useable")
    @set:JsonProperty("Useable")
    var useable: UByte? = null

    @get:NwnField(name = "OnTrapTriggered", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnTrapTriggered")
    @set:JsonProperty("OnTrapTriggered")
    var onTrapTriggered: String? = null

    @get:NwnField(name = "Will", type = "BYTE")
    @get:JsonProperty("Will")
    @set:JsonProperty("Will")
    var will: UByte? = null

    @get:NwnField(name = "OnUsed", type = "ResRef")
    @get:JsonProperty("OnUsed")
    @set:JsonProperty("OnUsed")
    var onUsed: String? = null

    @get:NwnField(name = "Y", type = "FLOAT")
    @get:JsonProperty("Y")
    @set:JsonProperty("Y")
    var y: Float? = null

    @get:NwnField(name = "Static", type = "BYTE")
    @get:JsonProperty("Static")
    @set:JsonProperty("Static")
    var static: UByte? = null

    @get:NwnField(name = "BodyBag", type = "BYTE")
    @get:JsonProperty("BodyBag")
    @set:JsonProperty("BodyBag")
    var bodyBag: UByte? = null

    @get:NwnField(name = "KeyName", type = "CExoString", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("KeyName")
    @set:JsonProperty("KeyName")
    var keyName: String? = null

    @get:NwnField(name = "HP", type = "SHORT")
    @get:JsonProperty("HP")
    @set:JsonProperty("HP")
    var hP: Short? = null

    @get:NwnField(name = "OnClick", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnClick")
    @set:JsonProperty("OnClick")
    var onClick: String? = null

    @get:NwnField(name = "Locked", type = "BYTE")
    @get:JsonProperty("Locked")
    @set:JsonProperty("Locked")
    var locked: UByte? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<GitVarTable>? = null

    @get:NwnField(name = "ItemList", type = "List", indexedStructId = true)
    @get:JsonProperty("ItemList")
    @set:JsonProperty("ItemList")
    var itemList: List<GitItem>? = null

    @get:NwnField(name = "VisualTransform", type = "Struct", structType = 6)
    @get:JsonProperty("VisualTransform")
    @set:JsonProperty("VisualTransform")
    var visualTransform: GitVisualTransform? = null
}

class GitProperties {

    @get:NwnField(name = "PropertyName", type = "WORD")
    @get:JsonProperty("PropertyName")
    @set:JsonProperty("PropertyName")
    var propertyName: UShort? = null

    @get:NwnField(name = "CostValue", type = "WORD")
    @get:JsonProperty("CostValue")
    @set:JsonProperty("CostValue")
    var costValue: UShort? = null

    @get:NwnField(name = "Param1Value", type = "BYTE")
    @get:JsonProperty("Param1Value")
    @set:JsonProperty("Param1Value")
    var param1Value: UByte? = null

    @get:NwnField(name = "ChanceAppear", type = "BYTE")
    @get:JsonProperty("ChanceAppear")
    @set:JsonProperty("ChanceAppear")
    var chanceAppear: UByte? = null

    @get:NwnField(name = "CostTable", type = "BYTE")
    @get:JsonProperty("CostTable")
    @set:JsonProperty("CostTable")
    var costTable: UByte? = null

    @get:NwnField(name = "Param1", type = "BYTE")
    @get:JsonProperty("Param1")
    @set:JsonProperty("Param1")
    var param1: UByte? = null

    @get:NwnField(name = "Subtype", type = "WORD")
    @get:JsonProperty("Subtype")
    @set:JsonProperty("Subtype")
    var subtype: UShort? = null

    @get:NwnField(name = "Description", type = "CExoLocString")
    @get:JsonProperty("Description")
    @set:JsonProperty("Description")
    var description: CExoLocString? = null

    @get:NwnField(name = "PropertiesList", type = "List")
    @get:JsonProperty("PropertiesList")
    @set:JsonProperty("PropertiesList")
    var propertiesList: List<GitProperties>? = null

    @get:NwnField(name = "DescIdentified", type = "CExoLocString")
    @get:JsonProperty("DescIdentified")
    @set:JsonProperty("DescIdentified")
    var descIdentified: CExoLocString? = null

    @get:NwnField(name = "LocalizedName", type = "CExoLocString")
    @get:JsonProperty("LocalizedName")
    @set:JsonProperty("LocalizedName")
    var localizedName: CExoLocString? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "Comment", type = "CExoString", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.SUPPRESS)
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GitSkill {

    @get:NwnField(name = "Rank", type = "BYTE")
    @get:JsonProperty("Rank")
    @set:JsonProperty("Rank")
    var rank: UByte? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GitSome {

    @get:NwnField(name = "YPosition", type = "FLOAT")
    @get:JsonProperty("YPosition")
    @set:JsonProperty("YPosition")
    var yPosition: Float? = null

    @get:NwnField(name = "ModelPart1", type = "BYTE")
    @get:JsonProperty("ModelPart1")
    @set:JsonProperty("ModelPart1")
    var modelPart1: UByte? = null

    @get:NwnField(name = "ArmorPart_Belt", type = "BYTE")
    @get:JsonProperty("ArmorPartBelt")
    @set:JsonProperty("ArmorPartBelt")
    var armorPartBelt: UByte? = null

    @get:NwnField(name = "DescIdentified", type = "CExoLocString")
    @get:JsonProperty("DescIdentified")
    @set:JsonProperty("DescIdentified")
    var descIdentified: CExoLocString? = null

    @get:NwnField(name = "XPosition", type = "FLOAT")
    @get:JsonProperty("XPosition")
    @set:JsonProperty("XPosition")
    var xPosition: Float? = null

    @get:NwnField(name = "Plot", type = "BYTE")
    @get:JsonProperty("Plot")
    @set:JsonProperty("Plot")
    var plot: UByte? = null

    @get:NwnField(name = "Stolen", type = "BYTE")
    @get:JsonProperty("Stolen")
    @set:JsonProperty("Stolen")
    var stolen: UByte? = null

    @get:NwnField(name = "XOrientation", type = "FLOAT")
    @get:JsonProperty("XOrientation")
    @set:JsonProperty("XOrientation")
    var xOrientation: Float? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "Cursed", type = "BYTE")
    @get:JsonProperty("Cursed")
    @set:JsonProperty("Cursed")
    var cursed: UByte? = null

    @get:NwnField(name = "ArmorPart_LFArm", type = "BYTE")
    @get:JsonProperty("ArmorPartLFArm")
    @set:JsonProperty("ArmorPartLFArm")
    var armorPartLFArm: UByte? = null

    @get:NwnField(name = "StackSize", type = "WORD")
    @get:JsonProperty("StackSize")
    @set:JsonProperty("StackSize")
    var stackSize: UShort? = null

    @get:NwnField(name = "Cost", type = "DWORD")
    @get:JsonProperty("Cost")
    @set:JsonProperty("Cost")
    var cost: UInt? = null

    @get:NwnField(name = "AddCost", type = "DWORD")
    @get:JsonProperty("AddCost")
    @set:JsonProperty("AddCost")
    var addCost: UInt? = null

    @get:NwnField(name = "BaseItem", type = "INT")
    @get:JsonProperty("BaseItem")
    @set:JsonProperty("BaseItem")
    var baseItem: Int? = null

    @get:NwnField(name = "Charges", type = "BYTE")
    @get:JsonProperty("Charges")
    @set:JsonProperty("Charges")
    var charges: UByte? = null

    @get:NwnField(name = "Description", type = "CExoLocString")
    @get:JsonProperty("Description")
    @set:JsonProperty("Description")
    var description: CExoLocString? = null

    @get:NwnField(name = "Metal2Color", type = "BYTE")
    @get:JsonProperty("Metal2Color")
    @set:JsonProperty("Metal2Color")
    var metal2Color: UByte? = null

    @get:NwnField(name = "ArmorPart_LShoul", type = "BYTE")
    @get:JsonProperty("ArmorPartLShoul")
    @set:JsonProperty("ArmorPartLShoul")
    var armorPartLShoul: UByte? = null

    @get:NwnField(name = "ModelPart2", type = "BYTE")
    @get:JsonProperty("ModelPart2")
    @set:JsonProperty("ModelPart2")
    var modelPart2: UByte? = null

    @get:NwnField(name = "ModelPart3", type = "BYTE")
    @get:JsonProperty("ModelPart3")
    @set:JsonProperty("ModelPart3")
    var modelPart3: UByte? = null

    @get:NwnField(name = "LocalizedName", type = "CExoLocString")
    @get:JsonProperty("LocalizedName")
    @set:JsonProperty("LocalizedName")
    var localizedName: CExoLocString? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "ArmorPart_LThigh", type = "BYTE")
    @get:JsonProperty("ArmorPartLThigh")
    @set:JsonProperty("ArmorPartLThigh")
    var armorPartLThigh: UByte? = null

    @get:NwnField(name = "ArmorPart_RShoul", type = "BYTE")
    @get:JsonProperty("ArmorPartRShoul")
    @set:JsonProperty("ArmorPartRShoul")
    var armorPartRShoul: UByte? = null

    @get:NwnField(name = "Identified", type = "BYTE")
    @get:JsonProperty("Identified")
    @set:JsonProperty("Identified")
    var identified: UByte? = null

    @get:NwnField(name = "YOrientation", type = "FLOAT")
    @get:JsonProperty("YOrientation")
    @set:JsonProperty("YOrientation")
    var yOrientation: Float? = null

    @get:NwnField(name = "ArmorPart_Pelvis", type = "BYTE")
    @get:JsonProperty("ArmorPartPelvis")
    @set:JsonProperty("ArmorPartPelvis")
    var armorPartPelvis: UByte? = null

    @get:NwnField(name = "ArmorPart_RFoot", type = "BYTE")
    @get:JsonProperty("ArmorPartRFoot")
    @set:JsonProperty("ArmorPartRFoot")
    var armorPartRFoot: UByte? = null

    @get:NwnField(name = "PropertiesList", type = "List", structType = 0)
    @get:JsonProperty("PropertiesList")
    @set:JsonProperty("PropertiesList")
    var propertiesList: List<GitProperties>? = null

    @get:NwnField(name = "ArmorPart_Torso", type = "BYTE")
    @get:JsonProperty("ArmorPartTorso")
    @set:JsonProperty("ArmorPartTorso")
    var armorPartTorso: UByte? = null

    @get:NwnField(name = "Metal1Color", type = "BYTE")
    @get:JsonProperty("Metal1Color")
    @set:JsonProperty("Metal1Color")
    var metal1Color: UByte? = null

    @get:NwnField(name = "Cloth1Color", type = "BYTE")
    @get:JsonProperty("Cloth1Color")
    @set:JsonProperty("Cloth1Color")
    var cloth1Color: UByte? = null

    @get:NwnField(name = "ZPosition", type = "FLOAT")
    @get:JsonProperty("ZPosition")
    @set:JsonProperty("ZPosition")
    var zPosition: Float? = null

    @get:NwnField(name = "Leather2Color", type = "BYTE")
    @get:JsonProperty("Leather2Color")
    @set:JsonProperty("Leather2Color")
    var leather2Color: UByte? = null

    @get:NwnField(name = "ArmorPart_LBicep", type = "BYTE")
    @get:JsonProperty("ArmorPartLBicep")
    @set:JsonProperty("ArmorPartLBicep")
    var armorPartLBicep: UByte? = null

    @get:NwnField(name = "ArmorPart_Neck", type = "BYTE")
    @get:JsonProperty("ArmorPartNeck")
    @set:JsonProperty("ArmorPartNeck")
    var armorPartNeck: UByte? = null

    @get:NwnField(name = "ArmorPart_LFoot", type = "BYTE")
    @get:JsonProperty("ArmorPartLFoot")
    @set:JsonProperty("ArmorPartLFoot")
    var armorPartLFoot: UByte? = null

    @get:NwnField(name = "ArmorPart_RBicep", type = "BYTE")
    @get:JsonProperty("ArmorPartRBicep")
    @set:JsonProperty("ArmorPartRBicep")
    var armorPartRBicep: UByte? = null

    @get:NwnField(name = "ArmorPart_LHand", type = "BYTE")
    @get:JsonProperty("ArmorPartLHand")
    @set:JsonProperty("ArmorPartLHand")
    var armorPartLHand: UByte? = null

    @get:NwnField(name = "Cloth2Color", type = "BYTE")
    @get:JsonProperty("Cloth2Color")
    @set:JsonProperty("Cloth2Color")
    var cloth2Color: UByte? = null

    @get:NwnField(name = "ArmorPart_RShin", type = "BYTE")
    @get:JsonProperty("ArmorPartRShin")
    @set:JsonProperty("ArmorPartRShin")
    var armorPartRShin: UByte? = null

    @get:NwnField(name = "ArmorPart_Robe", type = "BYTE")
    @get:JsonProperty("ArmorPartRobe")
    @set:JsonProperty("ArmorPartRobe")
    var armorPartRobe: UByte? = null

    @get:NwnField(name = "ArmorPart_LShin", type = "BYTE")
    @get:JsonProperty("ArmorPartLShin")
    @set:JsonProperty("ArmorPartLShin")
    var armorPartLShin: UByte? = null

    @get:NwnField(name = "ArmorPart_RThigh", type = "BYTE")
    @get:JsonProperty("ArmorPartRThigh")
    @set:JsonProperty("ArmorPartRThigh")
    var armorPartRThigh: UByte? = null

    @get:NwnField(name = "Leather1Color", type = "BYTE")
    @get:JsonProperty("Leather1Color")
    @set:JsonProperty("Leather1Color")
    var leather1Color: UByte? = null

    @get:NwnField(name = "ArmorPart_RFArm", type = "BYTE")
    @get:JsonProperty("ArmorPartRFArm")
    @set:JsonProperty("ArmorPartRFArm")
    var armorPartRFArm: UByte? = null

    @get:NwnField(name = "ArmorPart_RHand", type = "BYTE")
    @get:JsonProperty("ArmorPartRHand")
    @set:JsonProperty("ArmorPartRHand")
    var armorPartRHand: UByte? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<GitVarTable>? = null
}

class GitSound {

    @get:NwnField(name = "Hours", type = "DWORD")
    @get:JsonProperty("Hours")
    @set:JsonProperty("Hours")
    var hours: UInt? = null

    @get:NwnField(name = "MinDistance", type = "FLOAT")
    @get:JsonProperty("MinDistance")
    @set:JsonProperty("MinDistance")
    var minDistance: Float? = null

    @get:NwnField(name = "YPosition", type = "FLOAT")
    @get:JsonProperty("YPosition")
    @set:JsonProperty("YPosition")
    var yPosition: Float? = null

    @get:NwnField(name = "XPosition", type = "FLOAT")
    @get:JsonProperty("XPosition")
    @set:JsonProperty("XPosition")
    var xPosition: Float? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "Looping", type = "BYTE")
    @get:JsonProperty("Looping")
    @set:JsonProperty("Looping")
    var looping: UByte? = null

    @get:NwnField(name = "Positional", type = "BYTE")
    @get:JsonProperty("Positional")
    @set:JsonProperty("Positional")
    var positional: UByte? = null

    @get:NwnField(name = "Active", type = "BYTE")
    @get:JsonProperty("Active")
    @set:JsonProperty("Active")
    var active: UByte? = null

    @get:NwnField(name = "Sounds", type = "List", structType = 0)
    @get:JsonProperty("Sounds")
    @set:JsonProperty("Sounds")
    var sounds: List<GitSounds>? = null

    @get:NwnField(name = "LocName", type = "CExoLocString")
    @get:JsonProperty("LocName")
    @set:JsonProperty("LocName")
    var locName: CExoLocString? = null

    @get:NwnField(name = "MaxDistance", type = "FLOAT")
    @get:JsonProperty("MaxDistance")
    @set:JsonProperty("MaxDistance")
    var maxDistance: Float? = null

    @get:NwnField(name = "RandomPosition", type = "BYTE")
    @get:JsonProperty("RandomPosition")
    @set:JsonProperty("RandomPosition")
    var randomPosition: UByte? = null

    @get:NwnField(name = "RandomRangeY", type = "FLOAT")
    @get:JsonProperty("RandomRangeY")
    @set:JsonProperty("RandomRangeY")
    var randomRangeY: Float? = null

    @get:NwnField(name = "PitchVariation", type = "FLOAT")
    @get:JsonProperty("PitchVariation")
    @set:JsonProperty("PitchVariation")
    var pitchVariation: Float? = null

    @get:NwnField(name = "Random", type = "BYTE")
    @get:JsonProperty("Random")
    @set:JsonProperty("Random")
    var random: UByte? = null

    @get:NwnField(name = "Times", type = "BYTE")
    @get:JsonProperty("Times")
    @set:JsonProperty("Times")
    var times: UByte? = null

    @get:NwnField(name = "Volume", type = "BYTE")
    @get:JsonProperty("Volume")
    @set:JsonProperty("Volume")
    var volume: UByte? = null

    @get:NwnField(name = "Elevation", type = "FLOAT")
    @get:JsonProperty("Elevation")
    @set:JsonProperty("Elevation")
    var elevation: Float? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "Priority", type = "BYTE")
    @get:JsonProperty("Priority")
    @set:JsonProperty("Priority")
    var priority: UByte? = null

    @get:NwnField(name = "RandomRangeX", type = "FLOAT")
    @get:JsonProperty("RandomRangeX")
    @set:JsonProperty("RandomRangeX")
    var randomRangeX: Float? = null

    @get:NwnField(name = "VolumeVrtn", type = "BYTE")
    @get:JsonProperty("VolumeVrtn")
    @set:JsonProperty("VolumeVrtn")
    var volumeVrtn: UByte? = null

    @get:NwnField(name = "IntervalVrtn", type = "DWORD")
    @get:JsonProperty("IntervalVrtn")
    @set:JsonProperty("IntervalVrtn")
    var intervalVrtn: UInt? = null

    @get:NwnField(name = "Continuous", type = "BYTE")
    @get:JsonProperty("Continuous")
    @set:JsonProperty("Continuous")
    var continuous: UByte? = null

    @get:NwnField(name = "Interval", type = "DWORD")
    @get:JsonProperty("Interval")
    @set:JsonProperty("Interval")
    var interval: UInt? = null

    @get:NwnField(name = "GeneratedType", type = "DWORD")
    @get:JsonProperty("GeneratedType")
    @set:JsonProperty("GeneratedType")
    var generatedType: UInt? = null

    @get:NwnField(name = "ZPosition", type = "FLOAT")
    @get:JsonProperty("ZPosition")
    @set:JsonProperty("ZPosition")
    var zPosition: Float? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<GitVarTable>? = null
}

class GitSounds {

    @get:NwnField(name = "Sound", type = "ResRef")
    @get:JsonProperty("Sound")
    @set:JsonProperty("Sound")
    var sound: String? = null
}

class GitSpawnPoint {

    @get:NwnField(name = "Z", type = "FLOAT")
    @get:JsonProperty("Z")
    @set:JsonProperty("Z")
    var z: Float? = null

    @get:NwnField(name = "X", type = "FLOAT")
    @get:JsonProperty("X")
    @set:JsonProperty("X")
    var x: Float? = null

    @get:NwnField(name = "Orientation", type = "FLOAT")
    @get:JsonProperty("Orientation")
    @set:JsonProperty("Orientation")
    var orientation: Float? = null

    @get:NwnField(name = "Y", type = "FLOAT")
    @get:JsonProperty("Y")
    @set:JsonProperty("Y")
    var y: Float? = null

    @get:NwnField(name = "Comment", type = "CExoString", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GitSpecAbility {

    @get:NwnField(name = "SpellFlags", type = "BYTE")
    @get:JsonProperty("SpellFlags")
    @set:JsonProperty("SpellFlags")
    var spellFlags: UByte? = null

    @get:NwnField(name = "SpellCasterLevel", type = "BYTE")
    @get:JsonProperty("SpellCasterLevel")
    @set:JsonProperty("SpellCasterLevel")
    var spellCasterLevel: UByte? = null

    @get:NwnField(name = "Spell", type = "WORD")
    @get:JsonProperty("Spell")
    @set:JsonProperty("Spell")
    var spell: UShort? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

@JsonPropertyOrder(value=["LocName", "ResRef", "Tag"])
class GitStore {

    @get:NwnField(name = "ItemList", type = "List", indexedStructId = true)
    @get:JsonProperty("ItemList")
    @set:JsonProperty("ItemList")
    var itemList: List<GitItem>? = null

    @get:NwnField(name = "BlackMarket", type = "BYTE")
    @get:JsonProperty("BlackMarket")
    @set:JsonProperty("BlackMarket")
    var blackMarket: UByte? = null

    @get:NwnField(name = "StoreGold", type = "INT")
    @get:JsonProperty("StoreGold")
    @set:JsonProperty("StoreGold")
    var storeGold: Int? = null

    @get:NwnField(name = "StoreList", type = "List", structType = 4)
    @get:JsonProperty("StoreList")
    @set:JsonProperty("StoreList")
    var storeList: List<GitStore>? = null

    @get:NwnField(name = "WillOnlyBuy", type = "List", structType = 97869)
    @get:JsonProperty("WillOnlyBuy")
    @set:JsonProperty("WillOnlyBuy")
    var willOnlyBuy: List<GitWillOnlyBuy>? = null

    @get:NwnField(name = "YOrientation", type = "FLOAT")
    @get:JsonProperty("YOrientation")
    @set:JsonProperty("YOrientation")
    var yOrientation: Float? = null

    @get:NwnField(name = "LocName", type = "CExoLocString")
    @get:JsonProperty("LocName")
    @set:JsonProperty("LocName")
    var locName: CExoLocString? = null

    @get:NwnField(name = "MaxBuyPrice", type = "INT")
    @get:JsonProperty("MaxBuyPrice")
    @set:JsonProperty("MaxBuyPrice")
    var maxBuyPrice: Int? = null

    @get:NwnField(name = "BM_MarkDown", type = "INT")
    @get:JsonProperty("BMMarkDown")
    @set:JsonProperty("BMMarkDown")
    var bMMarkDown: Int? = null

    @get:NwnField(name = "XPosition", type = "FLOAT")
    @get:JsonProperty("XPosition")
    @set:JsonProperty("XPosition")
    var xPosition: Float? = null

    @get:NwnField(name = "MarkUp", type = "INT")
    @get:JsonProperty("MarkUp")
    @set:JsonProperty("MarkUp")
    var markUp: Int? = null

    @get:NwnField(name = "WillNotBuy", type = "List", structType = 97869)
    @get:JsonProperty("WillNotBuy")
    @set:JsonProperty("WillNotBuy")
    var willNotBuy: List<GitWillNotBuy>? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<GitVarTable>? = null

    @get:NwnField(name = "YPosition", type = "FLOAT")
    @get:JsonProperty("YPosition")
    @set:JsonProperty("YPosition")
    var yPosition: Float? = null

    @get:NwnField(name = "OnOpenStore", type = "ResRef")
    @get:JsonProperty("OnOpenStore")
    @set:JsonProperty("OnOpenStore")
    var onOpenStore: String? = null

    @get:NwnField(name = "OnStoreClosed", type = "ResRef")
    @get:JsonProperty("OnStoreClosed")
    @set:JsonProperty("OnStoreClosed")
    var onStoreClosed: String? = null

    @get:NwnField(name = "MarkDown", type = "INT")
    @get:JsonProperty("MarkDown")
    @set:JsonProperty("MarkDown")
    var markDown: Int? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "ResRef", type = "ResRef")
    @get:JsonProperty("ResRef")
    @set:JsonProperty("ResRef")
    var resRef: String? = null

    @get:NwnField(name = "ZPosition", type = "FLOAT")
    @get:JsonProperty("ZPosition")
    @set:JsonProperty("ZPosition")
    var zPosition: Float? = null

    @get:NwnField(name = "XOrientation", type = "FLOAT")
    @get:JsonProperty("XOrientation")
    @set:JsonProperty("XOrientation")
    var xOrientation: Float? = null

    @get:NwnField(name = "IdentifyPrice", type = "INT")
    @get:JsonProperty("IdentifyPrice")
    @set:JsonProperty("IdentifyPrice")
    var identifyPrice: Int? = null
}

class GitTemplate {

    @get:NwnField(name = "TemplateID", type = "WORD")
    @get:JsonProperty("TemplateID")
    @set:JsonProperty("TemplateID")
    var templateID: UShort? = null
}

class GitTrigger {

    @get:NwnField(name = "ZPosition", type = "FLOAT")
    @get:JsonProperty("ZPosition")
    @set:JsonProperty("ZPosition")
    var zPosition: Float? = null

    @get:NwnField(name = "ScriptUserDefine", type = "ResRef")
    @get:JsonProperty("ScriptUserDefine")
    @set:JsonProperty("ScriptUserDefine")
    var scriptUserDefine: String? = null

    @get:NwnField(name = "YOrientation", type = "FLOAT")
    @get:JsonProperty("YOrientation")
    @set:JsonProperty("YOrientation")
    var yOrientation: Float? = null

    @get:NwnField(name = "LoadScreenID", type = "WORD")
    @get:JsonProperty("LoadScreenID")
    @set:JsonProperty("LoadScreenID")
    var loadScreenID: UShort? = null

    @get:NwnField(name = "TrapDisarmable", type = "BYTE")
    @get:JsonProperty("TrapDisarmable")
    @set:JsonProperty("TrapDisarmable")
    var trapDisarmable: UByte? = null

    @get:NwnField(name = "OnClick", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.DEFAULT_VALUE)
    @get:JsonProperty("OnClick")
    @set:JsonProperty("OnClick")
    var onClick: String? = null

    @get:NwnField(name = "Faction", type = "DWORD")
    @get:JsonProperty("Faction")
    @set:JsonProperty("Faction")
    var faction: UInt? = null

    @get:NwnField(name = "HighlightHeight", type = "FLOAT")
    @get:JsonProperty("HighlightHeight")
    @set:JsonProperty("HighlightHeight")
    var highlightHeight: Float? = null

    @get:NwnField(name = "TrapFlag", type = "BYTE")
    @get:JsonProperty("TrapFlag")
    @set:JsonProperty("TrapFlag")
    var trapFlag: UByte? = null

    @get:NwnField(name = "Type", type = "INT")
    @get:JsonProperty("Type")
    @set:JsonProperty("Type")
    var type: Int? = null

    @get:NwnField(name = "ZOrientation", type = "FLOAT")
    @get:JsonProperty("ZOrientation")
    @set:JsonProperty("ZOrientation")
    var zOrientation: Float? = null

    @get:NwnField(name = "XPosition", type = "FLOAT")
    @get:JsonProperty("XPosition")
    @set:JsonProperty("XPosition")
    var xPosition: Float? = null

    @get:NwnField(name = "OnDisarm", type = "ResRef")
    @get:JsonProperty("OnDisarm")
    @set:JsonProperty("OnDisarm")
    var onDisarm: String? = null

    @get:NwnField(name = "AutoRemoveKey", type = "BYTE")
    @get:JsonProperty("AutoRemoveKey")
    @set:JsonProperty("AutoRemoveKey")
    var autoRemoveKey: UByte? = null

    @get:NwnField(name = "PortraitId", type = "WORD")
    @get:JsonProperty("PortraitId")
    @set:JsonProperty("PortraitId")
    var portraitId: UShort? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "KeyName", type = "CExoString")
    @get:JsonProperty("KeyName")
    @set:JsonProperty("KeyName")
    var keyName: String? = null

    @get:NwnField(name = "XOrientation", type = "FLOAT")
    @get:JsonProperty("XOrientation")
    @set:JsonProperty("XOrientation")
    var xOrientation: Float? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "ScriptOnExit", type = "ResRef")
    @get:JsonProperty("ScriptOnExit")
    @set:JsonProperty("ScriptOnExit")
    var scriptOnExit: String? = null

    @get:NwnField(name = "LinkedTo", type = "CExoString")
    @get:JsonProperty("LinkedTo")
    @set:JsonProperty("LinkedTo")
    var linkedTo: String? = null

    @get:NwnField(name = "TrapOneShot", type = "BYTE")
    @get:JsonProperty("TrapOneShot")
    @set:JsonProperty("TrapOneShot")
    var trapOneShot: UByte? = null

    @get:NwnField(name = "TrapDetectDC", type = "BYTE")
    @get:JsonProperty("TrapDetectDC")
    @set:JsonProperty("TrapDetectDC")
    var trapDetectDC: UByte? = null

    @get:NwnField(name = "LinkedToFlags", type = "BYTE")
    @get:JsonProperty("LinkedToFlags")
    @set:JsonProperty("LinkedToFlags")
    var linkedToFlags: UByte? = null

    @get:NwnField(name = "DisarmDC", type = "BYTE")
    @get:JsonProperty("DisarmDC")
    @set:JsonProperty("DisarmDC")
    var disarmDC: UByte? = null

    @get:NwnField(name = "TrapType", type = "BYTE")
    @get:JsonProperty("TrapType")
    @set:JsonProperty("TrapType")
    var trapType: UByte? = null

    @get:NwnField(name = "ScriptHeartbeat", type = "ResRef")
    @get:JsonProperty("ScriptHeartbeat")
    @set:JsonProperty("ScriptHeartbeat")
    var scriptHeartbeat: String? = null

    @get:NwnField(name = "Cursor", type = "BYTE")
    @get:JsonProperty("Cursor")
    @set:JsonProperty("Cursor")
    var cursor: UByte? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<GitVarTable>? = null

    @get:NwnField(name = "OnTrapTriggered", type = "ResRef")
    @get:JsonProperty("OnTrapTriggered")
    @set:JsonProperty("OnTrapTriggered")
    var onTrapTriggered: String? = null

    @get:NwnField(name = "Geometry", type = "List", structType = 3)
    @get:JsonProperty("Geometry")
    @set:JsonProperty("Geometry")
    var geometry: List<GitGeometry>? = null

    @get:NwnField(name = "TrapDetectable", type = "BYTE")
    @get:JsonProperty("TrapDetectable")
    @set:JsonProperty("TrapDetectable")
    var trapDetectable: UByte? = null

    @get:NwnField(name = "LocalizedName", type = "CExoLocString")
    @get:JsonProperty("LocalizedName")
    @set:JsonProperty("LocalizedName")
    var localizedName: CExoLocString? = null

    @get:NwnField(name = "ScriptOnEnter", type = "ResRef")
    @get:JsonProperty("ScriptOnEnter")
    @set:JsonProperty("ScriptOnEnter")
    var scriptOnEnter: String? = null

    @get:NwnField(name = "YPosition", type = "FLOAT")
    @get:JsonProperty("YPosition")
    @set:JsonProperty("YPosition")
    var yPosition: Float? = null
}

class GitVarTable {

    @get:NwnField(name = "Type", type = "DWORD")
    @get:JsonProperty("Type")
    @set:JsonProperty("Type")
    var type: UInt? = null

    @get:NwnField(name = "Name", type = "CExoString")
    @get:JsonProperty("Name")
    @set:JsonProperty("Name")
    var name: String? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "Value", type = "")
    @get:JsonProperty("Value")
    @set:JsonProperty("Value")
    var value: Any? = null
}

class GitVisualTransform {

    @get:NwnField(name = "ScaleY", type = "FLOAT")
    @get:JsonProperty("ScaleY")
    @set:JsonProperty("ScaleY")
    var scaleY: Float? = null

    @get:NwnField(name = "ScaleZ", type = "FLOAT")
    @get:JsonProperty("ScaleZ")
    @set:JsonProperty("ScaleZ")
    var scaleZ: Float? = null

    @get:NwnField(name = "ScaleX", type = "FLOAT")
    @get:JsonProperty("ScaleX")
    @set:JsonProperty("ScaleX")
    var scaleX: Float? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null
}

class GitWaypoint {

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "XPosition", type = "FLOAT")
    @get:JsonProperty("XPosition")
    @set:JsonProperty("XPosition")
    var xPosition: Float? = null

    @get:NwnField(name = "LocalizedName", type = "CExoLocString")
    @get:JsonProperty("LocalizedName")
    @set:JsonProperty("LocalizedName")
    var localizedName: CExoLocString? = null

    @get:NwnField(name = "MapNoteEnabled", type = "BYTE")
    @get:JsonProperty("MapNoteEnabled")
    @set:JsonProperty("MapNoteEnabled")
    var mapNoteEnabled: UByte? = null

    @get:NwnField(name = "LinkedTo", type = "CExoString")
    @get:JsonProperty("LinkedTo")
    @set:JsonProperty("LinkedTo")
    var linkedTo: String? = null

    @get:NwnField(name = "ZPosition", type = "FLOAT")
    @get:JsonProperty("ZPosition")
    @set:JsonProperty("ZPosition")
    var zPosition: Float? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "XOrientation", type = "FLOAT")
    @get:JsonProperty("XOrientation")
    @set:JsonProperty("XOrientation")
    var xOrientation: Float? = null

    @get:NwnField(name = "HasMapNote", type = "BYTE")
    @get:JsonProperty("HasMapNote")
    @set:JsonProperty("HasMapNote")
    var hasMapNote: UByte? = null

    @get:NwnField(name = "YPosition", type = "FLOAT")
    @get:JsonProperty("YPosition")
    @set:JsonProperty("YPosition")
    var yPosition: Float? = null

    @get:NwnField(name = "MapNote", type = "CExoLocString")
    @get:JsonProperty("MapNote")
    @set:JsonProperty("MapNote")
    var mapNote: CExoLocString? = null

    @get:NwnField(name = "YOrientation", type = "FLOAT")
    @get:JsonProperty("YOrientation")
    @set:JsonProperty("YOrientation")
    var yOrientation: Float? = null

    @get:NwnField(name = "Appearance", type = "BYTE")
    @get:JsonProperty("Appearance")
    @set:JsonProperty("Appearance")
    var appearance: UByte? = null

    @get:NwnField(name = "Description", type = "CExoLocString")
    @get:JsonProperty("Description")
    @set:JsonProperty("Description")
    var description: CExoLocString? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<GitVarTable>? = null
}

class GitWillNotBuy {

    @get:NwnField(name = "BaseItem", type = "INT")
    @get:JsonProperty("BaseItem")
    @set:JsonProperty("BaseItem")
    var baseItem: Int? = null
}

class GitWillOnlyBuy {

    @get:NwnField(name = "BaseItem", type = "INT")
    @get:JsonProperty("BaseItem")
    @set:JsonProperty("BaseItem")
    var baseItem: Int? = null
}
