package neverwintertoolkit.model.are

// Generated 2024-02-01T13:06:42.232830
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.serde.annotation.Serdeable
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GenericGffFactory
import neverwintertoolkit.file.gff.GenericGffWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import neverwintertoolkit.model.annotation.NwnField
import java.io.OutputStream

@JsonIgnoreProperties("StructId")
@Serdeable
@ReflectiveAccess
@Introspected
class Are : GffObj {
    companion object {
        val factory: GffFactory<Are> = GenericGffFactory(Are::class.java, ".are")
    }

    override fun writeGff(output: OutputStream) = GenericGffWriter(this, ".are").writeGff(output)

    @get:NwnField(name = "ID", type = "INT")
    @get:JsonProperty("Id")
    @set:JsonProperty("Id")
    var id: Int? = null

    @get:NwnField(name = "ModSpotCheck", type = "INT")
    @get:JsonProperty("ModSpotCheck")
    @set:JsonProperty("ModSpotCheck")
    var modSpotCheck: Int? = null

    @get:NwnField(name = "PlayerVsPlayer", type = "BYTE", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("PlayerVsPlayer")
    @set:JsonProperty("PlayerVsPlayer")
    var playerVsPlayer: UByte? = null

    @get:NwnField(name = "SunFogAmount", type = "BYTE")
    @get:JsonProperty("SunFogAmount")
    @set:JsonProperty("SunFogAmount")
    var sunFogAmount: UByte? = null

    @get:NwnField(name = "OnExit", type = "ResRef")
    @get:JsonProperty("OnExit")
    @set:JsonProperty("OnExit")
    var onExit: String? = null

    @get:NwnField(name = "Width", type = "INT")
    @get:JsonProperty("Width")
    @set:JsonProperty("Width")
    var width: Int? = null

    @get:NwnField(name = "Creator_ID", type = "INT")
    @get:JsonProperty("CreatorId")
    @set:JsonProperty("CreatorId")
    var creatorId: Int? = null

    @get:NwnField(name = "LightingScheme", type = "BYTE")
    @get:JsonProperty("LightingScheme")
    @set:JsonProperty("LightingScheme")
    var lightingScheme: UByte? = null

    @get:NwnField(name = "DayNightCycle", type = "BYTE")
    @get:JsonProperty("DayNightCycle")
    @set:JsonProperty("DayNightCycle")
    var dayNightCycle: UByte? = null

    @get:NwnField(name = "Height", type = "INT")
    @get:JsonProperty("Height")
    @set:JsonProperty("Height")
    var height: Int? = null

    @get:NwnField(name = "Tile_List", type = "List", structType = 1)
    @get:JsonProperty("TileList")
    @set:JsonProperty("TileList")
    var tileList: List<AreTile>? = null

    @get:NwnField(name = "NoRest", type = "BYTE")
    @get:JsonProperty("NoRest")
    @set:JsonProperty("NoRest")
    var noRest: UByte? = null

    @get:NwnField(name = "SunAmbientColor", type = "DWORD")
    @get:JsonProperty("SunAmbientColor")
    @set:JsonProperty("SunAmbientColor")
    var sunAmbientColor: UInt? = null

    @get:NwnField(name = "ChanceLightning", type = "INT")
    @get:JsonProperty("ChanceLightning")
    @set:JsonProperty("ChanceLightning")
    var chanceLightning: Int? = null

    @get:NwnField(name = "Tag", type = "CExoString")
    @get:JsonProperty("Tag")
    @set:JsonProperty("Tag")
    var tag: String? = null

    @get:NwnField(name = "Version", type = "DWORD")
    @get:JsonProperty("Version")
    @set:JsonProperty("Version")
    var version: UInt? = null

    @get:NwnField(name = "Flags", type = "DWORD")
    @get:JsonProperty("Flags")
    @set:JsonProperty("Flags")
    var flags: UInt? = null

    @get:NwnField(name = "SunDiffuseColor", type = "DWORD")
    @get:JsonProperty("SunDiffuseColor")
    @set:JsonProperty("SunDiffuseColor")
    var sunDiffuseColor: UInt? = null

    @get:NwnField(name = "MoonAmbientColor", type = "DWORD")
    @get:JsonProperty("MoonAmbientColor")
    @set:JsonProperty("MoonAmbientColor")
    var moonAmbientColor: UInt? = null

    @get:NwnField(name = "OnUserDefined", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnUserDefined")
    @set:JsonProperty("OnUserDefined")
    var onUserDefined: String? = null

    @get:NwnField(name = "LoadScreenID", type = "WORD")
    @get:JsonProperty("LoadScreenId")
    @set:JsonProperty("LoadScreenId")
    var loadScreenId: UShort? = null

    @get:NwnField(name = "SunShadows", type = "BYTE")
    @get:JsonProperty("SunShadows")
    @set:JsonProperty("SunShadows")
    var sunShadows: UByte? = null

    @get:NwnField(name = "OnEnter", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnEnter")
    @set:JsonProperty("OnEnter")
    var onEnter: String? = null

    @get:NwnField(name = "MoonFogColor", type = "DWORD")
    @get:JsonProperty("MoonFogColor")
    @set:JsonProperty("MoonFogColor")
    var moonFogColor: UInt? = null

    @get:NwnField(name = "MoonFogAmount", type = "BYTE")
    @get:JsonProperty("MoonFogAmount")
    @set:JsonProperty("MoonFogAmount")
    var moonFogAmount: UByte? = null

    @get:NwnField(name = "OnHeartbeat", type = "ResRef", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("OnHeartbeat")
    @set:JsonProperty("OnHeartbeat")
    var onHeartbeat: String? = null

    @get:NwnField(name = "MoonShadows", type = "BYTE")
    @get:JsonProperty("MoonShadows")
    @set:JsonProperty("MoonShadows")
    var moonShadows: UByte? = null

    @get:NwnField(name = "Comments", type = "CExoString", blankBehavior = neverwintertoolkit.model.annotation.BlankBehavior.GENERATE)
    @get:JsonProperty("Comments")
    @set:JsonProperty("Comments")
    var comments: String? = null

    @get:NwnField(name = "Expansion_List", type = "List")
    @get:JsonProperty("ExpansionList")
    @set:JsonProperty("ExpansionList")
    var expansionList: List<AreExpansion>? = null

    @get:NwnField(name = "ChanceRain", type = "INT")
    @get:JsonProperty("ChanceRain")
    @set:JsonProperty("ChanceRain")
    var chanceRain: Int? = null

    @get:NwnField(name = "ChanceSnow", type = "INT")
    @get:JsonProperty("ChanceSnow")
    @set:JsonProperty("ChanceSnow")
    var chanceSnow: Int? = null

    @get:NwnField(name = "SkyBox", type = "BYTE")
    @get:JsonProperty("SkyBox")
    @set:JsonProperty("SkyBox")
    var skyBox: UByte? = null

    @get:NwnField(name = "IsNight", type = "BYTE")
    @get:JsonProperty("IsNight")
    @set:JsonProperty("IsNight")
    var isNight: UByte? = null

    @get:NwnField(name = "MoonDiffuseColor", type = "DWORD")
    @get:JsonProperty("MoonDiffuseColor")
    @set:JsonProperty("MoonDiffuseColor")
    var moonDiffuseColor: UInt? = null

    @get:NwnField(name = "WindPower", type = "INT")
    @get:JsonProperty("WindPower")
    @set:JsonProperty("WindPower")
    var windPower: Int? = null

    @get:NwnField(name = "FogClipDist", type = "FLOAT")
    @get:JsonProperty("FogClipDist")
    @set:JsonProperty("FogClipDist")
    var fogClipDist: Float? = null

    @get:NwnField(name = "ShadowOpacity", type = "BYTE")
    @get:JsonProperty("ShadowOpacity")
    @set:JsonProperty("ShadowOpacity")
    var shadowOpacity: UByte? = null

    @get:NwnField(name = "Name", type = "CExoLocString")
    @get:JsonProperty("Name")
    @set:JsonProperty("Name")
    var name: CExoLocString? = null

    @get:NwnField(name = "Tileset", type = "ResRef")
    @get:JsonProperty("Tileset")
    @set:JsonProperty("Tileset")
    var tileset: String? = null

    @get:NwnField(name = "ModListenCheck", type = "INT")
    @get:JsonProperty("ModListenCheck")
    @set:JsonProperty("ModListenCheck")
    var modListenCheck: Int? = null

    @get:NwnField(name = "ResRef", type = "ResRef")
    @get:JsonProperty("ResRef")
    @set:JsonProperty("ResRef")
    var resRef: String? = null

    @get:NwnField(name = "SunFogColor", type = "DWORD")
    @get:JsonProperty("SunFogColor")
    @set:JsonProperty("SunFogColor")
    var sunFogColor: UInt? = null

    @get:NwnField(name = "TileBrdrDisabled", type = "BYTE")
    @get:JsonProperty("TileBrdrDisabled")
    @set:JsonProperty("TileBrdrDisabled")
    var tileBrdrDisabled: UByte? = null
}

class AreExpansion {
}

@Serdeable
@ReflectiveAccess
@Introspected
@JsonIgnoreProperties("StructId")
class AreTile {

    @get:NwnField(name = "Tile_AnimLoop3", type = "BYTE")
    @get:JsonProperty("TileAnimLoop3")
    @set:JsonProperty("TileAnimLoop3")
    var tileAnimLoop3: UByte? = null

    @get:NwnField(name = "Tile_AnimLoop1", type = "BYTE")
    @get:JsonProperty("TileAnimLoop1")
    @set:JsonProperty("TileAnimLoop1")
    var tileAnimLoop1: UByte? = null

    @get:NwnField(name = "Tile_AnimLoop2", type = "BYTE")
    @get:JsonProperty("TileAnimLoop2")
    @set:JsonProperty("TileAnimLoop2")
    var tileAnimLoop2: UByte? = null

    @get:NwnField(name = "Tile_MainLight2", type = "BYTE")
    @get:JsonProperty("TileMainLight2")
    @set:JsonProperty("TileMainLight2")
    var tileMainLight2: UByte? = null

    @get:NwnField(name = "Tile_SrcLight2", type = "BYTE")
    @get:JsonProperty("TileSrcLight2")
    @set:JsonProperty("TileSrcLight2")
    var tileSrcLight2: UByte? = null

    @get:NwnField(name = "Tile_MainLight1", type = "BYTE")
    @get:JsonProperty("TileMainLight1")
    @set:JsonProperty("TileMainLight1")
    var tileMainLight1: UByte? = null

    @get:NwnField(name = "Tile_Orientation", type = "INT")
    @get:JsonProperty("TileOrientation")
    @set:JsonProperty("TileOrientation")
    var tileOrientation: Int? = null

    @get:NwnField(name = "Tile_Height", type = "INT")
    @get:JsonProperty("TileHeight")
    @set:JsonProperty("TileHeight")
    var tileHeight: Int? = null

    @get:NwnField(name = "Tile_ID", type = "INT")
    @get:JsonProperty("TileId")
    @set:JsonProperty("TileId")
    var tileId: Int? = null

    @get:NwnField(name = "Tile_SrcLight1", type = "BYTE")
    @get:JsonProperty("TileSrcLight1")
    @set:JsonProperty("TileSrcLight1")
    var tileSrcLight1: UByte? = null
}
