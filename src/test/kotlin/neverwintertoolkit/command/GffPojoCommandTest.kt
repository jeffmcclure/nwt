package neverwintertoolkit.command

import kotlin.test.Test
import neverwintertoolkit.con.BaseTest
import picocli.CommandLine
import java.io.PrintWriter
import java.io.StringWriter
import java.nio.file.Paths
import kotlin.test.assertEquals

class GffPojoCommandTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun git() {
//        val file = Paths.get("src/test/resources/misc/module.ifo").toFile()
//        val file = Paths.get("src/test/resources/git/area031.git").toFile()
        test("src/test/resources/git/area034b.git")
    }

    @Test
    fun gic() {
//        val file = Paths.get("src/test/resources/misc/module.ifo").toFile()
//        val file = Paths.get("src/test/resources/git/area031.git").toFile()
//        val file = Paths.get("src/test/resources/git/area034b.git").toFile()
        test("src/test/resources/gic/underlair.gic")
    }


    @Test
    fun are() {
        test("src/test/resources/area/modules/tristram.are")
    }

    @Test
    fun mod() {
        test("src/test/resources/mod/module000a.mod")
    }

    @Test
    fun thechamberofbone_p() {
        test("src/test/resources/git/thechamberofbone.git", "p")
    }

    @Test
    fun thechamberofbone_j() {
        test("src/test/resources/git/thechamberofbone.git", "j")
    }
    @Test
    fun area034b_p() {
        test("src/test/resources/git/area034b.git", "p")
    }
    @Test
    fun area034b_j() {
        test("src/test/resources/git/area034b.git", "j")
    }
    @Test
    fun area034_p() {
        test("src/test/resources/git/area034.git", "p")
    }
    @Test
    fun area034_j() {
        test("src/test/resources/git/area034.git", "j")
    }
    @Test
    fun area034_d() {
        test("src/test/resources/git/area034.git", "d")
    }

    fun test(fileName: String, subCommand:String = "p") {
        val app = NwtCommand()
        val cmd = CommandLine(app)

        val sw = StringWriter()
        cmd.out = PrintWriter(sw)

        val file = Paths.get(fileName).toFile()

//        val exitCode = cmd.execute("gff", "p", file.toString())
//        val exitCode = cmd.execute("gff", "p", file.toString(), file.toString(), file.toString())
        val exitCode = cmd.execute("gff", subCommand, file.toString())
        assertEquals(0, exitCode, "exit code")

//        Paths.get("src/main/kotlin/Tmp.kt").writeText(sw.toString())
        logger.error("{}", sw)
    }

}
