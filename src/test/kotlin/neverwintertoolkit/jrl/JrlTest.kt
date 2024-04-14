package neverwintertoolkit.jrl

import kotlin.test.Test
import neverwintertoolkit.con.BaseTest
import java.nio.file.Path

class JrlTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun module() {
        testGffFile(Path.of("src/test/resources/jrl/module.jrl"), compareAllJson = true)
    }

    @Test
    fun dir() {
        testGffFiles(Path.of("src/test/resources/jrl"), ".jrl", compareAllJson = true)
    }
}
