package neverwintertoolkit.command.erf

import io.micronaut.core.annotation.ReflectiveAccess
import neverwintertoolkit.command.BaseCommand
import neverwintertoolkit.command.VersionInfo
import picocli.CommandLine.Command
import java.io.File

@Command(
    name = "erf",
    mixinStandardHelpOptions = false,
    versionProvider = VersionInfo::class,
    description = ["Work with ERF files"],
    subcommands = [
        ErfCreateCommand::class,
        ErfListCommand::class,
        ErfExtractCommand::class,
        ErfInfoCommand::class
    ]
)
@ReflectiveAccess
open class ErfCommand

abstract class ErfBaseCommand : BaseCommand() {

    open var toStdout = false

    open var aOption = false

    open lateinit var file: File

    open var pattern: String? = null

    open var useJson: Boolean = true
//    override fun called(): Int {
//        System.err.println("ErfBaseCommand.call()")
//        return -1
//    }
}
