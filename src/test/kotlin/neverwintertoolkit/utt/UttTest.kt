package neverwintertoolkit.utt

import kotlin.test.Test
import neverwintertoolkit.con.BaseTest
import java.nio.file.Path

class UttTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun triggerceptrap_utt() {
        testGffFile(Path.of("src/test/resources/utt/triggerceptrap.utt"), compareAllJson = true)
    }

    @Test
    fun dir() {
        testGffFiles(Path.of("src/test/resources/utt"), ".utt", compareAllJson = true)
    }
}
