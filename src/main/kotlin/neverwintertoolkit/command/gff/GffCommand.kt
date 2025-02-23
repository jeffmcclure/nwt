package neverwintertoolkit.command.gff

import io.micronaut.core.annotation.ReflectiveAccess
import neverwintertoolkit.command.VersionInfo
import picocli.CommandLine.Command

@Command(
    name = "gff",
    mixinStandardHelpOptions = true,
    versionProvider = VersionInfo::class,
    description = ["Work with GFF files"],
    subcommands = [
        GffDumpCommand::class,
        GffDumpStructNameAndTypesCommand::class,
        GffGenerateGffCommand::class,
        GffGenerateJsonCommand::class,
        GffGenerateRawJsonCommand::class,
        GffGenerateXmlCommand::class,
        GffPojoCreateCommand::class,
    ]
)
@ReflectiveAccess
class GffCommand
