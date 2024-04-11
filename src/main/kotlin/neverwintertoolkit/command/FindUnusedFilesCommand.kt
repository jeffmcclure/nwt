package neverwintertoolkit.command

import neverwintertoolkit.extractFileName
import neverwintertoolkit.getNwtJsonFile
import picocli.CommandLine
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.bufferedReader
import kotlin.io.path.createDirectories
import kotlin.io.path.isRegularFile
import kotlin.io.path.name
import kotlin.io.path.walk

@CommandLine.Command(
    name = "unused",
    mixinStandardHelpOptions = true,
    description = ["Move unused file to nss-unused directory.  Run repeatedly until found unused file count is zero."],
    versionProvider = VersionInfo::class,
    hidden = false
)

class FindUnusedFilesCommand : BaseCommand() {

    @CommandLine.Parameters(index = "0", description = ["Process just one file"], arity = "0..1")
    var files: File? = null

    @OptIn(ExperimentalPathApi::class)
    override fun called(): Int {
        val nwt = getNwtJsonFile() // using as validation to ensure we have a project dir

        val dir = nwt.parent

        fun textIsFound(extractFileName: String): Boolean {
            return dir.resolve("src").walk().asIterable().filter { path: Path ->
                path.isRegularFile() && path.name.lowercase() != "$extractFileName.nss"
            }.any { path ->
                path.bufferedReader().use { toSearch ->
                    val any = toSearch.lines().anyMatch { string: String ->
                        if (string.lowercase().contains(extractFileName)) {
                            logDebug { "$extractFileName $path $string" }
                            true
                        } else {
                            false
                        }
                    }
                    if (any) logDebug { "$extractFileName found in $path" }
                    any
                }
            }
        }

        val targetDir = dir.resolve("nss-unused").also { it.createDirectories() }

        val one: List<Path> = files?.let { listOf(it.toPath()) } ?: run {
            dir.resolve("src").resolve("nss").walk().asIterable().filter {
                it.isRegularFile() && it.name.lowercase().endsWith(".nss")
            }
        }

        var cnt = one.size
        val nssNames = one.filterIndexed { index: Int, nssPath ->
            val referenced = textIsFound("\"" + nssPath.name.extractFileName().lowercase() + "\"")
            logInfo { "used=%-5s %,5d / %,5d %s".format(referenced.toString(), index, cnt, nssPath) }
            if (!referenced) {
                Files.move(nssPath, targetDir.resolve(nssPath.name))
            }

            !referenced
        }


        if (nssNames.isEmpty()) {
            logInfo { "No files are unreferenced" }
        } else {
            logInfo { "Unreferenced files:" }
            nssNames.forEach {
                logInfo { "$it" }
            }
            logInfo { "Unreferenced count:${nssNames.size}" }
        }

        return 0
    }

}
