package neverwintertoolkit.command.gff

import neverwintertoolkit.JsonSettings
import neverwintertoolkit.command.VersionInfo
import neverwintertoolkit.file.gff.toRawJson
import neverwintertoolkit.getBaseMapper
import picocli.CommandLine
import java.io.File
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "r",
    mixinStandardHelpOptions = true,
    description = ["Generate raw JSON"],
    versionProvider = VersionInfo::class
)
class GffGenerateRawJsonCommand : GffTextGenerator(), Callable<Int> {

    @CommandLine.Parameters(index = "0", description = ["The GFF file to read."], arity = "1..")
    override lateinit var files: List<File>

    @CommandLine.Option(names = ["z", "-z"], required = false, description = ["Suppress pretty-printing for JSON/XML"])
    override var zOption = false

    override fun call(): Int {

        foo(".txt") { gff, out2 ->
            out2.println(gff.structs.toRawJson(getBaseMapper(JsonSettings(pretty = !zOption))))
        }

        return 0
    }
}