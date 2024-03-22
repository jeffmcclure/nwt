package neverwintertoolkit.command.erf

import neverwintertoolkit.command.VersionInfo
import neverwintertoolkit.file.erf.ErfWriter
import picocli.CommandLine
import java.io.File

@CommandLine.Command(
    name = "c",
    mixinStandardHelpOptions = true,
    description = ["Create ERF Files"],
    versionProvider = VersionInfo::class
)
class ErfCreateCommand : ErfBaseCommand() {

    @CommandLine.Parameters(index = "0", description = ["The ERF file to create."])
    override lateinit var file: File

    @CommandLine.Parameters(index = "1", description = ["directory containing files"])
    lateinit var dir: File

    override fun called(): Int {
        if (file.exists()) throw RuntimeException("$file already exists")

        file.exists() && throw RuntimeException("$file already exists")

        if (!dir.isDirectory) {
            if (dir.exists()) throw RuntimeException("Directory $dir exists and is not a directory")
            throw RuntimeException("Directory $dir does not exist")
        }

        val writer = ErfWriter()

        dir.listFiles()!!.forEach { aFile ->
            if (aFile.isDirectory) {
                println("Skipping directory ${aFile.name}")
            } else {
                writer.addFile(aFile.toPath())
            }
        }

        writer.writeErf(file.toPath())

        return 0
    }
}