package neverwintertoolkit.erf

import kotlin.test.Test
import neverwintertoolkit.command.GlobalOptions
import neverwintertoolkit.con.BaseTest
import neverwintertoolkit.file.erf.ErfFile
import neverwintertoolkit.file.erf.ErfWriter
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.file.Path

class ErfWriterTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun one() {
        logger.info("Hello, World!")

        val bos = ByteArrayOutputStream()
        val out = PrintStream(bos)

        val erfWriter = ErfWriter(GlobalOptions(out))

        // add members
        erfWriter.addFile(Path.of("src/test/resources/area/modules/area002.are"))
        erfWriter.addFile(Path.of("src/test/resources/area/modules/area003.are"))

//        erfWriter.setErfJson(Path.of("src/test/resources/erf/erf.json"))

        // output file
        val tmp = getFile("1", ".erf")
        erfWriter.writeErf(tmp)


        bos.reset()

        ErfFile(tmp, outStatus = out).erfHeader.print()
        logger.debug("erfHeader=\n{}", bos)

        bos.reset()
        ErfFile(tmp, outStatus = out).listAllEntries(pattern = null, printArchiveName = true)
        logger.debug("listAllEntries=\n{}", bos)

    }
}