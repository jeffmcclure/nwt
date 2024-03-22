package neverwintertoolkit.file.gff

import neverwintertoolkit.JsonSettings
import java.io.InputStream
import java.net.URL
import java.nio.file.Path

abstract class GffFactoryBase<T : GffObj>(val clazz: Class<T>, override val extension: String) : GffFactory<T> {

    override fun parseJson(file: Path): T {
        return getMapper(JsonSettings()).readValue(file.toFile(), clazz)
    }

    override fun parseJson(input: InputStream): T {
        return getMapper(JsonSettings()).readValue(input, clazz)
    }

    override fun parseJson(resource: URL): T {
        return getMapper(JsonSettings()).readValue(resource, clazz)
    }

    override fun parseXml(input: InputStream): T {
        TODO()
    }

}