package neverwintertoolkit.are

import kotlin.test.Test
import neverwintertoolkit.con.BaseTest
import neverwintertoolkit.file.gff.GffFile
import neverwintertoolkit.model.are.Are
import neverwintertoolkit.model.dlg.Dlg
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.name
import kotlin.test.assertTrue

class AreaTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    val base = "src/test/resources/area/modules"

    @Test
    fun one() {
        val file = Paths.get("$base/area002.are")
        val gffFile = GffFile(file)
        gffFile.dump()
    }

    @Test
    fun `area002_are`() {
        val file = Paths.get("$base/area002.are")
        val gffFile = GffFile(file)
        val area = Are.factory.readGff(gffFile)
        logger.info("{}", area.toJson())
    }

    @Test
    fun `2q4_brothel_are`() {
        val file = Paths.get("$base/2q4_brothel.are")
        val gffFile = GffFile(file)
        val area = Are.factory.readGff(gffFile)
        logger.info("{}", area.toJson())
    }

    @Test
    fun simpleRead() {
        val file = Paths.get("$base/area002.are")
        val gffFile = GffFile(file)
        val area = Are.factory.readGff(gffFile)
    }

    @Test
    fun readAre() {
        val file = Paths.get("$base/area002.are")
        val gffFile = GffFile(file)
        val area = Are.factory.readGff(gffFile)
//        logger.info("{}", area)
        logger.info("{}", area.toJson())

        val outFile = getFile(suffix = ".json")
        area.writeJson(outFile)
//        logger.info("{}", outFile)

        Dlg().writeGff(getFile(suffix = ".dlg"))
        Dlg().writeJson(getFile(suffix = ".json"))
    }

    @Test
    fun writeDlg() {
//        val file = Paths.get("src/test/resources/area002.are")
        val file = Paths.get("$base/area003.are")
        val dlg0 = getFile("0.dlg")
        val dlg1 = getFile("1.dlg")

        Files.copy(file, dlg0)

//        GffFile(file).dump()
        logger.info("{}", Are.factory.readGff(GffFile(file)).toJson())

        Are.factory.readGff(GffFile(file)).writeGff(dlg1)
        GffFile(dlg1).dump()
    }

    val skip = setOf("2q4_brothel.are", "tristram.are")

    @Test
    fun pass() {
        Files.list(Path.of("src/test/resources/area/modules")).filter { !skip.contains(it.name) }.forEach {
            logger.debug("{}", it)
            testGffFile(it, compareBinary = false, dmpFileCompare = true, compareStructId = true)
        }
    }

    @Test
    fun a2q4_brothel() {
        val x = Path.of("src/test/resources/area/modules/2q4_brothel.are")
        testGffFile(
            x,
            compareBinary = false,
            dmpFileCompare = true,
            compareStructId = true,
            regex = listOf(
                "\\[DeleteDelta, position: \\d*, lines: \\[ *\"Comments\" : \"\",]]\n\\[InsertDelta, position: \\d*, lines: \\[ *\"SkyBox\" : 0,]]\n".toRegex(),
                "\\[InsertDelta, position: \\d*, lines: \\[ *\"SkyBox\" : 0,]]\n".toRegex()
            )
        )
    }

    @Test
    fun oneArea() {
        val x = Path.of("src/test/resources/area/modules/tristram.are")
        testGffFile(
            x,
            compareBinary = false,
            dmpFileCompare = true,
            compareStructId = true,
            regex = listOf("\\[DeleteDelta, position: \\d*, lines: \\[ *\"Comments\" : \"\",]]\n".toRegex())
        )
    }

    @Test
    fun foaod() {
        var failed = mutableListOf<Path>()
        Files.list(Path.of("src/test/resources/area/modules")).filter { !skip.contains(it.name) }.forEach { file ->
            logger.info("{}", file)
            try {
                testGffFile(file, compareBinary = false, dmpFileCompare = true, compareStructId = true)
            } catch (e: Exception) {
                failed.add(file)
            }
        }
        failed.forEach {
            logger.error("failed={}", it)
        }
        assertTrue(failed.isEmpty())
    }

    @Test
    fun simple() {
        val file = Paths.get("$base/2q4_brothel.are")
        val dlg0 = getFile("0.dlg")
        val dlg1 = getFile("1.dlg")
        Files.copy(file, dlg0)

        val factory = Are.factory
        val gffFile = factory.readGff(GffFile(file))
        gffFile.writeGff(dlg1)

        logger.info("{}", GffFile(dlg1).readObject().toJson())
        logger.info("{}", println(GffFile(dlg1).toRawJson()))
    }

}