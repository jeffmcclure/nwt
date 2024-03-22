package neverwintertoolkit.model.utc

// Generated 2024-01-31T15:06:10.131206
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.model.annotation.NwnField
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import neverwintertoolkit.model.git.HasStructId
import java.io.OutputStream

class Utc : GffObj {
    companion object {
        val factory: GffFactory<Utc> = GenericGffFactory(Utc::class.java, ".utc")
    }
    override fun writeGff(output: OutputStream) {
        GenericGffWriter(this, ".utc").writeGff(output)
    }

    @get:NwnField(name = "FeatList", type = "List", structType = 1)
    @get:JsonProperty("FeatList")
    @set:JsonProperty("FeatList")
    var featList: List<UtcFeat>? = null

    @get:NwnField(name = "FirstName", type = "CExoLocString")
    @get:JsonProperty("FirstName")
    @set:JsonProperty("FirstName")
    var firstName: CExoLocString? = null

    @get:NwnField(name = "Con", type = "BYTE")
    @get:JsonProperty("Con")
    @set:JsonProperty("Con")
    var con: UByte? = null

    @get:NwnField(name = "Color_Hair", type = "BYTE")
    @get:JsonProperty("ColorHair")
    @set:JsonProperty("ColorHair")
    var colorHair: UByte? = null

    @get:NwnField(name = "BodyPart_LFArm", type = "BYTE")
    @get:JsonProperty("BodyPartLFArm")
    @set:JsonProperty("BodyPartLFArm")
    var bodyPartLFArm: UByte? = null

    @get:NwnField(name = "StartingPackage", type = "BYTE")
    @get:JsonProperty("StartingPackage")
    @set:JsonProperty("StartingPackage")
    var startingPackage: UByte? = null

    @get:NwnField(name = "NaturalAC", type = "BYTE")
    @get:JsonProperty("NaturalAC")
    @set:JsonProperty("NaturalAC")
    var naturalAC: UByte? = null

    @get:NwnField(name = "ClassList", type = "List", structType = 2)
    @get:JsonProperty("ClassList")
    @set:JsonProperty("ClassList")
    var classList: List<UtcClass>? = null

    @get:NwnField(name = "BodyPart_RShin", type = "BYTE")
    @get:JsonProperty("BodyPartRShin")
    @set:JsonProperty("BodyPartRShin")
    var bodyPartRShin: UByte? = null

    @get:NwnField(name = "WalkRate", type = "INT")
    @get:JsonProperty("WalkRate")
    @set:JsonProperty("WalkRate")
    var walkRate: Int? = null

    @get:NwnField(name = "LastName", type = "CExoLocString")
    @get:JsonProperty("LastName")
    @set:JsonProperty("LastName")
    var lastName: CExoLocString? = null

    @get:NwnField(name = "Plot", type = "BYTE")
    @get:JsonProperty("Plot")
    @set:JsonProperty("Plot")
    var plot: UByte? = null

