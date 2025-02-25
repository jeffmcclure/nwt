package neverwintertoolkit.command.gff

import neverwintertoolkit.command.VersionInfo
import picocli.CommandLine
import java.io.File
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "d",
    mixinStandardHelpOptions = true,
    description = ["Dump"],
    versionProvider = VersionInfo::class
)
class GffDumpCommand : Callable<Int>, GffTextGenerator() {

    @CommandLine.Parameters(index = "0", description = ["The GFF file to read."], arity = "1..")
    override lateinit var files: List<File>

    @CommandLine.Option(names = ["n", "-n"], required = false, description = ["when dumping file do not print fields"])
    override var nOption = false

    @CommandLine.Option(names = ["u", "-u"], description = ["comma separated list of struct nums to dump, e.g. '0,15-9,16,20-33,0,99-end'"])
    override var structList: String? = null

    @CommandLine.Option(names = ["m", "-m"], required = false, description = ["When dumping use alternate format - used for development testing"])
    override var mOption = false

    override fun call(): Int {

        foo(".txt") { gff, out2 ->
            gff.dump(out2)
        }

        return 0
    }
}