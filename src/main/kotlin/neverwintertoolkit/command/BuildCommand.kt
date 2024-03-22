package neverwintertoolkit.command

import neverwintertoolkit.Build
import neverwintertoolkit.getNwtJsonFile
import picocli.CommandLine

@CommandLine.Command(
    name = "build",
    aliases = ["b"],
    mixinStandardHelpOptions = true,
    description = ["Create target artifact"],
    versionProvider = VersionInfo::class,
    hidden = false
)

class BuildCommand(base: BaseBuildCommand? = null) : BaseBuildCommand() {
    companion object {
        var ran = false
    }

    init {
        base?.copyTo(this)
    }

    @CommandLine.Option(names = ["-t"], description = ["Number of threads, default is number of available processors"])
    var threadCount: Int = Runtime.getRuntime().availableProcessors()

    override fun called(): Int {
        if (CompileCommand.ran == false) {
            CompileCommand(this).call()
        }
        ran = true
        logger.debug("threadCount={}", threadCount)
        logDebug { "threadCount=$threadCount" }
        val file = getNwtJsonFile(dir)
        Build(file, buildCommand = this).pack()
        return 0
    }
}
