package neverwintertoolkit.key

import org.junit.jupiter.api.Test
import neverwintertoolkit.command.NwtCommand
import neverwintertoolkit.con.BaseTest
import neverwintertoolkit.globalSettings
import picocli.CommandLine
import java.io.PrintWriter
import java.io.StringWriter
import java.nio.file.Paths
import kotlin.test.assertEquals

class KeyFileTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!


    @Test
    fun one1() {
        test(
            "l", Paths.get(globalSettings.nwnRoot)
                .resolve("data")
                .resolve("base_scripts.bif").toString()
        )
    }

    @Test
    fun one2() {
        val key = Paths.get(globalSettings.nwnRoot)
            .resolve("data")
            .resolve("nwn_base.key").toString()
//        test("l", "-v", key, "data/sounds.bif")
//        test("l", "-v", key, "data/base_scripts.bif/inc_mf_combat.nss")
        test("x", key, "data/base_scripts.bif/inc_mf_combat.nss")
//        test("l", "-v", key, "data/base_scripts.bif")
    }

    fun test(vararg args: String) {
        val app = NwtCommand()
        val cmd = CommandLine(app)

        val sw = StringWriter()
        cmd.out = PrintWriter(sw)

//        val file = Paths.get("src/test/resources/key/nwn_base_loc.key").toFile()

//        val exitCode = cmd.execute("l", file.toString(), "data/pl_xp1_sounds.bif")
        val exitCode = cmd.execute(*args)
        assertEquals(0, exitCode, "exit code")

        logger.error("{}", sw)
    }
}