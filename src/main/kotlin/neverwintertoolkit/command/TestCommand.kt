package neverwintertoolkit.command

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import neverwintertoolkit.file.con.TestDlgFile
import picocli.CommandLine
import java.io.File

@OptIn(DelicateCoroutinesApi::class)
@CommandLine.Command(
    name = "test",
    mixinStandardHelpOptions = true,
    versionProvider = VersionInfo::class,
    description = ["Run tests"],
    hidden = true
)
class TestCommand : BaseCommand() {

    @CommandLine.Option(names = ["-g"], required = false, description = ["Test GFF file"])
    var g = false

    @CommandLine.Option(names = ["-t"], description = ["Number of threads, default = 3 if -t not specified"])
    var threadCount: Int = 3

    @CommandLine.Parameters(index = "0", description = ["GFF Files"], arity = "1..*")
    var gffFiles: List<File> = listOf()
    override fun called(): Int {

        var count = 0
        if (g) count++

        if (count == 0)
            throw CommandLine.ParameterException(spec.commandLine(), "Error: Specify up to one of: g")

        if (count > 1)
            throw CommandLine.ParameterException(spec.commandLine(), "Error: Specify up to one of: g")

        if (g) {
            processG()
            return 0
        }

        return 1
    }

    private fun processG() {
        if (vOption) {
            println("threadCount=$threadCount")
        }
        val context2 = newFixedThreadPoolContext(threadCount, "Fixed")

        runBlocking(context2) {
            val fnameSize = gffFiles.maxOf { it.name.length }
            val numSize = gffFiles.size.toString().length
            val fmt = "%,${numSize}d of %,${numSize}d %${fnameSize}s %s"
            gffFiles.forEachIndexed { index, file ->
                launch {
                    var result = "pass"

                    val dat = TestDlgFile(file.toPath())
                    try {
                        dat.jsonTest { msg, exception -> result = "fail $msg" }
                    } finally {
                        dat.deleteFiles()
                    }

                    if (result == "pass") {
                        println(fmt.format(index + 1, gffFiles.size, file, result))
                    } else {
                        System.err.println(fmt.format(index + 1, gffFiles.size, file, result))
                    }
                }
            }
        }
    }

}
