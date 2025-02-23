package neverwintertoolkit.model.uti


// Generated 2024-01-31T19:09:07.206999
import com.fasterxml.jackson.annotation.JsonProperty
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import neverwintertoolkit.model.annotation.NwnField
import java.io.OutputStream

class Uti : GffObj {
    companion object {
        val factory: GffFactory<Uti> = GenericGffFactory(Uti::class.java, ".uti")
    }
    override fun writeGff(output: OutputStream) = GenericGffWriter(this, ".uti").writeGff(output)

    @get:NwnField(name = "PaletteID", type = "BYTE", gffOrder = 19)
    @get:JsonProperty("PaletteID")
    @set:JsonProperty("PaletteID")
    var paletteID: UByte? = null

    @get:NwnField(name = "TemplateResRef", type = "ResRef", gffOrder = 1)
    @get:JsonProperty("TemplateResRef")
    @set:JsonProperty("TemplateResRef")
    var templateResRef: String? = null

    @get:NwnField(name = "Stolen", type = "BYTE", gffOrder = 9)
    @get:JsonProperty("Stolen")
    @set:JsonProperty("Stolen")
    var stolen: UByte? = null

    @get:NwnField(name = "ArmorPart_LShin", type = "BYTE")
    @get:JsonProperty("ArmorPartLShin")
    @set:JsonProperty("ArmorPartLShin")
    var armorPartLShin: UByte? = null

    @get:NwnField(name = "ArmorPart_Robe", type = "BYTE")
    @get:JsonProperty("ArmorPartRobe")
    @set:JsonProperty("ArmorPartRobe")
    var armorPartRobe: UByte? = null

    @get:NwnField(name = "Comment", type = "CExoString", gffOrder = 20, blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("Comment")
    @set:JsonProperty("Comment")
    var comment: String? = null

    @get:NwnField(name = "Cursed", type = "BYTE", gffOrder = 14)
    @get:JsonProperty("Cursed")
    @set:JsonProperty("Cursed")
    var cursed: UByte? = null

    @get:NwnField(name = "DescIdentified", type = "CExoLocString", gffOrder = 5)
    @get:JsonProperty("DescIdentified")
    @set:JsonProperty("DescIdentified")
    var descIdentified: CExoLocString? = null

    @get:NwnField(name = "StackSize", type = "WORD", gffOrder = 10)
    @get:JsonProperty("StackSize")
    @set:JsonProperty("StackSize")
    var stackSize: UShort? = null

    @get:NwnField(name = "ModelPart2", type = "BYTE", gffOrder = 16)
    @get:JsonProperty("ModelPart2")
    @set:JsonProperty("ModelPart2")
    var modelPart2: UByte? = null

    @get:NwnField(name = "ModelPart1", type = "BYTE", gffOrder = 15)
    @get:JsonProperty("ModelPart1")
    @set:JsonProperty("ModelPart1")
    var modelPart1: UByte? = null

    @get:NwnField(name = "ArmorPart_RThigh", type = "BYTE")
    @get:JsonProperty("ArmorPartRThigh")
    @set:JsonProperty("ArmorPartRThigh")
    var armorPartRThigh: UByte? = null

    @get:NwnField(name = "PropertiesList", type = "List", structType = 0, gffOrder = 18)
    @get:JsonProperty("PropertiesList")
    @set:JsonProperty("PropertiesList")
    var propertiesList: List<UtiProperties>? = null

    @get:NwnField(name = "Cost", type = "DWORD", gffOrder = 8)
    @get:JsonProperty("Cost")
    @set:JsonProperty("Cost")
    var cost: UInt? = null

    @get:NwnField(name = "BaseItem", type = "INT", gffOrder = 2)
    @get:JsonProperty("BaseItem")
    @set:JsonProperty("BaseItem")
    var baseItem: Int? = null

    @get:NwnField(name = "Plot", type = "BYTE", gffOrder = 11)
    @get:JsonProperty("Plot")
    @set:JsonProperty("Plot")
    var plot: UByte? = null

    @get:NwnField(name = "Tag", type = "CExoString", gffOrder = 6)
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "ArmorPart_RBicep", type = "BYTE")
    @get:JsonProperty("ArmorPartRBicep")
    @set:JsonProperty("ArmorPartRBicep")
    var armorPartRBicep: UByte? = null