    @get:NwnField(name = "TemplateList", type = "List", structType = 5)
    @get:JsonProperty("TemplateList")
    @set:JsonProperty("TemplateList")
    var templateList: List<UtcTemplate>? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "Comment", type = "CExoString")
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "FactionID", type = "WORD")
    @get:JsonProperty("FactionID")
    @set:JsonProperty("FactionID")
    var factionID: UShort? = null

    @get:NwnField(name = "BodyBag", type = "BYTE")
    @get:JsonProperty("BodyBag")
    @set:JsonProperty("BodyBag")
    var bodyBag: UByte? = null

    @get:NwnField(name = "Int", type = "BYTE")
    @get:JsonProperty("Int")
    @set:JsonProperty("Int")
    var int: UByte? = null

    @get:NwnField(name = "fortbonus", type = "SHORT")
    @get:JsonProperty("Fortbonus")
    @set:JsonProperty("Fortbonus")
    var fortbonus: Short? = null

    @get:NwnField(name = "ChallengeRating", type = "FLOAT")
    @get:JsonProperty("ChallengeRating")
    @set:JsonProperty("ChallengeRating")
    var challengeRating: Float? = null

    @get:NwnField(name = "Deity", type = "CExoString")
    @get:JsonProperty("Deity")
    @set:JsonProperty("Deity")
    var deity: String? = null

    @get:NwnField(name = "SpecAbilityList", type = "List", structType = 4)
    @get:JsonProperty("SpecAbilityList")
    @set:JsonProperty("SpecAbilityList")
    var specAbilityList: List<UtcSpecAbility>? = null

    @get:NwnField(name = "PerceptionRange", type = "BYTE")
    @get:JsonProperty("PerceptionRange")
    @set:JsonProperty("PerceptionRange")
    var perceptionRange: UByte? = null

    @get:NwnField(name = "DecayTime", type = "DWORD")
    @get:JsonProperty("DecayTime")
    @set:JsonProperty("DecayTime")
    var decayTime: UInt? = null

    @get:NwnField(name = "PortraitId", type = "WORD")
    @get:JsonProperty("PortraitId")
    @set:JsonProperty("PortraitId")
    var portraitId: UShort? = null

    @get:NwnField(name = "ScriptDeath", type = "ResRef")
    @get:JsonProperty("ScriptDeath")
    @set:JsonProperty("ScriptDeath")
    var scriptDeath: String? = null

    @get:NwnField(name = "PaletteID", type = "BYTE")
    @get:JsonProperty("PaletteID")
    @set:JsonProperty("PaletteID")
    var paletteID: UByte? = null

    @get:NwnField(name = "ScriptDisturbed", type = "ResRef")
    @get:JsonProperty("ScriptDisturbed")
    @set:JsonProperty("ScriptDisturbed")
    var scriptDisturbed: String? = null

    @get:NwnField(name = "Str", type = "BYTE")
    @get:JsonProperty("Str")
    @set:JsonProperty("Str")
    var str: UByte? = null

    @get:NwnField(name = "Lootable", type = "BYTE")
    @get:JsonProperty("Lootable")
    @set:JsonProperty("Lootable")
    var lootable: UByte? = null

    @get:NwnField(name = "Conversation", type = "ResRef")
    @get:JsonProperty("Conversation")
    @set:JsonProperty("Conversation")
    var conversation: String? = null

    @get:NwnField(name = "Disarmable", type = "BYTE")
    @get:JsonProperty("Disarmable")
    @set:JsonProperty("Disarmable")
    var disarmable: UByte? = null

    @get:NwnField(name = "ScriptOnNotice", type = "ResRef")
    @get:JsonProperty("ScriptOnNotice")
    @set:JsonProperty("ScriptOnNotice")
    var scriptOnNotice: String? = null

    @get:NwnField(name = "Phenotype", type = "INT")
    @get:JsonProperty("Phenotype")
    @set:JsonProperty("Phenotype")
    var phenotype: Int? = null

    @get:NwnField(name = "IsPC", type = "BYTE")
    @get:JsonProperty("IsPC")
    @set:JsonProperty("IsPC")
    var isPC: UByte? = null

    @get:NwnField(name = "LawfulChaotic", type = "BYTE")
    @get:JsonProperty("LawfulChaotic")
    @set:JsonProperty("LawfulChaotic")
    var lawfulChaotic: UByte? = null

    @get:NwnField(name = "IsImmortal", type = "BYTE")
    @get:JsonProperty("IsImmortal")
    @set:JsonProperty("IsImmortal")
    var isImmortal: UByte? = null

    @get:NwnField(name = "MaxHitPoints", type = "SHORT")
    @get:JsonProperty("MaxHitPoints")
    @set:JsonProperty("MaxHitPoints")
    var maxHitPoints: Short? = null

    @get:NwnField(name = "SoundSetFile", type = "WORD")
    @get:JsonProperty("SoundSetFile")
    @set:JsonProperty("SoundSetFile")
    var soundSetFile: UShort? = null

    @get:NwnField(name = "Wis", type = "BYTE")
    @get:JsonProperty("Wis")
    @set:JsonProperty("Wis")
    var wis: UByte? = null

    @get:NwnField(name = "BodyPart_LThigh", type = "BYTE")
    @get:JsonProperty("BodyPartLThigh")
    @set:JsonProperty("BodyPartLThigh")
    var bodyPartLThigh: UByte? = null

    @get:NwnField(name = "ScriptSpawn", type = "ResRef")
    @get:JsonProperty("ScriptSpawn")
    @set:JsonProperty("ScriptSpawn")
    var scriptSpawn: String? = null

    @get:NwnField(name = "Equip_ItemList", type = "List", structType = 2)
    @get:JsonProperty("EquipItemList")
    @set:JsonProperty("EquipItemList")
    var equipItemList: List<UtcEquipItem>? = null

    @get:NwnField(name = "Color_Tattoo1", type = "BYTE")
    @get:JsonProperty("ColorTattoo1")
    @set:JsonProperty("ColorTattoo1")
    var colorTattoo1: UByte? = null

    @get:NwnField(name = "Gender", type = "BYTE")
    @get:JsonProperty("Gender")
    @set:JsonProperty("Gender")
    var gender: UByte? = null

    @get:NwnField(name = "NoPermDeath", type = "BYTE")
    @get:JsonProperty("NoPermDeath")
    @set:JsonProperty("NoPermDeath")
    var noPermDeath: UByte? = null

    @get:NwnField(name = "ScriptUserDefine", type = "ResRef")
    @get:JsonProperty("ScriptUserDefine")
    @set:JsonProperty("ScriptUserDefine")
    var scriptUserDefine: String? = null

    @get:NwnField(name = "Dex", type = "BYTE")
    @get:JsonProperty("Dex")
    @set:JsonProperty("Dex")
    var dex: UByte? = null

    @get:NwnField(name = "Wings_New", type = "DWORD")
    @get:JsonProperty("WingsNew")
    @set:JsonProperty("WingsNew")
    var wingsNew: UInt? = null

    @get:NwnField(name = "ScriptSpellAt", type = "ResRef")
    @get:JsonProperty("ScriptSpellAt")
    @set:JsonProperty("ScriptSpellAt")
    var scriptSpellAt: String? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef")
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "Cha", type = "BYTE")
    @get:JsonProperty("Cha")
    @set:JsonProperty("Cha")
    var cha: UByte? = null

    @get:NwnField(name = "willbonus", type = "SHORT")
    @get:JsonProperty("Willbonus")
    @set:JsonProperty("Willbonus")
    var willbonus: Short? = null

    @get:NwnField(name = "Race", type = "BYTE")
    @get:JsonProperty("Race")
    @set:JsonProperty("Race")
    var race: UByte? = null

    @get:NwnField(name = "Color_Tattoo2", type = "BYTE")
    @get:JsonProperty("ColorTattoo2")
    @set:JsonProperty("ColorTattoo2")
    var colorTattoo2: UByte? = null

    @get:NwnField(name = "ScriptRested", type = "ResRef")
    @get:JsonProperty("ScriptRested")
    @set:JsonProperty("ScriptRested")
    var scriptRested: String? = null

    @get:NwnField(name = "CRAdjust", type = "INT")
    @get:JsonProperty("CRAdjust")
    @set:JsonProperty("CRAdjust")
    var cRAdjust: Int? = null

    @get:NwnField(name = "ArmorPart_RFoot", type = "BYTE")
    @get:JsonProperty("ArmorPartRFoot")
    @set:JsonProperty("ArmorPartRFoot")
    var armorPartRFoot: UByte? = null

    @get:NwnField(name = "HitPoints", type = "SHORT")
    @get:JsonProperty("HitPoints")
    @set:JsonProperty("HitPoints")
    var hitPoints: Short? = null

    @get:NwnField(name = "CurrentHitPoints", type = "SHORT")
    @get:JsonProperty("CurrentHitPoints")
    @set:JsonProperty("CurrentHitPoints")
    var currentHitPoints: Short? = null

    @get:NwnField(name = "ScriptDialogue", type = "ResRef")
    @get:JsonProperty("ScriptDialogue")
    @set:JsonProperty("ScriptDialogue")
    var scriptDialogue: String? = null

    @get:NwnField(name = "Subrace", type = "CExoString")
    @get:JsonProperty("Subrace")
    @set:JsonProperty("Subrace")
    var subrace: String? = null

    @get:NwnField(name = "GoodEvil", type = "BYTE")
    @get:JsonProperty("GoodEvil")
    @set:JsonProperty("GoodEvil")
    var goodEvil: UByte? = null

    @get:NwnField(name = "refbonus", type = "SHORT")
    @get:JsonProperty("Refbonus")
    @set:JsonProperty("Refbonus")
    var refbonus: Short? = null

    @get:NwnField(name = "BodyPart_RFArm", type = "BYTE")
    @get:JsonProperty("BodyPartRFArm")
    @set:JsonProperty("BodyPartRFArm")
    var bodyPartRFArm: UByte? = null

    @get:NwnField(name = "BodyPart_LShoul", type = "BYTE")
    @get:JsonProperty("BodyPartLShoul")
    @set:JsonProperty("BodyPartLShoul")
    var bodyPartLShoul: UByte? = null

    @get:NwnField(name = "ScriptOnBlocked", type = "ResRef")
    @get:JsonProperty("ScriptOnBlocked")
    @set:JsonProperty("ScriptOnBlocked")
    var scriptOnBlocked: String? = null

    @get:NwnField(name = "Interruptable", type = "BYTE")
    @get:JsonProperty("Interruptable")
    @set:JsonProperty("Interruptable")
    var interruptable: UByte? = null

    @get:NwnField(name = "BodyPart_Neck", type = "BYTE")
    @get:JsonProperty("BodyPartNeck")
    @set:JsonProperty("BodyPartNeck")
    var bodyPartNeck: UByte? = null

    @get:NwnField(name = "ScriptEndRound", type = "ResRef")
    @get:JsonProperty("ScriptEndRound")
    @set:JsonProperty("ScriptEndRound")
    var scriptEndRound: String? = null

    @get:NwnField(name = "Color_Skin", type = "BYTE")
    @get:JsonProperty("ColorSkin")
    @set:JsonProperty("ColorSkin")
    var colorSkin: UByte? = null

    @get:NwnField(name = "SkillList", type = "List", structType = 0)
    @get:JsonProperty("SkillList")
    @set:JsonProperty("SkillList")
    var skillList: List<UtcSkill>? = null

    @get:NwnField(name = "ScriptHeartbeat", type = "ResRef")
    @get:JsonProperty("ScriptHeartbeat")
    @set:JsonProperty("ScriptHeartbeat")
    var scriptHeartbeat: String? = null

    @get:NwnField(name = "Description", type = "CExoLocString")
    @get:JsonProperty("Description")
    @set:JsonProperty("Description")
    var description: CExoLocString? = null

    @get:NwnField(name = "ScriptAttacked", type = "ResRef")
    @get:JsonProperty("ScriptAttacked")
    @set:JsonProperty("ScriptAttacked")
    var scriptAttacked: String? = null

    @get:NwnField(name = "BodyPart_LHand", type = "BYTE")
    @get:JsonProperty("BodyPartLHand")
    @set:JsonProperty("BodyPartLHand")
    var bodyPartLHand: UByte? = null

    @get:NwnField(name = "ItemList", type = "List", indexedStructId = true)
    @get:JsonProperty("ItemList")
    @set:JsonProperty("ItemList")
    var itemList: List<UtcItem>? = null

    @get:NwnField(name = "Tail_New", type = "DWORD")
    @get:JsonProperty("TailNew")
    @set:JsonProperty("TailNew")
    var tailNew: UInt? = null

    @get:NwnField(name = "BodyPart_Belt", type = "BYTE")
    @get:JsonProperty("BodyPartBelt")
    @set:JsonProperty("BodyPartBelt")
    var bodyPartBelt: UByte? = null

    @get:NwnField(name = "BodyPart_RThigh", type = "BYTE")
    @get:JsonProperty("BodyPartRThigh")
    @set:JsonProperty("BodyPartRThigh")
    var bodyPartRThigh: UByte? = null

    @get:NwnField(name = "BodyPart_LFoot", type = "BYTE")
    @get:JsonProperty("BodyPartLFoot")
    @set:JsonProperty("BodyPartLFoot")
    var bodyPartLFoot: UByte? = null

    @get:NwnField(name = "BodyPart_LBicep", type = "BYTE")
    @get:JsonProperty("BodyPartLBicep")
    @set:JsonProperty("BodyPartLBicep")
    var bodyPartLBicep: UByte? = null

    @get:NwnField(name = "BodyPart_RHand", type = "BYTE")
    @get:JsonProperty("BodyPartRHand")
    @set:JsonProperty("BodyPartRHand")
    var bodyPartRHand: UByte? = null

    @get:NwnField(name = "BodyPart_RShoul", type = "BYTE")
    @get:JsonProperty("BodyPartRShoul")
    @set:JsonProperty("BodyPartRShoul")
    var bodyPartRShoul: UByte? = null

    @get:NwnField(name = "BodyPart_Pelvis", type = "BYTE")
    @get:JsonProperty("BodyPartPelvis")
    @set:JsonProperty("BodyPartPelvis")
    var bodyPartPelvis: UByte? = null

    @get:NwnField(name = "Appearance_Head", type = "BYTE")
    @get:JsonProperty("AppearanceHead")
    @set:JsonProperty("AppearanceHead")
    var appearanceHead: UByte? = null

    @get:NwnField(name = "BodyPart_Torso", type = "BYTE")
    @get:JsonProperty("BodyPartTorso")
    @set:JsonProperty("BodyPartTorso")
    var bodyPartTorso: UByte? = null

    @get:NwnField(name = "BodyPart_RBicep", type = "BYTE")
    @get:JsonProperty("BodyPartRBicep")
    @set:JsonProperty("BodyPartRBicep")
    var bodyPartRBicep: UByte? = null

    @get:NwnField(name = "Appearance_Type", type = "WORD")
    @get:JsonProperty("AppearanceType")
    @set:JsonProperty("AppearanceType")
    var appearanceType: UShort? = null

    @get:NwnField(name = "BodyPart_LShin", type = "BYTE")
    @get:JsonProperty("BodyPartLShin")
    @set:JsonProperty("BodyPartLShin")
    var bodyPartLShin: UByte? = null

    @get:NwnField(name = "ScriptDamaged", type = "ResRef")
    @get:JsonProperty("ScriptDamaged")
    @set:JsonProperty("ScriptDamaged")
    var scriptDamaged: String? = null

    @get:NwnField(name = "Tail", type = "BYTE")
    @get:JsonProperty("Tail")
    @set:JsonProperty("Tail")
    var tail: UByte? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<UtcVarTable>? = null

    @get:NwnField(name = "Wings", type = "BYTE")
    @get:JsonProperty("Wings")
    @set:JsonProperty("Wings")
    var wings: UByte? = null
}

