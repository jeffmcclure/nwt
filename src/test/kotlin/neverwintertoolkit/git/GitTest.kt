package neverwintertoolkit.git

import kotlin.test.Test
import neverwintertoolkit.JsonSettings
import neverwintertoolkit.con.BaseTest
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffFile
import neverwintertoolkit.file.gff.toRawJson
import neverwintertoolkit.getBaseMapper
import neverwintertoolkit.model.git.Git
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.io.path.name
import kotlin.io.path.writeText
import kotlin.test.Ignore

class GitTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun gitTest() {
        val file = Paths.get("src/test/resources/git/area002.git")
        val xxx = GffFactory.getFactoryForFileName(file)
        val gffFile = GffFile(file)
        val structs = gffFile.structs
        val foo = structs.toRawJson(getBaseMapper(JsonSettings()))
        logger.info("{}", foo)
    }

    @Test
    fun gitDirTest() {
        fff { gffFile ->
            val structs = gffFile.structs
            val foo = structs.toRawJson(getBaseMapper(JsonSettings()))
            logger.info("{}", foo)
        }
    }

    @Test
    fun inthecatacombs() {
        val file = Paths.get("src/test/resources/git/inthecatacombs.git")
        val gffFile = GffFile(file)
        val gffObj = gffFile.readObject() as Git
        logger.info("{}", gffObj.toJson())
    }

    @Test
    fun area014_a() {
        val file = Paths.get("src/test/resources/git/area014.git")
        val gffFile = GffFile(file)
        val gffObj = gffFile.readObject() as Git
        logger.info("{}", gffObj.toJson())
    }

    @Test
    fun townentrance_a() {
        val file = Paths.get("src/test/resources/git/townentrance.git")
        val gffFile = GffFile(file)
        val gffObj = gffFile.readObject() as Git
        logger.info("{}", gffObj.toJson())
    }

    //    val skip = setOf("area014.git")
//    val only = setOf("zhentnpc234.git")
    val only = setOf<String>()
    val skip = setOf<String>()
    val dirx = Paths.get("src/test/resources/git")

    private fun getFiles() =
        Files.list(dirx).filter { only.isEmpty() || only.contains(it.name) }.filter { !skip.contains(it.name) }.filter { it.name.endsWith(".git") }

    fun fff(block: (gffFile: GffFile) -> Unit) {
        getFiles().forEach { file ->
            logger.info("file={}", file)
            val gffFile = GffFile(file)
            block(gffFile)
        }
    }

    @Test
    fun parseDirTest() {
        fff { gffFile ->
            logger.debug("testing gff file {}", gffFile.file)
            val structs = gffFile.structs
            logger.info("{}", structs.toRawJson(getBaseMapper(JsonSettings())))

            val gffObj = gffFile.readObject() as Git
            logger.info("{}", gffObj.toJson())
        }
    }

    @Test
    fun dir() {
        testGffFiles(
            Path.of("src/test/resources/git"), ".git",
            skip = setOf("_stores.git", "area003b.git", "area034b.git", "area020.git")
        )
    }

    @Test
    fun special() {
//        val file = Paths.get("src/test/resources/git/thechamberofbone.git")
//        val file = Paths.get("src/test/resources/git/area003b.git")
        val file = Paths.get("src/test/resources/git/area034.git")
//        val file = Paths.get("src/test/resources/git/a1_trogstrhold3.git")
        val factory = GffFactory.getFactoryForFileName(file.name)!!
        val gff0 = getFile("0" + factory.extension)
        Files.copy(file, gff0, StandardCopyOption.REPLACE_EXISTING)

        val dat = factory.readGff(file)
        val json = dat.toJson()
        val json1 = getFile("1.json")
        json1.writeText(json)
        println(json1)
//        println(json)
        val gff = factory.parseJson(json1)

        val gff2 = getFile("2" + factory.extension)
        val json2 = getFile("2.json")
        gff.writeGff(gff2)
        gff.writeJson(json2)
//        println(json1)
        GffFile(gff2).dump(System.out)
    }

    @Ignore
    @Test
    fun oneoff() {
        val file = Path.of("src/test/resources/1.json")
        val obj = GffFactory.getFactoryForFileName(file)!!.parseJson(file)
        logger.trace("{}", obj)
    }

    @Test
    fun thechamberofbone() {
        testGffFile(Path.of("src/test/resources/git/thechamberofbone.git"), compareStructId = false)
    }

    @Test
    fun townentrance() {
        testGffFile(Path.of("src/test/resources/git/townentrance.git"), compareStructId = false)
    }

    @Test
    fun a1_trogstrhold3() {
        testGffFile(Path.of("src/test/resources/git/a1_trogstrhold3.git"), compareStructId = false)
    }

    @Test
    fun area034() {
        testGffFile(Path.of("src/test/resources/git/area034.git"), compareStructId = false)
    }

    @Test
    fun area034b() {
        testGffFile(Path.of("src/test/resources/git/area034b.git"), compareStructId = false, compareStructCounts = false)
    }

    @Test
    fun area014() {
        testGffFile(Path.of("src/test/resources/git/area014.git"), compareStructId = false)
    }

    @Test
    fun area003b() {
        testGffFile(Path.of("src/test/resources/git/area003b.git"), compareStructId = false, compareStructCounts = false)
    }

    @Test
    fun area003() {
        testGffFile(Path.of("src/test/resources/git/area003.git"), compareStructId = false)
    }

    @Test
    fun zhentnpc234() {
        testGffFile(Path.of("src/test/resources/git/zhentnpc234.git"), compareStructId = false)
    }

    @Test
    fun _stores() {
        testGffFile(Path.of("src/test/resources/git/_stores.git"), compareStructId = false, compareStructCounts = false)
    }

    @Test
    fun ar1501_goblinkit() {
        testGffFile(Path.of("src/test/resources/git/ar1501_goblinkit.git"), compareStructId = false)
    }

    @Test
    fun area020() {
        testGffFile(Path.of("src/test/resources/git/area020.git"), compareStructId = false, compareStructCounts = false)
    }

    @Test
    fun theoutskirts001() {
        testGffFile(Path.of("src/test/resources/git/theoutskirts001.git"), compareStructId = false)
    }
}
