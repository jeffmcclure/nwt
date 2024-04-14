package neverwintertoolkit.gic

import kotlin.test.Test
import neverwintertoolkit.con.BaseTest
import java.nio.file.Path

class GicTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun dir() {
        testGffFiles(Path.of("src/test/resources/gic"), ".gic", compareAllJson = true)
    }
}
