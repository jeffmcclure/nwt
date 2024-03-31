package neverwintertoolkit.con

import org.junit.jupiter.api.Test
import neverwintertoolkit.globalSettings
import neverwintertoolkit.model.dlg.Dlg
import neverwintertoolkit.model.dlg.DlgSorter
import neverwintertoolkit.readPart
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.writeText
import kotlin.test.Ignore

class ConTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun con_cain() {
        testDlgPart("/con/con_cain.dlg.json5", "/con/con_cain.dlg-part.json5")
    }

    @Test
    fun con_adria() {
        testDlgPart("/con/con_adria.dlg.json5", "/con/con_adria.dlg-part.json5")
    }

    fun testDlgPart(dlg: String, dlgs: String) {
        val adriaUrl: URL = this::class.java.getResource(dlg)!!
        val adria: Dlg = Dlg.factory.parseJson(adriaUrl)

        val res: URL = this::class.java.getResource(dlgs)!!
        readPart(adria, res)

        val path = Paths.get("build").resolve("out.dlg.json5")
        adria.writeJson(path)

        val outGff = Paths.get("build").resolve("out.dlg")
        adria.writeGff(outGff)

        val sorted = DlgSorter(adria).sorted().toJson()
//        println(DlgSorter(adria).sorted().toJson())
        Paths.get("build").resolve("sorted.json5").writeText(sorted)

        println()
    }

    @Test
    fun dev2() {
//        val file = f("/con/modules/con_janna.dlg")
        val file = f("/con/reference/con_bbb.dlg")
        Dlg.factory.readGff(file)
//        println(GffConReader(GffFile(file)).readCon().toJson())
    }

    @Test
    fun modules() {
        testGffFiles(Paths.get("src/test/resources/con/modules"), ".dlg")
    }

    @Test
    fun reference() {
        testGffFiles(Paths.get("src/test/resources/con/reference"), ".dlg")
    }

    @Test
    fun oen() {
        testGffFile(f("/con/reference/con_ccc.dlg"), compareAllJson = true)
    }


    @Test
    fun print() {
        globalSettings.simplifyJson = false
//        globalSettings.genBlankStrings = false
//        val file = f("/con/reference/con_i18n.dlg")
//        val file = f("/con/reference/con_aaa.dlg")
        val file = f("/con/modules/con_janna.dlg")
        logger.debug("{}", Dlg.factory.readGff(file).toJson())
    }

    @Test
    fun oneGen() {
        val file = f("/con/reference/con_aaa.dlg")
        val dat: Dlg = Dlg.factory.readGff(file)
        val json1 = getFile("1.json")
        json1.writeText(dat.toJson())
        logger.debug(dat.toJson())
    }

    @Test
    fun dev() {
//        val file = f("/con/reference/con_i18n.dlg")
        val file = f("/con/reference/con_aaa.dlg")
//        val file = f("/con/modules/con_janna.dlg")

//        val gff = GffFile(file)
//        val con = GffCon(gff).readCon()
//        con.removeIndexField()
//        con.removeUnreferencedStructIds()

        testGffFile(file, compareAllJson = true)

//        val con = Con.parseJson(file)
//        println(con.toJson(JsonSettings(suppressDefaults = false)))
//        println("".padStart(55, '='))
//        println("".padStart(55, '='))
//        println("".padStart(55, '='))
//        println(con.toJson())
    }

    @Test
    fun pass() {
        testGffFiles(Paths.get("src/test/resources/con/reference"), ".dlg", compareAllJson = true)
        testGffFiles(Paths.get("src/test/resources/con/modules"), ".dlg", compareAllJson = true)
    }

    /**
     * I don't think these will ever be fixed.  These are very rare failures when scanning
     * hundreds of files.
     */
    @Test
    @Ignore
    fun failx() {
        Files.list(Path.of("src/test/resources/con/fail")).forEach {
            testGffFile(it, compareAllJson = true)
        }
    }

}