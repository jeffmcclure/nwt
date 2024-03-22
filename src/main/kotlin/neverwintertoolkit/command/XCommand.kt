package neverwintertoolkit.command

import neverwintertoolkit.file.key.KeyFile
import neverwintertoolkit.getExtension
import picocli.CommandLine
import java.io.File

@CommandLine.Command(
    name = "x",
    mixinStandardHelpOptions = true,
    description = ["Extract files from an archive"],
    hidden = false,
    versionProvider = VersionInfo::class
)
class XCommand : BaseCommand() {

    @CommandLine.Parameters(index = "0", description = ["The file to read."], arity = "1..")
    lateinit var files: List<File>

    @CommandLine.Parameters(index = "1", description = ["pattern"], arity = "0..")
    var patterns: List<String> = emptyList()

    override fun called(): Int {
        files.forEach { file ->
            when (val extension = getExtension(file.name).lowercase()) {
                ".key" -> {
                    KeyFile(file.toPath(), this).extract(patterns, printArchiveName = false)
                }

                ".erf", ".hak" -> {}
                else -> {
                    status.println("unsupported file extension '$extension' for $file")
                }
            }
        }

        return 0
    }
}