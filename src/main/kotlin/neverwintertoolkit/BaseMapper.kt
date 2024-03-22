package neverwintertoolkit

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.core.json.JsonReadFeature
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.LongNode
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.serde.annotation.Serdeable
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.CExoLocStringDeserializer
import neverwintertoolkit.file.gff.CExoLocStringSerializer
import neverwintertoolkit.file.gff.DlgFooDeserializer
import neverwintertoolkit.file.gff.DlgFooSerializer
import neverwintertoolkit.file.gff.DlgReplyDeserializer
import neverwintertoolkit.file.gff.DlgReplySerializer
import neverwintertoolkit.file.gff.DlgStartingDeserializer
import neverwintertoolkit.file.gff.DlgStartingSerializer
import neverwintertoolkit.model.dlg.DlgFoo
import neverwintertoolkit.model.dlg.DlgReply
import neverwintertoolkit.model.dlg.DlgStarting
import java.io.IOException
import kotlin.reflect.KClass

data class JsonSettings(
    val pretty: Boolean = true,
    val simplifyJson: Boolean = globalSettings.simplifyJson,
    val addToExclude: Set<KClass<*>> = setOf(),
    val exclude: Set<KClass<*>> = setOf(),
) {

    fun clone(exclude: Set<KClass<*>> = emptySet()): JsonSettings {
        return JsonSettings(
            pretty = pretty,
            simplifyJson = simplifyJson,
            exclude = exclude,
        )
    }
}

fun getBaseMapper(jsonSettings: JsonSettings = JsonSettings()): ObjectMapper {

    val mapper = ObjectMapper()
        .enable(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES.mappedFeature())
        .enable(JsonReadFeature.ALLOW_TRAILING_COMMA.mappedFeature())
        .enable(JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature())
        .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature())
        .enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature())
        .enable(JsonReadFeature.ALLOW_JAVA_COMMENTS.mappedFeature())
        .enable(JsonReadFeature.ALLOW_LEADING_DECIMAL_POINT_FOR_NUMBERS.mappedFeature())
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // TODO
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
        .enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION)
        .registerModule(KotlinModule.Builder().build())
    //.registerModule(KotlinModule())

    mapper.setConfig(
        mapper.serializationConfig
            .with(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
            .with(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
    )

    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

//    if (!jsonSettings.genBlankStrings) {
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY) // comment out to improve compatability for testing
//    }

    if (jsonSettings.pretty) {
        val pp = DefaultPrettyPrinter()
        pp.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE)
        mapper.setDefaultPrettyPrinter(pp)

        mapper.enable(SerializationFeature.INDENT_OUTPUT)
    }

    val module = SimpleModule()

    val exclude = jsonSettings.exclude

    if (!exclude.contains(DlgReplySerializer::class))
        module.addSerializer(DlgReply::class.java, DlgReplySerializer(jsonSettings))

    if (!exclude.contains(DlgReplyDeserializer::class))
        module.addDeserializer(DlgReply::class.java, DlgReplyDeserializer(jsonSettings))

    if (!exclude.contains(DlgFooSerializer::class))
        module.addSerializer(DlgFoo::class.java, DlgFooSerializer(jsonSettings))

    if (!exclude.contains(DlgFooDeserializer::class))
        module.addDeserializer(DlgFoo::class.java, DlgFooDeserializer(jsonSettings))

    if (!exclude.contains(DlgStartingSerializer::class))
        module.addSerializer(DlgStarting::class.java, DlgStartingSerializer(jsonSettings))
    if (!exclude.contains(DlgStartingDeserializer::class))
        module.addDeserializer(DlgStarting::class.java, DlgStartingDeserializer(jsonSettings))

    if (!exclude.contains(CExoLocStringSerializer::class))
        module.addSerializer(CExoLocString::class.java, CExoLocStringSerializer(jsonSettings))
    if (!exclude.contains(CExoLocStringDeserializer::class))
        module.addDeserializer(CExoLocString::class.java, CExoLocStringDeserializer(jsonSettings))

    mapper.registerModule(module)

    return mapper
}

@Serdeable
@Introspected
@ReflectiveAccess
interface BaseMapper {
    fun getMapper(jsonSettings: JsonSettings = JsonSettings()): ObjectMapper = getBaseMapper()
}

class UShortDeserializer constructor() : StdDeserializer<UShort>(CExoLocString::class.java) {

    constructor(one: Int) : this() {
    }

    @Throws(IOException::class, JacksonException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): UShort {
        val tn: TreeNode = jp.readValueAsTree()
        return (tn as IntNode).intValue().toUShort()
    }
}

class UIntDeserializer : StdDeserializer<UInt>(CExoLocString::class.java) {
    @Throws(IOException::class, JacksonException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): UInt {
        val tn: TreeNode = jp.readValueAsTree()
        return when (tn) {
            is LongNode -> tn.longValue().toUInt()
            is IntNode -> tn.intValue().toUInt()
            else -> throw RuntimeException("unexpected")
        }
    }
}

class UByteSerializer : StdSerializer<UByte>(UByte::class.java) {
    override fun serialize(value: UByte, gen: JsonGenerator, provider: SerializerProvider?) = gen.writeNumber(value.toShort())
}

class UByteDeserializer : StdDeserializer<UByte>(CExoLocString::class.java) {
    @Throws(IOException::class, JacksonException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): UByte {
        val tn: TreeNode = jp.readValueAsTree()
        return (tn as IntNode).intValue().toUByte()
    }
}

class UShortSerializer : StdSerializer<UShort>(UShort::class.java) {
    override fun serialize(value: UShort, gen: JsonGenerator, provider: SerializerProvider?) = gen.writeNumber(value.toInt())
}

class UIntSerializer : StdSerializer<UInt>(UInt::class.java) {
    override fun serialize(value: UInt, gen: JsonGenerator, provider: SerializerProvider?) = gen.writeNumber(value.toLong())
}