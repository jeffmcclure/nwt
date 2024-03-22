package neverwintertoolkit.command

import picocli.CommandLine
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively
import kotlin.io.path.exists

@CommandLine.Command(
    name = "clean",
    aliases = ["c"],
    mixinStandardHelpOptions = true,
    description = ["Clean module"],
    versionProvider = VersionInfo::class,
    hidden = false
)

class CleanCommand : BaseCommand() {

    @CommandLine.Parameters(index = "0", description = ["Project directory containing nwt.json (default is current working directory)"], arity = "0..1")
    var dir: File? = null

    @OptIn(ExperimentalPathApi::class)
    override fun called(): Int {
        val dir: Path = dir?.toPath() ?: Paths.get(System.getProperty("user.dir"))

        val target = dir.resolve("target")
        if (target.exists()) {
            logInfo { "Deleting target..." }
            target.deleteRecursively()
        }
        return 0
    }
}
