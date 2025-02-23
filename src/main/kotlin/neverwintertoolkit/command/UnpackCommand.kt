package neverwintertoolkit.command

import io.micronaut.core.annotation.ReflectiveAccess
import neverwintertoolkit.getNwtJsonFile
import picocli.CommandLine
import java.io.File

@CommandLine.Command(
    name = "unpack",
    mixinStandardHelpOptions = true,
    versionProvider = VersionInfo::class,
    description = ["Unpack module"],
    hidden = false
)
@ReflectiveAccess
class UnpackCommand : BaseCommand() {

    @CommandLine.Parameters(index = "0", description = ["Project directory containing nwt.json (default is current working directory)"], arity = "0..1")
    var dir: File? = null

    @CommandLine.Parameters(index = "1", description = ["file to unpack.  Default is source from nwt.json5."], arity = "0..1")
    var file: File? = null

    @CommandLine.Option(names = ["t", "-t"], description = ["Number of threads, default is number of available processors"])
    var threadCount: Int = Runtime.getRuntime().availableProcessors()

    @CommandLine.Option(names = ["-raw"], description = ["Extract files as raw binary files, do not convert to json"])
    var raw: Boolean = false

    @CommandLine.Option(names = ["c", "-c"], description = ["Extract compiled script .ncs files (default is to exclude .ncs files)"])
    var cOption: Boolean = false

    var useJson = true

    override fun called(): Int {
        logger.debug("threadCount={}", threadCount)
        useJson = !raw
        Unpack(getNwtJsonFile(dir), globalOptions = this, status = status, useJson = useJson, threadCount = threadCount).unpackJar(file?.toPath(), unpackNcsFiles = cOption)
        return 0
    }
}
