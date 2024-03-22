package neverwintertoolkit.utm

import org.junit.jupiter.api.Test
import neverwintertoolkit.con.BaseTest
import java.nio.file.Path

class UtmTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun ogdenstore001_utm() {
        testGffFile(Path.of("src/test/resources/utm/ogdenstore001.utm"), compareAllJson = true)
    }

    @Test
    fun dir() {
        testGffFiles(Path.of("src/test/resources/utm"), ".utm", compareAllJson = true)
    }
}
