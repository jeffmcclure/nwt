package neverwintertoolkit.command

import neverwintertoolkit.getNwtJsonFile
import picocli.CommandLine

@CommandLine.Command(
    name = "install",
    aliases = ["i"],
    mixinStandardHelpOptions = true,
    versionProvider = VersionInfo::class,
    description = ["Install generated artifacts"],
    hidden = false
)

class InstallCommand(base: BaseBuildCommand? = null) : BaseBuildCommand() {

    init {
        base?.copyTo(this)
    }

    @CommandLine.Option(names = ["--overwrite"], description = ["overwrite target file"])
    var overwriteOption = false

    override fun called(): Int {
        val file = getNwtJsonFile(dir)

        if (BuildCommand.ran == false) {
            BuildCommand(this).call()
        }
        Install(file, installCommand = this).install()
        return 0
    }
}
