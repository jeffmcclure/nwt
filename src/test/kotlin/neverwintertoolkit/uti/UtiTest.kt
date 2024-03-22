package neverwintertoolkit.uti

import org.junit.jupiter.api.Test
import neverwintertoolkit.con.BaseTest
import java.nio.file.Path
import kotlin.test.Ignore

class UtiTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Ignore
    @Test
    fun door_fancy001() {
        testGffFile(Path.of("src/test/resources/uti/file.uti"), compareAllJson = true)
    }

    @Test
    fun dir() {
        testGffFiles(Path.of("src/test/resources/uti"), ".uti", compareAllJson = true)
    }
}
