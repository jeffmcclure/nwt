package neverwintertoolkit.command.erf

import neverwintertoolkit.command.VersionInfo
import neverwintertoolkit.file.erf.ErfFile
import picocli.CommandLine
import java.io.File

@CommandLine.Command(
    name = "x",
    mixinStandardHelpOptions = true,
    description = ["Extract ERF Files"],
    versionProvider = VersionInfo::class
)
class ErfExtractCommand : ErfBaseCommand() {

    @CommandLine.Parameters(index = "0", description = ["The ERF file to read."])
    override lateinit var file: File

    @CommandLine.Parameters(index = "1", description = ["pattern"], arity = "0..")
    var patterns: List<String>? = null

    @CommandLine.Option(names = ["--erf"], required = false, description = ["include generation of erf.json"])
    var erf = false

    @CommandLine.Option(names = ["-O", "--to-stdout"], required = false, description = ["Extract files to standard output."])
    override var toStdout = false

    @CommandLine.Option(names = ["-raw"], description = ["Extract files as raw binary files, do not convert to json"])
    var raw: Boolean = false

    override fun called(): Int {
        useJson = !raw

        val path = file.toPath()
        val erfFile = ErfFile(path, this)

        return erfFile.extractAll(status = status, useJson = useJson, noErf = !erf, patterns = patterns ?: emptyList(), toStdout = toStdout)
    }
}