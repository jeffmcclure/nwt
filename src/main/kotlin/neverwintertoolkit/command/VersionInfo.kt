package neverwintertoolkit.command

import io.micronaut.core.annotation.ReflectiveAccess
import picocli.CommandLine
import java.util.Properties

@ReflectiveAccess
class VersionInfo : CommandLine.IVersionProvider {
    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(VersionInfo::class.toString())!!
    }

    override fun getVersion(): Array<String> {
        logger.debug("VersionInfo.getVersion()")
        val list = mutableListOf("nwt version 0.0.4")

        this.javaClass.getResourceAsStream("/version.properties").use { input ->
            val props = Properties()
            props.load(input)
            props.map { (key, value) ->
                logger.info("{}={}", key, value)
                "$key=$value"
            }.toCollection(list)
        }

        return list.toTypedArray()
    }
}

