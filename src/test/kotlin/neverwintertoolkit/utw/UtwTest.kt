package neverwintertoolkit.utw

import kotlin.test.Test
import neverwintertoolkit.con.BaseTest
import java.nio.file.Path

class UtwTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun waypoint001_utw() {
        testGffFile(Path.of("src/test/resources/utw/waypoint001.utw"), compareAllJson = true)
    }

    @Test
    fun dir() {
        testGffFiles(Path.of("src/test/resources/utw"), ".utw", compareAllJson = true)
    }
}
