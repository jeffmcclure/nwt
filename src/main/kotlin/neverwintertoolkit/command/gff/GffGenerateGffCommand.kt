package neverwintertoolkit.command.gff

import neverwintertoolkit.command.VersionInfo
import neverwintertoolkit.file.gff.GffFactory
import picocli.CommandLine
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
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

            val outPath = if (fOption) {
                if (path.parent != null)
                    path.parent.resolve(path.name + fileExtension)
                else
                    Path.of(path.name + fileExtension)
            } else null

            globalOptions.status.println("Reading $path")
            if (outPath != null) globalOptions.status.println("Writing $outPath")

            val out = outPath?.let { PrintStream(FileOutputStream(it.toFile())) } ?: System.out!!
            try {
                if (GffFactory.isJsonFile(path)) {
                    val one = gffFactory.parseJson(path)
                    one.writeGff(out)
                } else if (GffFactory.isXml(path)) {
                    gffFactory.parseXml(path).writeGff(out)
                } else throw RuntimeException("unknown file extension for $path")
            } finally {
                if (outPath != null) out.close()
            }
        }
    }

    override fun call(): Int {
        processDlg()

        return 0
    }

}
