package neverwintertoolkit.command.gff

import neverwintertoolkit.command.VersionInfo
import picocli.CommandLine
import java.io.File
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "y",
    mixinStandardHelpOptions = true,
    description = ["Show Struct Names and Types"],
    versionProvider = VersionInfo::class
)
class GffDumpStructNameAndTypesCommand : GffOptions(), Callable<Int> {

    @CommandLine.Parameters(index = "0", description = ["The GFF file to read."], arity = "1..")
    override lateinit var files: List<File>

    override fun call(): Int {

        foo(".txt") { gff, out2 ->
            gff.dumpStructs(out2)
        }

        return 0
    }
}