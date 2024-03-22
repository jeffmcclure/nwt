package neverwintertoolkit.command.gff

import neverwintertoolkit.JsonSettings
import neverwintertoolkit.command.VersionInfo
import neverwintertoolkit.globalSettings
import neverwintertoolkit.model.dlg.Dlg
import picocli.CommandLine
import java.io.File
import java.util.concurrent.Callable

@CommandLine.Command(
    name = "j",
    mixinStandardHelpOptions = true,
    description = ["Generate JSON"],
    versionProvider = VersionInfo::class
)
class GffGenerateJsonCommand : GffOptions(), Callable<Int> {
    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(GffGenerateJsonCommand::class.toString())!!
    }

    @CommandLine.Parameters(index = "0", description = ["The GFF file to read."], arity = "1..")
    override lateinit var files: List<File>

    @CommandLine.Option(
        names = ["s", "-s"],
        required = false,
        description = ["do not generate StructId or RefStructId when producing JSON/XML; instead produce \"Index\".  The main purpose of this is to facilitate unit testing for comparison of files.   This is necessary because the original Aurora produces different ordering/numbering of structs/labels/fields in the .dlg file"]
    )
    var suppressStructIdOutput = false

    @CommandLine.Option(names = ["a", "-a"], required = false, description = ["generate all fields, do not suppress fields holding default values"])
    var aOption = false

    @CommandLine.Option(names = ["i", "-i"], required = false, description = ["generate structId for all records"])
    var iOption = false

    @CommandLine.Option(names = ["z", "-z"], required = false, description = ["Suppress pretty-printing for JSON/XML"])
    override var zOption = false

    override fun call(): Int {

        foo(globalSettings.getJsonExtension()) { gff, out2 ->
            when (gff.gffHeader.fileType) {
                "DLG " -> {
                    val ojb = gff.readObject()
                    val con = Dlg.factory.readGff(gff)
                    if (suppressStructIdOutput)
//                        con.renumberStructs()
                    else {
//                        if (!aOption) con.removeIndexField()
//                        if (!iOption) con.removeUnreferencedStructIds()
                    }

//                    if (jOption)
                    out2.println(con.toJson(JsonSettings(pretty = !zOption)))
//                    else
//                        out2.println(con.toXml(JsonSettings(pretty = !zOption, suppressDefaults = !aOption)))
                }

                else -> {
                    val gff = gff.readObject()
                    if (suppressStructIdOutput)
                        gff.removeStructIds()
//                    if (jOption)
                    out2.println(gff.toJson(JsonSettings(pretty = !zOption)))
//                    else
//                        out2.println(gff.toXml(JsonSettings(pretty = !zOption, suppressDefaults = !aOption)))
                }
            }        }

        return 0
    }
}