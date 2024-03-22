package neverwintertoolkit.command

import neverwintertoolkit.Nwt
import neverwintertoolkit.extractExtension
import neverwintertoolkit.globalSettings
import neverwintertoolkit.isDirEmpty
import picocli.CommandLine
import picocli.CommandLine.Parameters
import java.io.File
import java.nio.file.Files
import kotlin.io.path.outputStream

@CommandLine.Command(
    name = "init",
    mixinStandardHelpOptions = true,
    versionProvider = VersionInfo::class,
    description = ["Init module"],
    hidden = false
)
class InitCommand : BaseCommand() {

    @Parameters(index = "0", description = ["Target Files (module, hak, etc.)."])
    var dir: File? = null

    @Parameters(index = "1", description = ["Target Files (module, hak, etc.)."], arity = "0..")
    var targets: List<File> = listOf()

    @CommandLine.Option(names = ["n", "-n"], required = false, description = ["Do not automatically unpack files"])
    var nOption = false

    @CommandLine.Option(names = ["t", "-t"], description = ["Number of threads, default is number of available processors"])
    var threadCount: Int = Runtime.getRuntime().availableProcessors()

    @CommandLine.Option(names = ["-raw"], description = ["Extract files as raw binary files, do not convert to json"])
    var raw: Boolean = false

    @CommandLine.Option(names = ["c", "-c"], description = ["Extract compiled script .ncs files when unpacking (default is to exclude .ncs files)"])
    var cOption: Boolean = false

    var useJson = true

    override fun called(): Int {
        logInfo { "init...${System.getProperty("user.dir")}" }

        val dir = dir?.toPath() ?: run {
            println("Please specify a <dir>")
            return -1
        }

        if (Files.exists(dir)) {
            if (!Files.isDirectory(dir)) throw RuntimeException("$dir exists but is not a directory")
            if (!dir.isDirEmpty()) throw RuntimeException("$dir exists and is not empty")
        } else {
            logInfo { "Creating $dir" }
            Files.createDirectories(dir)
        }

        val nwtFile = dir.resolve("nwt${globalSettings.getJsonExtension()}")
        val x = if (targets.isEmpty()) {
            logInfo { "Please specify a <target>" }
            return -1
        } else if (targets.size > 2) {
            logInfo { "multiple <target> not currently supported" }
            return -1
        } else if (targets.size == 1) {
            val target = targets.first()
            val one = getTemplate()
            val modName = target.name
            val sdir = globalSettings.substitueVariables(target.parent.toString())
            one.source = sdir + File.separator + modName
            val (name, ext) = extractExtension(modName)

            // don't target nwnRoot folder, redirect to nwnHome
            val tdir = if (sdir.contains("\${nwnRoot}")) "\${nwnHome}" + File.separator + "modules" else sdir
            one.target = tdir + File.separator + name.replace(" ", "") + "a" + if (ext.lowercase() == ".nwm") ".mod" else ext

            logInfo { "Creating $nwtFile" }
            Nwt.toJson(listOf(one), nwtFile)
            one
        } else {
            throw RuntimeException("should not get here")
        }


        // needed for haks
        if (x.isHak) {
            val gitConfig = dir.resolve(".git").resolve("config")
            logInfo { "Creating $gitConfig" }
            Files.createDirectories(gitConfig.parent)
            gitConfig.outputStream().use { os ->
                this::class.java.getResourceAsStream("/neverwintertoolkit/git-config-template")!!.use { ins ->
                    ins.copyTo(os)
                }
            }
        }

        val gitignore = dir.resolve(".gitignore")
        logInfo { "Creating $gitignore" }
        gitignore.outputStream().use { out ->
            this::class.java.getResourceAsStream("/neverwintertoolkit/gitignore.txt")!!.use { ins ->
                ins.copyTo(out)
            }
        }

        /*
         * unpack
         */
        logger.debug("threadCount={}", threadCount)
        useJson = !raw
        if (!nOption)
            Unpack(nwtFile, unpackCommand = this, status = status, useJson = useJson, threadCount = threadCount).unpackJar(unpackNcsFiles = cOption)

        return 0
    }

    fun getTemplate(): Nwt {
        return Nwt.parseJson(this::class.java.getResourceAsStream("/neverwintertoolkit/nwt-template.json5")!!).first()
    }
}
