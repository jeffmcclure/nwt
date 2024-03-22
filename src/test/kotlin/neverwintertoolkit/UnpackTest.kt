package neverwintertoolkit

import org.junit.jupiter.api.Test
import neverwintertoolkit.command.InitCommand
import neverwintertoolkit.command.Unpack
import neverwintertoolkit.command.UnpackCommand
import neverwintertoolkit.con.BaseTest
import picocli.CommandLine
import java.io.PrintWriter
import java.io.StringWriter
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.absolute
import kotlin.io.path.createDirectories
import kotlin.io.path.name
import kotlin.test.assertEquals

class UnpackTest : BaseTest() {
    val mapper = getBaseMapper()
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun onea() {
        val app = InitCommand()
        var cmd = CommandLine(app)

        val sw = StringWriter()
        cmd.out = PrintWriter(sw)

        val dir = getDir()
        println("dir=$dir")
        val exitCode = cmd.execute(dir.toString(), Paths.get("src/test/resources/mod/module000a.mod").absolute().normalize().toString())
        assertEquals(0, exitCode)

        val unpackCommand = UnpackCommand()
        cmd = CommandLine(unpackCommand)
        val exitValue = cmd.execute(dir.toString())
        logger.info("{}", dir)
        assertEquals(0, exitValue)
    }


    @Test
    fun unpackOne() {
        unpackOne(Paths.get("src/test/resources/nwt/nwt.json5"))
//        unpackOne(Paths.get("src/test/resources/nwt/nwtArray.json"))
    }

    fun unpackOne(file: Path) {
//        val dir = Paths.get("build").resolve(file.name).createDirectories()
        val dir = Files.createTempDirectory(Paths.get("build"), file.name).createDirectories()
        Unpack(file, dir, status=System.out, useJson = true).unpackJar()
    }

    @Test
    fun parseGen() {
        parseGen(Paths.get("src/test/resources/nwt/nwt.json5"))
        parseGen(Paths.get("src/test/resources/nwt/nwtArray.json5"))
    }

    @Test
    fun justParse() {
        Nwt.parseJson(Paths.get("src/test/resources/nwt/nwt.json5"))
        Nwt.parseJson(Paths.get("src/test/resources/nwt/nwtArray.json5"))
    }

    fun parseGen(file: Path) {
        val dir = Paths.get("build").resolve(file.name).createDirectories()

        val targets = Unpack(file, dir, status=System.out, useJson = true).getTargets()
        val json = mapper.writeValueAsString(targets)
        println(json);
    }

    @Test
    fun one() {
        val dir = Paths.get("build/one")
        if (Files.exists(dir)) {
            val newdir = dir.parent.resolve(dir.name + "." + System.currentTimeMillis())
            println("newdir=$newdir")
            Files.move(dir, newdir)
        }

        Files.createDirectories(dir)
        val nwt = Paths.get("src/test/resources/nwt/nwt.json5")

        val unpack = Unpack(nwt, dir, status=System.out, useJson = true)
        unpack.unpackJar()
    }

    @Test
    fun matchTest() {
        val pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*java")
        pathMatcher.matches(Paths.get("Cat.java")).also { println(it) }
        pathMatcher.matches(Paths.get("Animal.java")).also { println(it) }
        pathMatcher.matches(Paths.get("Animal.kt")).also { println(it) }
    }

}
