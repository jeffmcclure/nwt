package neverwintertoolkit.con

import org.junit.jupiter.api.Test
import neverwintertoolkit.model.dlg.Dlg
import neverwintertoolkit.JsonSettings
import org.slf4j.event.Level
import java.nio.file.Files
import java.nio.file.Path
import java.util.Random
import java.util.function.Supplier
import kotlin.io.path.writeText
import kotlin.test.Ignore

class Scratch2Test : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    fun two(file: Path) {
        val con = Dlg.factory.readGff(file)

//        println(con.toJson(true))
//        println(con.toXml(true))
//        gff.dump()

        val tmp = Files.createTempFile("", ".json")!!
        tmp.writeText(con.toJson(JsonSettings(pretty = true)))
        Dlg.factory.parseJson(tmp)
        Files.deleteIfExists(tmp)
    }

    @Test
    @Ignore
    fun all() {
        one("/con/con_aaa.dlg.json")
    }

    fun one(name: String) {
        val resource = Scratch2Test::class.java.getResource(name)!!
        val con = Dlg.factory.parseJson(resource)
        println(con.toJson(JsonSettings(pretty = false)))
        val tmp = Files.createTempFile("", "")!!;
        tmp.writeText(con.toJson(JsonSettings(pretty = false)))
//        tmp.bufferedWriter().use { it.write(con.toJson()) }
        Dlg.factory.parseJson(tmp)
        Files.deleteIfExists(tmp)

        Dlg.factory.parseJson(Path.of("src/test/resources$name").toUri().toURL())
    }


    @Test
    fun logTest() {
        println(logger.isInfoEnabled);
        println(logger.isErrorEnabled);
        logger.info("here")
        logger.warn("here")
        logger.error("here")
        logger.atLevel(Level.DEBUG).log("Hello, Debug!")
//        logger.log(Level.DEBUG, "Hello, Debug!")
        logger.trace("Name is {} and age is {}", Supplier<Any> { getName() }, Supplier<Any> { getRandomNumber() })
        logger.info("Name is {} and age is {}", { getName() }, { getRandomNumber() })
    }

    private fun getName(): String {
        return "jeff"
    }

    private fun getRandomNumber(): Long {
        logger.info("getRandomNumber() enter")
        return Random().nextLong()
    }
}