class UtcClass {

    @get:NwnField(name = "ClassLevel", type = "SHORT")
    @get:JsonProperty("ClassLevel")
    @set:JsonProperty("ClassLevel")
    var classLevel: Short? = null

    @get:NwnField(name = "Class", type = "INT")
    @get:JsonProperty("ClassX")
    @set:JsonProperty("ClassX")
    var classX: Int? = null

    @get:NwnField(name = "MemorizedList1", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList1")
    @set:JsonProperty("MemorizedList1")
    var memorizedList1: List<UtcMemorized>? = null

    @get:NwnField(name = "MemorizedList7", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList7")
    @set:JsonProperty("MemorizedList7")
    var memorizedList7: List<UtcMemorized>? = null

    @get:NwnField(name = "MemorizedList4", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList4")
    @set:JsonProperty("MemorizedList4")
    var memorizedList4: List<UtcMemorized>? = null

    @get:NwnField(name = "MemorizedList0", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList0")
    @set:JsonProperty("MemorizedList0")
    var memorizedList0: List<UtcMemorized>? = null

    @get:NwnField(name = "MemorizedList8", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList8")
    @set:JsonProperty("MemorizedList8")
    var memorizedList8: List<UtcMemorized>? = null

    @get:NwnField(name = "MemorizedList5", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList5")
    @set:JsonProperty("MemorizedList5")
    var memorizedList5: List<UtcMemorized>? = null

    @get:NwnField(name = "MemorizedList2", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList2")
    @set:JsonProperty("MemorizedList2")
    var memorizedList2: List<UtcMemorized>? = null

    @get:NwnField(name = "KnownList0", type = "List", structType = 3)
    @get:JsonProperty("KnownList0")
    @set:JsonProperty("KnownList0")
    var knownList0: List<UtcKnown>? = null

    @get:NwnField(name = "MemorizedList3", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList3")
    @set:JsonProperty("MemorizedList3")
    var memorizedList3: List<UtcMemorized>? = null

    @get:NwnField(name = "MemorizedList6", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList6")
    @set:JsonProperty("MemorizedList6")
    var memorizedList6: List<UtcMemorized>? = null

    @get:NwnField(name = "KnownList5", type = "List", structType = 3)
    @get:JsonProperty("KnownList5")
    @set:JsonProperty("KnownList5")
    var knownList5: List<UtcKnown>? = null

    @get:NwnField(name = "KnownList1", type = "List", structType = 3)
    @get:JsonProperty("KnownList1")
    @set:JsonProperty("KnownList1")
    var knownList1: List<UtcKnown>? = null

    @get:NwnField(name = "MemorizedList9", type = "List", structType = 3)
    @get:JsonProperty("MemorizedList9")
    @set:JsonProperty("MemorizedList9")
    var memorizedList9: List<UtcMemorized>? = null

    @get:NwnField(name = "KnownList3", type = "List", structType = 3)
    @get:JsonProperty("KnownList3")
    @set:JsonProperty("KnownList3")
    var knownList3: List<UtcKnown>? = null

    @get:NwnField(name = "KnownList2", type = "List", structType = 3)
    @get:JsonProperty("KnownList2")
    @set:JsonProperty("KnownList2")
    var knownList2: List<UtcKnown>? = null

    @get:NwnField(name = "KnownList7", type = "List", structType = 3)
    @get:JsonProperty("KnownList7")
    @set:JsonProperty("KnownList7")
    var knownList7: List<UtcKnown>? = null

    @get:NwnField(name = "KnownList4", type = "List", structType = 3)
    @get:JsonProperty("KnownList4")
    @set:JsonProperty("KnownList4")
    var knownList4: List<UtcKnown>? = null

    @get:NwnField(name = "KnownList6", type = "List", structType = 3)
    @get:JsonProperty("KnownList6")
    @set:JsonProperty("KnownList6")
    var knownList6: List<UtcKnown>? = null

    @get:NwnField(name = "KnownList9", type = "List", structType = 3)
    @get:JsonProperty("KnownList9")
    @set:JsonProperty("KnownList9")
    var knownList9: List<UtcKnown>? = null

    @get:NwnField(name = "KnownList8", type = "List", structType = 3)
    @get:JsonProperty("KnownList8")
    @set:JsonProperty("KnownList8")
    var knownList8: List<UtcKnown>? = null
}

class UtcEquipItem : HasStructId {

    @get:JsonProperty("StructId")
    @set:JsonProperty("StructId")
    override var structId: UInt? = null

    @get:NwnField(name = "EquippedRes", type = "ResRef")
    @get:JsonProperty("EquippedRes")
    @set:JsonProperty("EquippedRes")
    var equippedRes: String? = null

    @get:NwnField(name = "Dropable", type = "BYTE")
    @get:JsonProperty("Dropable")
    @set:JsonProperty("Dropable")
    var dropable: UByte? = null

    @get:NwnField(name = "Pickpocketable", type = "BYTE")
    @get:JsonProperty("Pickpocketable")
    @set:JsonProperty("Pickpocketable")
    var pickpocketable: UByte? = null
}

class UtcFeat {

    @get:NwnField(name = "Feat", type = "WORD")
    @get:JsonProperty("Feat")
    @set:JsonProperty("Feat")
    var feat: UShort? = null
}

class UtcItem {

    @get:NwnField(name = "Repos_Posy", type = "WORD")
    @get:JsonProperty("ReposPosy")
    @set:JsonProperty("ReposPosy")
    var reposPosy: UShort? = null

    @get:NwnField(name = "Dropable", type = "BYTE")
    @get:JsonProperty("Dropable")
    @set:JsonProperty("Dropable")
    var dropable: UByte? = null

    @get:NwnField(name = "Repos_PosX", type = "WORD")
    @get:JsonProperty("ReposPosX")
    @set:JsonProperty("ReposPosX")
    var reposPosX: UShort? = null

    @get:NwnField(name = "InventoryRes", type = "ResRef")
    @get:JsonProperty("InventoryRes")
    @set:JsonProperty("InventoryRes")
    var inventoryRes: String? = null

    @get:NwnField(name = "Pickpocketable", type = "BYTE")
    @get:JsonProperty("Pickpocketable")
    @set:JsonProperty("Pickpocketable")
    var pickpocketable: UByte? = null
}

class UtcKnown {

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

class UtcMemorized {

    @get:NwnField(name = "SpellFlags", type = "BYTE")
    @get:JsonProperty("SpellFlags")
    @set:JsonProperty("SpellFlags")
    var spellFlags: UByte? = null

    @get:NwnField(name = "SpellMetaMagic", type = "BYTE")
    @get:JsonProperty("SpellMetaMagic")
    @set:JsonProperty("SpellMetaMagic")
    var spellMetaMagic: UByte? = null

    @get:NwnField(name = "Spell", type = "WORD")
    @get:JsonProperty("Spell")
    @set:JsonProperty("Spell")
    var spell: UShort? = null
}

class UtcSkill {

    @get:NwnField(name = "Rank", type = "BYTE")
    @get:JsonProperty("Rank")
    @set:JsonProperty("Rank")
    var rank: UByte? = null
}

class UtcSpecAbility {

    @get:NwnField(name = "Spell", type = "WORD")
    @get:JsonProperty("Spell")
    @set:JsonProperty("Spell")
    var spell: UShort? = null

    @get:NwnField(name = "SpellCasterLevel", type = "BYTE")
    @get:JsonProperty("SpellCasterLevel")
    @set:JsonProperty("SpellCasterLevel")
    var spellCasterLevel: UByte? = null

    @get:NwnField(name = "SpellFlags", type = "BYTE")
    @get:JsonProperty("SpellFlags")
    @set:JsonProperty("SpellFlags")
    var spellFlags: UByte? = null
}

class UtcTemplate {

    @get:NwnField(name = "TemplateID", type = "WORD")
    @get:JsonProperty("TemplateID")
    @set:JsonProperty("TemplateID")
    var templateID: UShort? = null
}

class UtcVarTable {

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