    @get:NwnField(name = "Charges", type = "BYTE", gffOrder = 7)
    @get:JsonProperty("Charges")
    @set:JsonProperty("Charges")
    var charges: UByte? = null

    @get:NwnField(name = "AddCost", type = "DWORD", gffOrder = 12)
    @get:JsonProperty("AddCost")
    @set:JsonProperty("AddCost")
    var addCost: UInt? = null

    @get:NwnField(name = "LocalizedName", type = "CExoLocString", gffOrder = 3)
    @get:JsonProperty("LocalizedName")
    @set:JsonProperty("LocalizedName")
    var localizedName: CExoLocString? = null

    @get:NwnField(name = "ArmorPart_LBicep", type = "BYTE")
    @get:JsonProperty("ArmorPartLBicep")
    @set:JsonProperty("ArmorPartLBicep")
    var armorPartLBicep: UByte? = null

    @get:NwnField(name = "ArmorPart_LFArm", type = "BYTE")
    @get:JsonProperty("ArmorPartLFArm")
    @set:JsonProperty("ArmorPartLFArm")
    var armorPartLFArm: UByte? = null

    @get:NwnField(name = "Identified", type = "BYTE", gffOrder = 13)
    @get:JsonProperty("Identified")
    @set:JsonProperty("Identified")
    var identified: UByte? = null

    @get:NwnField(name = "Description", type = "CExoLocString", gffOrder = 4, blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("Description")
    @set:JsonProperty("Description")
    var description: CExoLocString? = null

    @get:NwnField(name = "ArmorPart_RFoot", type = "BYTE")
    @get:JsonProperty("ArmorPartRFoot")
    @set:JsonProperty("ArmorPartRFoot")
    var armorPartRFoot: UByte? = null

    @get:NwnField(name = "ModelPart3", type = "BYTE", gffOrder = 17)
    @get:JsonProperty("ModelPart3")
    @set:JsonProperty("ModelPart3")
    var modelPart3: UByte? = null

    @get:NwnField(name = "ArmorPart_Torso", type = "BYTE")
    @get:JsonProperty("ArmorPartTorso")
    @set:JsonProperty("ArmorPartTorso")
    var armorPartTorso: UByte? = null

    @get:NwnField(name = "ArmorPart_RFArm", type = "BYTE")
    @get:JsonProperty("ArmorPartRFArm")
    @set:JsonProperty("ArmorPartRFArm")
    var armorPartRFArm: UByte? = null

    @get:NwnField(name = "Metal2Color", type = "BYTE")
    @get:JsonProperty("Metal2Color")
    @set:JsonProperty("Metal2Color")
    var metal2Color: UByte? = null

    @get:NwnField(name = "Cloth2Color", type = "BYTE")
    @get:JsonProperty("Cloth2Color")
    @set:JsonProperty("Cloth2Color")
    var cloth2Color: UByte? = null

    @get:NwnField(name = "ArmorPart_LHand", type = "BYTE")
    @get:JsonProperty("ArmorPartLHand")
    @set:JsonProperty("ArmorPartLHand")
    var armorPartLHand: UByte? = null

    @get:NwnField(name = "Metal1Color", type = "BYTE")
    @get:JsonProperty("Metal1Color")
    @set:JsonProperty("Metal1Color")
    var metal1Color: UByte? = null

    @get:NwnField(name = "ArmorPart_RShin", type = "BYTE")
    @get:JsonProperty("ArmorPartRShin")
    @set:JsonProperty("ArmorPartRShin")
    var armorPartRShin: UByte? = null

    @get:NwnField(name = "ArmorPart_Neck", type = "BYTE")
    @get:JsonProperty("ArmorPartNeck")
    @set:JsonProperty("ArmorPartNeck")
    var armorPartNeck: UByte? = null

    @get:NwnField(name = "Leather1Color", type = "BYTE")
    @get:JsonProperty("Leather1Color")
    @set:JsonProperty("Leather1Color")
    var leather1Color: UByte? = null

    @get:NwnField(name = "Leather2Color", type = "BYTE")
    @get:JsonProperty("Leather2Color")
    @set:JsonProperty("Leather2Color")
    var leather2Color: UByte? = null

    @get:NwnField(name = "ArmorPart_RShoul", type = "BYTE")
    @get:JsonProperty("ArmorPartRShoul")
    @set:JsonProperty("ArmorPartRShoul")
    var armorPartRShoul: UByte? = null

    @get:NwnField(name = "ArmorPart_RHand", type = "BYTE")
    @get:JsonProperty("ArmorPartRHand")
    @set:JsonProperty("ArmorPartRHand")
    var armorPartRHand: UByte? = null

    @get:NwnField(name = "ArmorPart_Belt", type = "BYTE")
    @get:JsonProperty("ArmorPartBelt")
    @set:JsonProperty("ArmorPartBelt")
    var armorPartBelt: UByte? = null

    @get:NwnField(name = "ArmorPart_LThigh", type = "BYTE")
    @get:JsonProperty("ArmorPartLThigh")
    @set:JsonProperty("ArmorPartLThigh")
    var armorPartLThigh: UByte? = null

    @get:NwnField(name = "ArmorPart_Pelvis", type = "BYTE")
    @get:JsonProperty("ArmorPartPelvis")
    @set:JsonProperty("ArmorPartPelvis")
    var armorPartPelvis: UByte? = null

    @get:NwnField(name = "ArmorPart_LShoul", type = "BYTE")
    @get:JsonProperty("ArmorPartLShoul")
    @set:JsonProperty("ArmorPartLShoul")
    var armorPartLShoul: UByte? = null

    @get:NwnField(name = "ArmorPart_LFoot", type = "BYTE")
    @get:JsonProperty("ArmorPartLFoot")
    @set:JsonProperty("ArmorPartLFoot")
    var armorPartLFoot: UByte? = null

    @get:NwnField(name = "Cloth1Color", type = "BYTE")
    @get:JsonProperty("Cloth1Color")
    @set:JsonProperty("Cloth1Color")
    var cloth1Color: UByte? = null

    @get:NwnField(name = "VarTable", type = "List", structType = 0)
    @get:JsonProperty("VarTable")
    @set:JsonProperty("VarTable")
    var varTable: List<UtiVarTable>? = null
}

class UtiProperties {

