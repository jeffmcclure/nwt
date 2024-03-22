package neverwintertoolkit.utd

import org.junit.jupiter.api.Test
import neverwintertoolkit.con.BaseTest
import java.nio.file.Path

class UtdTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun door_fancy001() {
        testGffFile(Path.of("src/test/resources/utd/door_fancy001.utd"), compareAllJson = true)
    }

    @Test
    fun dir() {
        testGffFiles(Path.of("src/test/resources/utd"), ".utd", compareAllJson = true)
    }
}
