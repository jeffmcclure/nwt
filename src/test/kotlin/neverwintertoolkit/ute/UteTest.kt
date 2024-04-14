package neverwintertoolkit.ute

import kotlin.test.Test
import neverwintertoolkit.con.BaseTest
import java.nio.file.Path
import kotlin.test.Ignore

class UteTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Ignore
    @Test
    fun door_fancy001() {
        testGffFile(Path.of("src/test/resources/ute/door_fancy001.utd"), compareAllJson = true)
    }

    @Test
    fun dir() {
        testGffFiles(Path.of("src/test/resources/ute"), ".ute", compareAllJson = true)
    }
}
