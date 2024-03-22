package neverwintertoolkit.command.config

import neverwintertoolkit.GlobalSettings
import neverwintertoolkit.command.VersionInfo
import neverwintertoolkit.globalSettings
import picocli.CommandLine
import java.nio.file.Files
import java.time.LocalDateTime
import java.util.concurrent.Callable
import kotlin.io.path.name

@CommandLine.Command(
    name = "init",
    mixinStandardHelpOptions = true,
    description = ["initialize config with default values"],
    versionProvider = VersionInfo::class,
    hidden = false,
)
class ConfigInitCommand : Callable<Int> {

    override fun call(): Int {
        val configPath = GlobalSettings.configPath
        if (Files.exists(configPath)) {
            val new = configPath.parent.resolve(configPath.name + "-" + LocalDateTime.now().toString())
            if (Files.exists(new)) {
                System.err.println("Backup already exits $new")
                return 1
            }
            println("Saving existing config file to '$new'")
            Files.copy(configPath, new)

            if (!Files.exists(new)) {
                System.err.println("rename failed")
                return 1
            }
        }

        val dir = GlobalSettings.configPath.parent

        if (!Files.isDirectory(dir)) {
            println("creating config directory $dir")
            Files.createDirectories(dir)
        }

        if (!Files.isDirectory(dir)) {
            System.err.println("create dir failed")
            return 1
        }

        try {
            GlobalSettings.generateDefault().saveToConfigFile()
        } catch (e:Exception) {
            // if create fails generate a blank file so user can configure it
            GlobalSettings().saveToConfigFile()
            System.err.println("Error generating config file, file template created.  You must manually edit ${GlobalSettings.configPath}")
            e.printStackTrace()
            return 1
        }

        println("Created '$configPath'")
        println()

        ConfigCommand().call()

        return 0
    }
}