package neverwintertoolkit.command

import neverwintertoolkit.file.erf.ErfFile
import neverwintertoolkit.file.key.KeyFile
import neverwintertoolkit.getExtension
import picocli.CommandLine
import java.io.File
import java.nio.file.Files
import kotlin.io.path.pathString

@CommandLine.Command(
    name = "extract",
    aliases = ["x"],
    mixinStandardHelpOptions = true,
    description = ["Extract contents of a file"],
    hidden = false,
    versionProvider = VersionInfo::class
)
class ExtractCommand : BaseCommand() {

    @CommandLine.Parameters(index = "0", description = ["The file to read."], arity = "1..1")
    lateinit var file: File

    @CommandLine.Parameters(index = "1", description = ["pattern"], arity = "0..")
    var patterns: List<String>? = null

    @CommandLine.Option(names = ["--erf"], required = false, description = ["include generation of erf.json"])
    var erf = false

    @CommandLine.Option(names = ["-raw"], description = ["Extract files as raw binary files, do not convert to json"])
    var raw: Boolean = false

    @CommandLine.Option(names = ["-O", "--to-stdout"], required = false, description = ["Extract files to standard output."])
    var toStdout = false

    var useJson = true

    override fun called(): Int {

        useJson = !raw

        when (val extension = getExtension(file.name).lowercase()) {
            ".bif" -> {
                logInfo { "Cannot extract .bif files directly, use .key file" }
            }

            ".key" -> {
                KeyFile(file.toPath(), this).extract(patterns ?: emptyList(), printArchiveName = false, toStdout = toStdout)
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
                    println()
                }

                return erfFile.extractAll(status = status, useJson = useJson, noErf = !erf, patterns = patterns ?: emptyList(), toStdout = toStdout)
            }

            else -> {
                val ext = listOf(".erf", ".hak", ".nwm", ".mod", ".bif", ".key")
                status.println("unsupported file extension '$extension' for $file.  Supported extensions are $ext")
                return 1
            }
        }

        return 0
    }
}