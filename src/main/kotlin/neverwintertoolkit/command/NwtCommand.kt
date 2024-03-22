package neverwintertoolkit.command

import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.serde.annotation.SerdeImport
import neverwintertoolkit.command.config.ConfigCommand
import neverwintertoolkit.command.erf.ErfCommand
import neverwintertoolkit.command.gff.GffCommand
import picocli.CommandLine
import picocli.CommandLine.Command
import picocli.CommandLine.IVersionProvider
import java.util.Properties
import kotlin.system.exitProcess

@Command(
    name = "nwt",
    mixinStandardHelpOptions = true,
    description = ["Neverwinter Nights Toolkit version 0.0.3"],
    subcommands = [ErfCommand::class, GffCommand::class, TestCommand::class,
        CleanCommand::class, CompileCommand::class, InstallCommand::class,
        BuildCommand::class, UnpackCommand::class, InitCommand::class, ListCommand::class, ExtractCommand::class,
        FindUnusedFilesCommand::class, ConfigCommand::class, RebuildCommand::class, DiffCommand::class],
    versionProvider = VersionInfo::class,
    subcommandsRepeatable = true
)
//@SerdeImport(com.fasterxml.jackson.databind.PropertyNamingStrategies.NamingBase::class)
//@SerdeImport(com.fasterxml.jackson.databind.PropertyNamingStrategies::class)
//@SerdeImport(com.fasterxml.jackson.databind.PropertyNamingStrategy::class)
@SerdeImport(neverwintertoolkit.UpperCamelCaseStrategy::class)
@SerdeImport(MutableList::class)
class NwtCommand {
    companion object {
        var loggerContext = org.slf4j.LoggerFactory.getILoggerFactory()
        private val logger = loggerContext.getLogger(NwtCommand::class.toString())
    }
}

fun main(args: Array<String>): Unit = exitProcess(CommandLine(NwtCommand()).execute(*args))

@ReflectiveAccess
class VersionInfo : IVersionProvider {
    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(VersionInfo::class.toString())!!
    }

    override fun getVersion(): Array<String> {
        logger.debug("VersionInfo.getVersion()")
        val list = mutableListOf("nwt version 0.0.3")

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
