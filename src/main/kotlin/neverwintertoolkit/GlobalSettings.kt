package neverwintertoolkit

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.json.JsonReadFeature
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.PrintStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.pathString
import kotlin.system.exitProcess

/**
 * Don't put any defaults in the constructor/default values that could ever fail
 */
data class GlobalSettings(
    var simplifyJson: Boolean = true,
    var useJson5Extension: Boolean = true,
    var nwnRoot: String = "",
    var nwnHome: String = "",
    val scriptCompilerBinary: String = "nwn_script_comp",
) {

    @JsonIgnore
    var strictTestingMode: Boolean = false

    @JsonIgnore
    fun getJsonExtension(): String = if (useJson5Extension) ".json5" else ".json"

    fun write(configPath: Path) {
        getMyMapper().writeValue(configPath.toFile(), this)
    }

    fun saveToConfigFile() {
        write(configPath)
    }

    fun dump(out: PrintStream) {
        out.print(getMyMapper().writeValueAsString(this))
        out.flush()
    }

    fun expandVariables(sfile: String): String {
        return sfile.replace("\${nwnHome}", globalSettings.nwnHome)
            .replace("\${nwnRoot}", globalSettings.nwnRoot)
    }

    fun substitueVariables(str: String): String {
        return str.replace(globalSettings.nwnHome, "\${nwnHome}")
            .replace(globalSettings.nwnRoot, "\${nwnRoot}")
    }

    companion object {
        private fun findHome(): Path {
            val path = Paths.get(System.getProperty("user.home"))
                .resolve("Documents")
                .resolve("Neverwinter Nights")

            if (!Files.isDirectory(path)) throw RuntimeException("Failed to find Neverwinter Nights home.   Directory not found: '$path'")
            return path
        }

        private fun findRoot(): Path {
            val path = Paths.get(System.getProperty("user.home"))
                .resolve("Library")
                .resolve("Application Support")
                .resolve("Steam")
                .resolve("steamapps")
                .resolve("common")
                .resolve("Neverwinter Nights")

            if (!Files.isDirectory(path)) throw RuntimeException("Failed to find Neverwinter Nights root.   Directory not found: '$path'")
            return path
        }

        fun readConfig(): GlobalSettings {
            val dir = configPath.parent

            if (Files.notExists(configPath)) {
                println("config file '$configPath' does not exist.   Use 'nwt config init'")
                exitProcess(1)
//                throw Exception("config file '$configPath' does not exist.   Use 'nwt config init'")
            }

            return getMyMapper().readValue(Files.newBufferedReader(configPath), GlobalSettings::class.java)
        }

        val configPath = Paths.get(System.getProperty("user.home"), ".config", "nwt", "nwt-config.json5")

        fun generateDefault(): GlobalSettings {
            return GlobalSettings(
                simplifyJson = true,
                useJson5Extension = true,
                nwnRoot = findRoot().pathString,
                nwnHome = findHome().pathString,
                scriptCompilerBinary = "nwn_script_comp"
                // scriptCompilerBinary = "nwnsc"
            )
        }

        private fun getMyMapper(): ObjectMapper {
            val mapper = ObjectMapper()
                .enable(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature())
                .enable(JsonReadFeature.ALLOW_TRAILING_COMMA.mappedFeature())
                .enable(JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature())
                .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature())
                .enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature())
                .enable(JsonReadFeature.ALLOW_JAVA_COMMENTS.mappedFeature())
                .enable(JsonReadFeature.ALLOW_LEADING_DECIMAL_POINT_FOR_NUMBERS.mappedFeature())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION)
                .registerModule(KotlinModule.Builder().build())
            //.registerModule(KotlinModule())

            mapper.setConfig(
                mapper.serializationConfig
                    .with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
                    .with(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
            )

            val pp = DefaultPrettyPrinter()
            pp.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE)
            mapper.setDefaultPrettyPrinter(pp)

            mapper.enable(SerializationFeature.INDENT_OUTPUT)
            return mapper
        }
    }
}
