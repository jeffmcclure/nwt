package neverwintertoolkit

import org.junit.jupiter.api.Test
import neverwintertoolkit.con.BaseTest
import neverwintertoolkit.file.gff.GffFile
import neverwintertoolkit.file.gff.toRawJson
import java.nio.file.Paths

class MoreTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun gic() {
        val file = Paths.get("src/test/resources/gic/area004.gic")
        val gffFile = GffFile(file)
        val structs = gffFile.structs
        val foo = structs.toRawJson(getBaseMapper(JsonSettings()))
        logger.info("{}", foo)
    }

    @Test
    fun bic() {
        val file = Paths.get("src/test/resources/player.bic")
        val gffFile = GffFile(file)
        val structs = gffFile.structs
        val foo = structs.toRawJson(getBaseMapper(JsonSettings()))
        logger.info("{}", foo)
    }

    @Test
    fun ifo() {
        val file = Paths.get("src/test/resources/ifo/module.ifo")
        val gffFile = GffFile(file)
        val structs = gffFile.structs
        val foo = structs.toRawJson(getBaseMapper(JsonSettings()))
        logger.info("{}", foo)
    }


}