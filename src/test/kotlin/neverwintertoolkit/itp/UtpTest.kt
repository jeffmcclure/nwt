package neverwintertoolkit.itp

import org.junit.jupiter.api.Test
import neverwintertoolkit.con.BaseTest
import java.nio.file.Path

class itpTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun itempalcus_itp() {
        testGffFile(Path.of("src/test/resources/itp/itempalcus.itp"), compareAllJson = true)
    }

    @Test
    fun dir() {
        testGffFiles(Path.of("src/test/resources/itp"), ".itp", compareAllJson = true)
    }
}
