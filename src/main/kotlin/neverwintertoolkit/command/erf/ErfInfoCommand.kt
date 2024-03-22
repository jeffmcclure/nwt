package neverwintertoolkit.command.erf

import neverwintertoolkit.command.VersionInfo
import neverwintertoolkit.file.erf.ErfFile
import picocli.CommandLine
import java.io.File
import java.nio.file.Files
import kotlin.io.path.pathString

@CommandLine.Command(
    name = "i",
    mixinStandardHelpOptions = true,
    description = ["Show info for ERF Files"],
    versionProvider = VersionInfo::class
)
class ErfInfoCommand : ErfBaseCommand() {

    @CommandLine.Parameters(index = "0", description = ["The ERF file to read."], arity = "1..")
    lateinit var files: List<File>

    override fun called(): Int {
        files.forEach { file ->
            val path = file.toPath()
            val erfFile = ErfFile(path, this)

            println("file path : %s".format(path.pathString))
            println("file size : %,d".format(Files.size(path)))
            erfFile.erfHeader.print()
            erfFile.readStrings().firstOrNull()?.let {
                println()
                println(it)
            }
        }

        return 0
    }
}