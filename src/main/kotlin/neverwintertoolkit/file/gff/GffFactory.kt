package neverwintertoolkit.file.gff

import neverwintertoolkit.BaseMapper
import neverwintertoolkit.model.dlg.Dlg
import neverwintertoolkit.model.are.Are
import neverwintertoolkit.model.fac.Fac
import neverwintertoolkit.model.gic.Gic
import neverwintertoolkit.model.git.Git
import neverwintertoolkit.model.ifo.Ifo
import neverwintertoolkit.model.itp.Itp
import neverwintertoolkit.model.jrl.Jrl
import neverwintertoolkit.model.utc.Utc
import neverwintertoolkit.model.utd.Utd
import neverwintertoolkit.model.ute.Ute
import neverwintertoolkit.model.uti.Uti
import neverwintertoolkit.model.utm.Utm
import neverwintertoolkit.model.utp.Utp
import neverwintertoolkit.model.uts.Uts
import neverwintertoolkit.model.utt.Utt
import neverwintertoolkit.model.utw.Utw
import java.io.File
import java.io.InputStream
import java.net.URL
import java.nio.file.Path
import kotlin.io.path.name

/*
   The following file types within a module are all in GFF:
     • Module info file (ifo)
     • Area-related files: area file (are), game object instances and dynamic area properties (git), game instance comments (gic)
      • Object Blueprints: creature (utc), door (utd), encounter (ute), item (uti), placeable (utp), sound (uts), store (utm), trigger (utt), waypoint (utw)
      • Conversation files (dlg)
      • Journal file (jrl)
      • Faction file (fac)
      • Palette file (itp)
      • Plot Wizard files: plot instance/plot manager file (ptm), plot wizard blueprint (ptt) The following files created by the game are also GFF:
      • Character/Creature File (bic)
 */

interface GffFactory<T : GffObj> : BaseMapper {
    fun parseJson(file: Path): T
    fun parseJson(input: InputStream): T
    fun parseJson(resource: URL): T

    fun readGff(gffFile: GffFile): T
    fun readGff(file: Path): T {
        return readGff(GffFile(file))
    }

    fun parseXml(resource: URL): T {
        resource.openStream().use { input ->
            return parseXml(input)
        }
    }

    fun parseXml(input: InputStream): T

    fun parseXml(path: Path): T {
        return parseXml(path.toUri().toURL())
    }

    val extension: String

    companion object {
        private val regex1 = listOf(
            "(?i).*\\.([^.]+)\\.json5?".toRegex(),
            "(?i).*\\.([^.]+)".toRegex()
        )

        val isJsonRegex = "(?i).*\\.json5?".toRegex()
        val isXmlRegex = "(?i).*\\.xml".toRegex()

        fun isXml(file: Path): Boolean {
            return file.name.matches(isXmlRegex)
        }

        fun isJsonFile(file: Path): Boolean {
            return file.name.matches(isJsonRegex)
        }

        fun getFactoryForFileName(file: File): GffFactory<out GffObj>? {
            return getFactoryForFileName(file.name)
        }

        fun getFactoryForFileName(file: Path): GffFactory<out GffObj>? {
            return getFactoryForFileName(file.name)
        }

        fun getFileType(name: String): String? {
            return regex1.firstNotNullOfOrNull { re ->
                re.matchEntire(name)
            }?.let { matchResult ->
                matchResult.groupValues[1].lowercase()
            }
        }

        fun getFactoryForFileName(name: String): GffFactory<out GffObj>? {
            return getFactoryForType(getFileType(name))
        }

        private val allFactories = listOf(
            Dlg.factory,
            Are.factory,
            Gic.factory,
            Git.factory,
            Ifo.factory,
            Utc.factory,
            Utd.factory,
            Ute.factory,
            Uti.factory,
            Utm.factory,
            Utp.factory,
            Uts.factory,
            Utt.factory,
            Utw.factory,
            Jrl.factory,
            Fac.factory,
            Itp.factory,
        )

        private val factoryMap: Map<String, GffFactory<out GffObj>> by lazy {
            allFactories.groupBy { it.extension }.entries.forEach { (extension, factoryList) ->
                if (factoryList.size > 1)
                    throw RuntimeException("Multiple factories for extension '$extension' $factoryList")
            }
            allFactories.associateBy { it.extension.substring(1) }
        }

        fun getFactoryForType(type: String?): GffFactory<out GffObj>? {
            return factoryMap[type?.lowercase()?.trim()]
        }
    }
}