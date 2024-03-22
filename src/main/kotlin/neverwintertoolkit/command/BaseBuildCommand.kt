package neverwintertoolkit.command

import picocli.CommandLine
import java.io.File

abstract class BaseBuildCommand : BaseCommand() {
    @CommandLine.Parameters(index = "0", description = ["Project directory containing nwt.json (default is current working directory)"], arity = "0..1")
    var dir: File? = null

    @CommandLine.Option(names = ["-i"], required = false, description = ["Incremental compile of only changed .nss files (default = false)"])
    var iOption = false

    @CommandLine.Option(names = ["-f"], required = false, description = ["Force compile of all .nss files"])
    var fOption = false

    @CommandLine.Option(names = ["-y"], required = false, description = ["Continue compiling .nss files even on error"])
    var yOption = false

    fun copyTo(target: BaseBuildCommand) {
        super.copyTo(target)
        target.iOption = iOption
        target.fOption = fOption
        target.yOption = yOption
        target.dir = dir
    }
}