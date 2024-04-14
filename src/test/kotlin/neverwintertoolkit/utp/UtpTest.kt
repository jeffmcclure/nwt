package neverwintertoolkit.utp

import kotlin.test.Test
import neverwintertoolkit.con.BaseTest
import java.nio.file.Path

class UtpTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun npcact_marea_mon_utp() {
        testGffFile(Path.of("src/test/resources/utp/npcact_marea_mon.utp"), compareAllJson = true)
    }

    @Test
    fun dir() {
        testGffFiles(Path.of("src/test/resources/utp"), ".utp", compareAllJson = true)
    }
}
