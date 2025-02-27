package neverwintertoolkit.file.gff

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.serde.annotation.Serdeable
import neverwintertoolkit.BaseMapper
import neverwintertoolkit.JsonSettings
import neverwintertoolkit.command.GlobalOptions
import java.io.OutputStream
import java.nio.file.Path
import kotlin.io.path.outputStream

@Serdeable
@Introspected
@ReflectiveAccess
interface GffObj : BaseMapper {
    fun toJson(jsonSettings: JsonSettings = JsonSettings()): String {
        return getMapper(jsonSettings).writeValueAsString(this)
    }

    fun toXml(jsonSettings: JsonSettings): String {
        val mapper = XmlMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)

        if (jsonSettings.pretty) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT)
        }

        return mapper.writeValueAsString(this);
    }

    fun writeJson(out: OutputStream, jsonSettings: JsonSettings = JsonSettings()) {
        return getMapper(jsonSettings).writeValue(out, this)
    }

    fun writeJson(file: Path, jsonSettings: JsonSettings = JsonSettings()) {
        file.outputStream().use { out ->
            writeJson(out, jsonSettings)
        }
    }

    fun writeGff(output: OutputStream)
    fun writeGff(file: Path, globalOptions: GlobalOptions? = null) : List<Path> {
        //println("Writing_a $file")
        file.outputStream().use { out ->
            writeGff(out)
        }

        return listOf(file)
    }

    fun removeStructIds() {
    }
}