package neverwintertoolkit.file.gff

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import neverwintertoolkit.JsonSettings
import neverwintertoolkit.fastIsNullOrBlank
import neverwintertoolkit.getBaseMapper
import neverwintertoolkit.model.dlg.DlgFoo
import neverwintertoolkit.model.dlg.DlgReply
import neverwintertoolkit.model.dlg.DlgStarting
import java.io.IOException
import kotlin.reflect.KClass

class CExoLocStringSerializer(val jsonSettings: JsonSettings) : StdSerializer<CExoLocString>(CExoLocString::class.java) {

    val objectMapper = getBaseMapper(
        jsonSettings.clone(
            exclude = setOf(
                *jsonSettings.exclude.toTypedArray(),
                CExoLocStringSerializer::class,
                CExoLocStringDeserializer::class
            )
        )
    )

    override fun serialize(value: CExoLocString?, gen: JsonGenerator, provider: SerializerProvider?) {
        if (value != null) {
            if (value.stringRef == 4294967295L && value.strings.isEmpty()) {
                gen.writeNull()
            } else if (jsonSettings.simplifyJson && value.stringRef == 4294967295L && value.strings.size == 1 && value.strings[0].id == 0) {
                gen.writeString(value.strings[0].string)
            } else {
                objectMapper.writeValue(gen, value)
            }
        }
    }
}

class CExoLocStringDeserializer(val jsonSettings: JsonSettings) : StdDeserializer<CExoLocString>(CExoLocString::class.java) {

    val objectMapper = getBaseMapper(
        jsonSettings.clone(
            exclude = setOf(
                *jsonSettings.exclude.toTypedArray(),
                CExoLocStringSerializer::class, CExoLocStringDeserializer::class
            )
        )
    )

    override fun getNullValue(ctxt: DeserializationContext?): CExoLocString? {
        return CExoLocString(4294967295L, listOf())
    }

    @Throws(IOException::class, JacksonException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): CExoLocString? {
        val tn: TreeNode = jp.readValueAsTree()

        if (tn.isObject) {
            val str = tn.toString()
            return objectMapper.readValue(str, CExoLocString::class.java)
        } else if (tn is TextNode) {
            val str = tn.textValue()
            return CExoLocString(stringRef = 4294967295L, strings = listOf(GffFile.GffIdString(0, str)))
        }

        return CExoLocString()
    }

}

class DlgFooSerializer(val jsonSettings: JsonSettings) : StdSerializer<DlgFoo>(DlgFoo::class.java) {

    val objectMapper = getBaseMapper(
        jsonSettings.clone(
            exclude = setOf(
                *jsonSettings.exclude.toTypedArray(),
                DlgFooSerializer::class, *(setOf<KClass<*>>().toTypedArray())
            )
        )
    )

    /**
     * DLG files require a not null value for IsChild (use 0 when null).   But we want to allow streamlined JSON, so 0 will always
     * be removed from the JSON, and nulls will always be written as 0 in the GFF.
     */
    override fun serialize(value: DlgFoo?, gen: JsonGenerator, provider: SerializerProvider?) {
        if (value != null) {
            if (jsonSettings.simplifyJson && value.index != null && value.conditionParams.isNullOrEmpty() && value.active.fastIsNullOrBlank()
                && (!value.isChild) && value.linkComment.fastIsNullOrBlank()
            ) {
                gen.writeNumber(value.index!!.toInt())
            } else {
                objectMapper.writeValue(gen, value)
            }
        }
    }
}

class DlgFooDeserializer(val jsonSettings: JsonSettings) : StdDeserializer<DlgFoo>(DlgFoo::class.java) {

    val objectMapper = getBaseMapper(
        jsonSettings.clone(
            exclude = setOf(
                *jsonSettings.exclude.toTypedArray(),
                DlgFooDeserializer::class, DlgFooSerializer::class
            )
        )
    )

    @Throws(IOException::class, JacksonException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): DlgFoo? {
        val tn: TreeNode = jp.readValueAsTree()

        if (tn.isObject) {
            val str = tn.toString()
            val x = objectMapper.readValue(str, DlgFoo::class.java)
            return x
        } else if (tn is IntNode) {
            return DlgFoo().also {
                it.index = tn.intValue().toUInt()
            }
        }

        return DlgFoo()
    }

}

class DlgStartingSerializer(val jsonSettings: JsonSettings) : StdSerializer<DlgStarting>(DlgStarting::class.java) {

    val objectMapper = getBaseMapper(
        jsonSettings.clone(
            exclude = setOf(
                *jsonSettings.exclude.toTypedArray(),
                DlgStartingSerializer::class, DlgStartingDeserializer::class
            )
        )
    )

    override fun serialize(value: DlgStarting?, gen: JsonGenerator, provider: SerializerProvider?) {
        if (value != null) {
            if (jsonSettings.simplifyJson && value.conditionParams.isNullOrEmpty() && value.active.fastIsNullOrBlank()) {
                gen.writeNumber(value.index!!.toInt())
            } else {
                objectMapper.writeValue(gen, value)
            }
        }
    }
}

class DlgStartingDeserializer(val jsonSettings: JsonSettings) : StdDeserializer<DlgStarting>(DlgStarting::class.java) {

    val objectMapper = getBaseMapper(
        jsonSettings.clone(
            exclude = setOf(
                *jsonSettings.exclude.toTypedArray(),
                DlgStartingDeserializer::class, DlgStartingSerializer::class
            )
        )
    )

    @Throws(IOException::class, JacksonException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): DlgStarting? {
        val tn: TreeNode = jp.readValueAsTree()

        if (tn.isObject) {
            val str = tn.toString()
            return objectMapper.readValue(str, DlgStarting::class.java)
        } else if (tn is IntNode) {
            return DlgStarting().also { it.index = tn.intValue().toUInt() }
        } else
            return DlgStarting()
    }

}

class DlgReplySerializer(val jsonSettings: JsonSettings) : StdSerializer<DlgReply>(DlgReply::class.java) {

    val objectMapper = getBaseMapper(
        jsonSettings.clone(
            exclude = setOf(
                *jsonSettings.exclude.toTypedArray(),
                DlgReplyDeserializer::class, DlgReplySerializer::class
            )
        )
    )

    override fun serialize(value: DlgReply?, gen: JsonGenerator, provider: SerializerProvider?) {
        if (value != null) {
            if (jsonSettings.simplifyJson) {
                objectMapper.writeValue(gen, value.clone().removeDefaultValues())
            } else {
                objectMapper.writeValue(gen, value)
            }
        }
    }
}

class DlgReplyDeserializer(val jsonSettings: JsonSettings) : StdDeserializer<DlgReply>(DlgReply::class.java) {

    val objectMapper = getBaseMapper(
        jsonSettings.clone(
            exclude = setOf(
                *jsonSettings.exclude.toTypedArray(),
                DlgReplyDeserializer::class, DlgReplySerializer::class
            )
        )
    )

    @Throws(IOException::class, JacksonException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): DlgReply? {
        val tn: TreeNode = jp.readValueAsTree()

        return if (tn.isObject) {
            val str = tn.toString()
            if (jsonSettings.simplifyJson) {
                objectMapper.readValue(str, DlgReply::class.java).populateDefaultValues()
            } else {
                objectMapper.readValue(str, DlgReply::class.java)
            }
        } else
            DlgReply()
    }

}