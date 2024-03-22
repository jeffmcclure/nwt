package neverwintertoolkit.command.config

import neverwintertoolkit.GlobalSettings
import neverwintertoolkit.command.VersionInfo
import neverwintertoolkit.globalSettings
import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "config",
    mixinStandardHelpOptions = true,
    description = ["view and manage global configuration"],
    versionProvider = VersionInfo::class,
    hidden = false,
    subcommands = [
        ConfigInitCommand::class,
    ]
)
class ConfigCommand : Callable<Int> {

    override fun call(): Int {
        val configPath = GlobalSettings.configPath
        println("config file = '$configPath'")
        globalSettings.dump(System.out)
        println()
//        System.out.flush()
//        configPath.readLines().forEach {
//            println(it)
//        }
        return 0
    }

}
