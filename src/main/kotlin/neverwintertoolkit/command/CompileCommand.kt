package neverwintertoolkit.command

import neverwintertoolkit.Nwt
import neverwintertoolkit.getNwtJsonFile
import neverwintertoolkit.globalSettings
import picocli.CommandLine
import java.io.File
import java.lang.Long.max
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.FileTime
import java.util.concurrent.TimeUnit
import kotlin.io.path.createDirectories
import kotlin.io.path.getLastModifiedTime
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.io.path.setLastModifiedTime
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "compile",
    aliases = ["o"],
    mixinStandardHelpOptions = true,
    versionProvider = VersionInfo::class,
    description = ["Compile .nss files"],
    hidden = false
)

class CompileCommand(base: BaseBuildCommand? = null) : BaseBuildCommand() {

    companion object {
        var ran = false
    }

    init {
        base?.copyTo(this)
    }

    override fun called(): Int {
        ran = true

        val ncsTarget = Paths.get("target/ncs")
        if (!Files.isDirectory(ncsTarget)) {
            ncsTarget.createDirectories()
            Files.setLastModifiedTime(ncsTarget, FileTime.from(0, TimeUnit.MILLISECONDS))
        }

        if (iOption && fOption) {
            logError { "Cannot use both -i and -f" }
            System.exit(1)
        }

        val nssSource = Paths.get("src/nss")

        if (fOption) {
            compileAll(nssSource, ncsTarget)
        } else if (!iOption) {
            if (!compileNeeded(nssSource, ncsTarget)) return 0
            compileAll(nssSource, ncsTarget)
        } else {
            // above commented incremental compile does not automatically work because "include" .nss files are inlined, and do not produce a binary .ncs file
            val targetLastModifed = ncsTarget.dirLastModified()
            val list = nssSource.listDirectoryEntries().filter { it.getLastModifiedTime().toMillis() > targetLastModifed }
            compileList(list, ncsTarget)
        }

        return 0
    }

    fun compileAll(nssSource: Path, ncsTarget: Path) {
        val cmd = mutableListOf<String>()
        val compiler: Path = Paths.get(globalSettings.scriptCompilerBinary)

        cmd.add(compiler.toString())
        val pb = when (compiler.name) {
            "nwn_script_comp" -> {
                // nwn_script_comp --verbose -d target -c src/nss
                cmd.add("--root")
                cmd.add(globalSettings.nwnRoot)
//                --root ROOT                 Override NWN root (autodetection is attempted)
                cmd.add("--userdirectory")
                cmd.add(globalSettings.nwnHome)
//                --userdirectory USERDIR

                if (vOption) cmd.add("--verbose")
                if (yOption) cmd.add("-y")
                val nwt = Nwt.parseJson(getNwtJsonFile(dir)).first()
                if (nwt.erfPath.isNotEmpty()) {
                    cmd.add("--erfs")
                    val list = nwt.erfPath.map { globalSettings.expandVariables(it) }
                    val str = list.joinToString(",")
                    cmd.add(str)
                }
                cmd.addAll(listOf("-d", ncsTarget.toString(), "-c", nssSource.toString()))
                ProcessBuilder(cmd)

            }

            "nwnsc" -> {
                cmd.add("-co")
                if (vOption) cmd.add("-j")
                if (yOption) cmd.add("-y")
                else cmd.add("-w")
                val nwt = Nwt.parseJson(getNwtJsonFile(dir)).first()
                if (nwt.erfPath.isNotEmpty()) {
                    throw RuntimeException("nwnsc does not support including .nss/.nsc files from .hak files.  Meaning nwt.json5 erfPath must be empty or update nwn-config.json5 to use nwn_script_comp.")
                }

//                if (nwt.erfPath.isNotEmpty()) {
//                    cmd.add("-i")
//                    val list = nwt.erfPath.map { globalSettings.expandVariables(it) }
//                    val str = list.joinToString(";")
//                    cmd.add(str)
//                }

                cmd.addAll(listOf("-n", globalSettings.nwnRoot, "-b", "../../$ncsTarget", "*nss"))
                ProcessBuilder(cmd).directory(File(nssSource.toString()))
            }

            else -> throw RuntimeException("unexpected .nss script compiler ${compiler.name}")

        }
        logInfo { cmd.toString() }


        logInfoNo { "Compiling all .nss files..." }
        val exitCode = runCommand(pb)
        if (!yOption && exitCode != 0) {
            nssSource.setLastModifiedTime(FileTime.fromMillis(System.currentTimeMillis()))
            exitProcess(exitCode)
        }
    }

    fun compileList(list: List<Path>, ncsTarget: Path) {
        val size = list.size

        list.forEachIndexed { index, path ->
            logInfo { "Compiling %4d of %4d : %s".format(index, size, path.toString()) }

            val cmd = mutableListOf<String>()
            val compiler = Paths.get(globalSettings.scriptCompilerBinary)

            val pb = when (compiler.name) {
                "nwn_script_comp" -> {
                    // nwn_script_comp --verbose -d target -c src/nss
                    cmd.add("nwn_script_comp")
                    if (vOption) cmd.add("--verbose")
//                    cmd.addAll(listOf("-d", ncsTarget.toString(), "-c", nssSource.toString()))
                    cmd.addAll(listOf("-d", ncsTarget.toString(), "-c", path.toString()))
                    ProcessBuilder(cmd)

                }

                "nwnsc" -> {
                    cmd.add("nwnsc")
                    cmd.add("-co")
                    if (vOption) cmd.add("-j")
                    else cmd.add("-w")
                    cmd.addAll(listOf("-n", globalSettings.nwnRoot, "-b", "../../$ncsTarget", path.name))
                    ProcessBuilder(cmd).directory(path.parent.toFile())
                }

                else -> throw RuntimeException("unexpected .nss script compiler ${compiler.name}")

            }
            logDebug { cmd.toString() }


            logInfoNo { "Compiling changed .nss files..." }
            val exitCode = runCommand(pb)
            if (!yOption && exitCode != 0) {
                path.setLastModifiedTime(FileTime.fromMillis(System.currentTimeMillis()))
                exitProcess(exitCode)
            }
        }
    }

    private fun runCommand(pb: ProcessBuilder): Int {
        val proc = pb.start()
        proc.inputReader().use { reader ->
            proc.errorReader().use { errorReader ->
                fun procOutput(): Boolean {
                    var any = false
                    if (reader.ready()) {
                        reader.readLine()?.let { any = true; logInfo { it } }
                    }

                    if (errorReader.ready()) {
                        errorReader.readLine()?.let { any = true; logInfo { "Error: $it" } }
                    }

                    if (any)
                        status.flush()
                    return any
                }

                while (true) {
                    while (procOutput()) {
                    }
                    if (!proc.isAlive) {
                        //info { "!isAlive" }
                        Thread.sleep(200)
                        procOutput() // one last check for new output
                        break
                    } else {
                        //info { "sleep" }
                        Thread.sleep(200)
                    }
                }
            }
        }
        val retCode = proc.waitFor()
        logInfo { "Done.  Compile return code = $retCode" }
        return retCode
    }

    private fun compileNeeded(nssSource: Path, ncsTarget: Path): Boolean {
        if (!Files.isDirectory(nssSource) || !Files.isDirectory(ncsTarget)) return false
        return nssSource.dirLastModified() > ncsTarget.dirLastModified()
    }

    fun Path.dirLastModified(): Long {
        return max(this.getLastModifiedTime().toMillis(), this.listDirectoryEntries().maxOfOrNull { it.getLastModifiedTime().toMillis() } ?: 0L)
    }

}
