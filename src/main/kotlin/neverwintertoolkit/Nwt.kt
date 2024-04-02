package neverwintertoolkit

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.serde.annotation.Serdeable
import java.io.IOException
import java.io.InputStream
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.inputStream
import kotlin.io.path.writeText
import kotlin.streams.asStream

@ReflectiveAccess
class Nwt {
    var source: String? = null
    var target: String? = null
    var name: String? = null
    var description: String? = null
    var version: String? = null
    var url: String? = null
    var author: String? = null
    var rules: RuleList? = null
    var overwrite: Boolean = false
    var erfPath: List<String> = emptyList()
    var dirPath: List<String> = emptyList()

    @get:JsonIgnore
    val sourcePath: Path
        get() {
            val sfile = source ?: throw RuntimeException("source attribute required in nwt.json")
            if (sfile.isBlank()) throw RuntimeException("source attribute required in nwt.json")
            return Paths.get(globalSettings.expandVariables(sfile))
        }

    @get:JsonIgnore
    val targetPath: Path
        get() {
            val sfile = target ?: throw RuntimeException("target attribute required in nwt.json")
            if (sfile.isBlank()) throw RuntimeException("target attribute required in nwt.json")
            return Paths.get(globalSettings.expandVariables(sfile))
        }

    fun toJson(nwtCfg: Path) {
        val mapper = getMapper(JsonSettings(exclude = setOf(XListDeserializer::class, XListSerializer::class)))
        nwtCfg.writeText(mapper.writeValueAsString(this))
    }

    override fun toString(): String {
        return "Nwt(source=$source, target=$target, name=$name, description=$description, url=$url, author=$author, rules=$rules, overwrite=$overwrite)"
    }

    @get:JsonIgnore
    val isModule: Boolean
        get() = target?.lowercase()?.endsWith(".mod") == true ||
                target?.lowercase()?.endsWith(".nwm") == true

    @get:JsonIgnore
    val isHak: Boolean
        get() = target?.lowercase()?.endsWith(".hak") == true

    companion object {
        fun getMapper(jsonSettings: JsonSettings): ObjectMapper {
            val mapper = getBaseMapper(jsonSettings)

            val module = SimpleModule()

            val exclude = jsonSettings.exclude

            if (!exclude.contains(XListSerializer::class))
                module.addSerializer(RuleList::class.java, XListSerializer(RuleList::class.java, jsonSettings))

//            if (!exclude.contains(XListSerializer::class))
//                module.addSerializer(SourceList::class.java, XListSerializer(SourceList::class.java, jsonSettings))

            if (!exclude.contains(RuleListDeserializer::class))
                module.addDeserializer(RuleList::class.java, RuleListDeserializer(jsonSettings))

//            if (!exclude.contains(SourceListDeserializer::class))
//                module.addDeserializer(SourceList::class.java, SourceListDeserializer(jsonSettings))

            mapper.registerModule(module)

            return mapper
        }

        fun parseJson(nwtJson: Path): List<Nwt> {
            nwtJson.inputStream().use { input ->
                return parseJson(input)
            }
        }

        fun parseJson(nwtJson: InputStream): List<Nwt> {
            val mapper = getMapper(JsonSettings())
            val tree = mapper.readTree(nwtJson)
            val arr = if (tree.isArray)
                mapper.treeToValue(tree, Array<Nwt>::class.java)
            else
                arrayOf(mapper.treeToValue(tree, Nwt::class.java))

            return arr.toList()
        }

        fun toJson(nwt: List<Nwt>, file: Path) {
            val mapper = getMapper(JsonSettings())
            file.writeText(mapper.writeValueAsString(nwt) + "\n")
        }
    }

}

@Serdeable
@ReflectiveAccess
@Introspected
open class XList : ArrayList<Pair<String, String>>()

//@Serdeable
//@ReflectiveAccess
//@Introspected
//class SourceList : XList()

@Serdeable
@ReflectiveAccess
@Introspected
class RuleList : XList()
class RuleListDeserializer(jsonSettings: JsonSettings) : XListDeserializer<RuleList>(RuleList::class.java, jsonSettings)
//class SourceListDeserializer(jsonSettings: JsonSettings) : XListDeserializer<SourceList>(SourceList::class.java, jsonSettings)

open class XListDeserializer<T : XList>(val clazz: Class<T>, val jsonSettings: JsonSettings) : StdDeserializer<T>(clazz) {

//    val objectMapper =
//        Nwt.getMapper(jsonSettings.clone(exclude = setOf(*jsonSettings.exclude.toTypedArray(), XListDeserializer::class, XListSerializer::class)))

    @Throws(IOException::class, JacksonException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): T? {
        val tn: TreeNode = jp.readValueAsTree()

        val ruleList = clazz.getDeclaredConstructor().newInstance()
        if (tn.isObject) {
            tn.fieldNames().asSequence().asStream().map {
                ruleList.add(Pair(it, (tn.path(it) as TextNode).asText()))
            }.toList()
        } else {
            throw RuntimeException("unexpected")
        }

        return ruleList
    }

}

class XListSerializer<T : XList>(clazz: Class<T>, jsonSettings: JsonSettings) : StdSerializer<T>(clazz) {

//    val objectMapper = getBaseMapper(jsonSettings.clone(exclude = setOf(*jsonSettings.exclude.toTypedArray(),
//        XListSerializer::class, XListDeserializer::class)))

    override fun serialize(value: T?, gen: JsonGenerator, provider: SerializerProvider?) {
        gen.writeStartObject()
        value?.forEach {
            gen.writeStringField(it.first, it.second.toString())
        }
        gen.writeEndObject()
    }
}
