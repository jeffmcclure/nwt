package neverwintertoolkit.fac

import kotlin.test.Test
import neverwintertoolkit.con.BaseTest
import java.nio.file.Path

class facTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun repute_fac() {
        testGffFile(Path.of("src/test/resources/fac/repute.fac"), compareAllJson = true)
    }

    @Test
    fun dir() {
        testGffFiles(Path.of("src/test/resources/fac"), ".fac", compareAllJson = true)
    }
}
