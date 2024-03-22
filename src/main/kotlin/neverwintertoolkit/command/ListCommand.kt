package neverwintertoolkit.command

import neverwintertoolkit.file.bif.BifFile
import neverwintertoolkit.file.erf.ErfFile
import neverwintertoolkit.file.key.KeyFile
import neverwintertoolkit.getExtension
import picocli.CommandLine
import java.io.File
import java.nio.file.Files
import kotlin.io.path.pathString

@CommandLine.Command(
    name = "list",
    aliases = ["l"],
    mixinStandardHelpOptions = true,
    description = ["List contents of a file"],
    hidden = false,
    versionProvider = VersionInfo::class
)
class ListCommand : BaseCommand() {

    @CommandLine.Parameters(index = "0", description = ["The file to read."], arity = "1..1")
    lateinit var file: File

    @CommandLine.Parameters(index = "1", description = ["pattern"], arity = "0..")
    var patterns: List<String>? = null

    @CommandLine.Option(names = ["a", "-a"], required = false, description = ["print archive name on each line when listing contents"])
    var aOption = false

    override fun called(): Int {
        when (val extension = getExtension(file.name).lowercase()) {
            ".bif" -> {
                BifFile(file.toPath(), this).list()
            }

            ".key" -> {
                KeyFile(file.toPath(), this).list(patterns ?: emptyList(), printArchiveName = aOption)
            }

            ".erf", ".hak", ".nwm", ".mod" -> {
                val path = file.toPath()
                val erfFile = ErfFile(path, this)

                if (debugEnabled) {
                    println("file path : %s".format(path.pathString))
                    println("file size : %,d".format(Files.size(path)))
                    erfFile.erfHeader.print()
                    erfFile.readStrings().firstOrNull()?.let {
                        println()
                        println(it)
                    }
                }

                if (debugEnabled)
                    println()
                erfFile.listAllEntries(patterns?.map { it.toRegex() }?.firstOrNull(), printArchiveName = aOption)
                return 0

            }

            else -> {
                val ext = listOf(".erf", ".hak", ".nwm", ".mod", ".bif", ".key")
                logInfo { "unsupported file extension '$extension' for $file.  Supported extensions are $ext" }
            }
        }

        return 0
    }
}