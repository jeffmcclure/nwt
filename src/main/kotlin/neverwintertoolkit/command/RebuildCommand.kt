package neverwintertoolkit.command

import neverwintertoolkit.FileType
import neverwintertoolkit.Nwt
import neverwintertoolkit.file.erf.ErfFile
import neverwintertoolkit.getNwtJsonFile
import neverwintertoolkit.globalSettings
import neverwintertoolkit.model.ifo.Ifo
import picocli.CommandLine
import java.io.File
import java.nio.file.Path
import kotlin.io.path.exists

@CommandLine.Command(
    name = "rebuild",
    mixinStandardHelpOptions = true,
    versionProvider = VersionInfo::class,
    description = ["Rebuild erfPath in nwt.json5 by finding module hak files that contain .nss or .nsc files"],
    hidden = false
)
class RebuildCommand : BaseCommand() {

    @CommandLine.Parameters(index = "0", description = ["Project directory containing nwt.json (default is current working directory)"], arity = "0..1")
    var dir: File? = null

    override fun called(): Int {
        val nwt = getNwtJsonFile(dir)
        rebuildHakPath(nwt, dir?.toPath() ?: nwt.parent, this)
        return 0
    }
}

fun rebuildHakPath(nwtFile: Path, dir: Path = nwtFile.parent, globalOptions: GlobalOptions) {
    val nwtJson: Nwt = Nwt.parseJson(nwtFile).first()
    if (!nwtJson.isModule) return
    // return null to skip
    fun include(str: Path): Path? {
        if (!str.exists()) return null
        val any = ErfFile(str, globalOptions = globalOptions).readAllEntries().any { it.fileType == FileType.kFileTypeNSS || it.fileType == FileType.kFileTypeNCS }
        return if (any) str else null
    }

    val mod = dir.resolve("src").resolve("module.ifo.json5")
    val obj: Ifo = if (mod.exists()) {
        Ifo.factory.parseJson(mod)
    } else {
        val mod = dir.resolve("src").resolve("module.ifo.json")
        if (mod.exists()) {
            Ifo.factory.parseJson(mod)
        } else {
            val mod = dir.resolve("src").resolve("module.ifo")
            if (mod.exists()) {
                Ifo.factory.readGff(mod, globalOptions)
            } else {
                throw RuntimeException("module.ifo not found")
            }
        }
    }
    val hakDir1 = Path.of(globalSettings.nwnHome).resolve("hak")
    val hakDir2 = Path.of(globalSettings.nwnRoot).resolve("data").resolve("hk")
    val foo: List<Path> = obj.modHakList?.mapNotNull { hak ->
        include(hakDir1.resolve("$hak.hak")) ?: include(hakDir2.resolve("$hak.hak"))
    } ?: emptyList()

    val foo2: List<String> = foo.map { globalSettings.substitueVariables(it.toString()) }

    val nwt: List<Nwt> = Nwt.parseJson(nwtFile)
    nwt[0].erfPath = foo2
    Nwt.toJson(nwt, nwtFile)
}
