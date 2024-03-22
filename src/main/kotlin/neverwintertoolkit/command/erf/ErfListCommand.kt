package neverwintertoolkit.command.erf

import neverwintertoolkit.command.VersionInfo
import neverwintertoolkit.file.erf.ErfFile
import picocli.CommandLine
import java.io.File
import java.nio.file.Files
import kotlin.io.path.pathString

@CommandLine.Command(
    name = "l",
    mixinStandardHelpOptions = true,
    description = ["List contents of ERF Files"],
    versionProvider = VersionInfo::class
)
class ErfListCommand : ErfBaseCommand() {

    @CommandLine.Parameters(index = "0", description = ["The ERF file to read."])
    override lateinit var file: File

    @CommandLine.Option(names = ["i", "-i"], required = false, description = ["show info and headers for existing file"])
    var i = false

    @CommandLine.Option(names = ["a", "-a"], required = false, description = ["print archive name on each line when listing contents"])
    override var aOption = false

    @CommandLine.Parameters(index = "1", description = ["pattern"], arity = "0..1")
    override var pattern: String? = null

    override fun called(): Int {
        val path = file.toPath()
        val erfFile = ErfFile(path, this)

        if (i) {
            println("file path : %s".format(path.pathString))
            println("file size : %,d".format(Files.size(path)))
            erfFile.erfHeader.print()
            erfFile.readStrings().firstOrNull()?.let {
                println()
                println(it)
            }
        }

        if (i)
            println()
        erfFile.listAllEntries(pattern = pattern?.toRegex(), printArchiveName = aOption)
        return 0
    }
}