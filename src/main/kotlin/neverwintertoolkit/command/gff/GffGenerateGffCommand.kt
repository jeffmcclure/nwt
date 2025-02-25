package neverwintertoolkit.command.gff

import neverwintertoolkit.command.VersionInfo
import neverwintertoolkit.file.gff.GffFactory
import picocli.CommandLine
import java.io.File
import java.nio.file.Path
import java.util.concurrent.Callable
import kotlin.io.path.name

@CommandLine.Command(
    name = "g",
    mixinStandardHelpOptions = true,
    description = ["Generate GFF file"],
    versionProvider = VersionInfo::class
)
class GffGenerateGffCommand : GffOptions(), Callable<Int> {
    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(GffGenerateJsonCommand::class.toString())!!
    }

    @CommandLine.Parameters(index = "0", description = ["The GFF file to read."], arity = "1..")
    override lateinit var files: List<File>

    private fun processDlg() {
        files.forEach { file ->
            val path = file.toPath()

            val gffFactory = GffFactory.getFactoryForFileName(path) ?: throw RuntimeException("cannot find factory for $path")
            val fileExtension = gffFactory.extension
            if (logger.isDebugEnabled)
                logger.debug("gffFactory={}, fileExtension={}", gffFactory::class.java.name, fileExtension)

            val outPath = if (path.parent != null)
                path.parent.resolve(path.name + fileExtension)
            else
                Path.of(path.name + fileExtension)

            if (GffFactory.isJsonFile(path)) {
                this.logInfo { "Reading $path" }
                val one = gffFactory.parseJson(path)
                one.writeGff(outPath, this)
            } else if (GffFactory.isXml(path)) {
                this.logInfo { "Reading $path" }
                val xx = gffFactory.parseXml(path)
                xx.writeGff(outPath, this)
            } else throw RuntimeException("unknown file extension for $path")
        }
    }

    override fun call(): Int {
        processDlg()

        return 0
    }

}