    @get:NwnField(name = "PropertyName", type = "WORD")
    @get:JsonProperty("PropertyName")
    @set:JsonProperty("PropertyName")
    var propertyName: UShort? = null

    @get:NwnField(name = "ChanceAppear", type = "BYTE")
    @get:JsonProperty("ChanceAppear")
    @set:JsonProperty("ChanceAppear")
    var chanceAppear: UByte? = null

    @get:NwnField(name = "Param1Value", type = "BYTE")
    @get:JsonProperty("Param1Value")
    @set:JsonProperty("Param1Value")
    var param1Value: UByte? = null

    @get:NwnField(name = "Param1", type = "BYTE")
    @get:JsonProperty("Param1")
    @set:JsonProperty("Param1")
    var param1: UByte? = null

    @get:NwnField(name = "CostTable", type = "BYTE")
    @get:JsonProperty("CostTable")
    @set:JsonProperty("CostTable")
    var costTable: UByte? = null

    @get:NwnField(name = "Subtype", type = "WORD")
    @get:JsonProperty("Subtype")
    @set:JsonProperty("Subtype")
    var subtype: UShort? = null

    @get:NwnField(name = "CostValue", type = "WORD")
    @get:JsonProperty("CostValue")
    @set:JsonProperty("CostValue")
    var costValue: UShort? = null
}

class UtiVarTable {

    @get:NwnField(name = "Name", type = "CExoString")
    @get:JsonProperty("Name")
    @set:JsonProperty("Name")
    var name: String? = null

    @get:NwnField(name = "Type", type = "DWORD")
    @get:JsonProperty("Type")
    @set:JsonProperty("Type")
    var type: UInt? = null

    @get:NwnField(name = "Value", type = "")
    @get:JsonProperty("Value")
    @set:JsonProperty("Value")
    var value: Any? = null
}
