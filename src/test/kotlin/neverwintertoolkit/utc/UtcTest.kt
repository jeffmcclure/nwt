package neverwintertoolkit.utc

import kotlin.test.Test
import neverwintertoolkit.con.BaseTest
import java.nio.file.Path

class UtcTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun gnthill001() {
        testGffFile(Path.of("src/test/resources/utc/canislupus.utc"), compareAllJson = true)
    }

    @Test
    fun dir() {
        testGffFiles(Path.of("src/test/resources/utc"), ".utc", compareAllJson = true)
    }
}
