package neverwintertoolkit.command

import org.junit.jupiter.api.Test
import neverwintertoolkit.command.gff.GffCommand
import neverwintertoolkit.con.BaseTest
import picocli.CommandLine
import java.io.PrintWriter
import java.io.StringWriter
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.test.assertEquals


class InitCommandTest : BaseTest() {

    @Test
    fun gffCommand() {
        val app = GffCommand()
        val cmd = CommandLine(app)

        val sw = StringWriter()
        cmd.out = PrintWriter(sw)

        val dir = Paths.get("src/test/resources/git/townentrance.git").toFile()

        val exitCode = cmd.execute("j", dir.toString())

        println(sw.toString())
        assertEquals(0, exitCode)
    }

    @Test
    fun two() {
        val app = InitCommand()
        val cmd = CommandLine(app)

        val sw = StringWriter()
        cmd.out = PrintWriter(sw)

// black box testing
//        val exitCode = cmd.execute("-x", "-y=123")
        val dir = getDir()
        Files.delete(dir)
        val exitCode = cmd.execute(dir.toString())
        assertEquals(-1, exitCode, "exit code")
//        assertEquals(0, exitCode)
//        assertEquals("Your output is abc...", sw.toString())

// white box testing
//        assertEquals("expectedValue1", app.state1)
//        assertEquals("expectedValue2", app.state2)
    }

    @Test
    fun three() {
        val app = InitCommand()
        val cmd = CommandLine(app)

        val sw = StringWriter()
        cmd.out = PrintWriter(sw)

        val dir = getDir()
//        Files.delete(dir)
//        Assertions.assertThrows(java.lang.RuntimeException::class.java) {
        val exitCode = cmd.execute(dir.toString())
        assertEquals(-1, exitCode, "exit code")

    }

}