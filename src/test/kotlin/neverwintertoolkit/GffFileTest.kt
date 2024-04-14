package neverwintertoolkit

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.ObjectNode
import kotlin.test.Test
import neverwintertoolkit.command.gff.GffCommand
import neverwintertoolkit.command.gff.GffOptions
import neverwintertoolkit.con.BaseTest
import neverwintertoolkit.file.gff.GffFile
import neverwintertoolkit.file.gff.toProgression
import neverwintertoolkit.file.gff.toRawJson
import neverwintertoolkit.file.gff.toRawJsonNode
import neverwintertoolkit.model.are.Are
import neverwintertoolkit.model.are.AreTile
import neverwintertoolkit.model.git.Git
import picocli.CommandLine
import java.io.PrintWriter
import java.io.StringWriter
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.test.DefaultAsserter
import kotlin.test.assertEquals

class GffFileTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(GffFileTest::class.java)!!

    @Test()
    fun some() {
        try {
            GffFile.GffFieldType.valueOf("Byte")
        } catch (e: Exception) {
            return
        }
        throw Exception("should have failed")
    }

    @Test
    fun toProgression() {
        assertEquals("1..3", "1-3".toProgression(9).toString())
        assertEquals("3..3", "3".toProgression(9).toString())
        assertEquals("3..9", "3-99".toProgression(9).toString())
    }

    fun f2(namex: String): Path {
        val name = "src/test/resources$namex"
        val file = Path.of(name)
        DefaultAsserter.assertTrue("$name does not exist", file.exists())
        return file
    }

    @Test
    fun one() {
        val path = f2("/con/reference/con_ddd.dlg")
        val one = GffFile(path, GffOptions().apply { vOption = true; oOption = true })
        one.dump()
    }

    @Test
    fun two() {
        val path = f2("/con/reference/con_ccc.dlg")
        val erf = GffFile(path)

        println(erf.gffHeader)

        println()
        println()
        erf.printAllLabels()

        println()
        println()
        erf.printAllFields()

        println()
        println()
        erf.printAllFieldIndices()
    }


    @Test
    fun raw() {
//        val path = f("/con/reference/con_ccc.dlg")
        val path = f2("/area/modules/area002.are")

        val json = GffFile(path).structs.toRawJson().also {
            logger.debug("{}", it)
        }

        val structs = GffFile(path).structs
        val nodeA = structs.first().toRawJsonNode()
        val nodeB = structs.drop(1).toRawJsonNode()

        val mapper = getBaseMapper()
        nodeA.expandListAll(mapper, structs)

//        nodeA.remove("StructId")
        val arrArea = mapper.treeToValue(nodeA, Are::class.java)
        val arrTile = mapper.treeToValue(nodeB, Array<AreTile>::class.java)
        println(mapper.writeValueAsString(arrArea))
        println(mapper.writeValueAsString(arrTile))
    }

    @Test
    fun rawCommand() {
        val app = GffCommand()
        val cmd = CommandLine(app)

        val sw = StringWriter()
        cmd.out = PrintWriter(sw)

        val file = f2("/area/modules/area002.are")
        val exitCode = cmd.execute("r", file.toString())
        assertEquals(0, exitCode, "exit code")
    }

    fun runCommand(block: (cmd: CommandLine, file: Path) -> Int) {
        val app = GffCommand()
        val cmd = CommandLine(app)

        val sw = StringWriter()
        cmd.out = PrintWriter(sw)

        val file = f2("/area002.are")
        val exitCode = block(cmd, file)
        assertEquals(0, exitCode, "exit code")
    }

    fun runCommand2(vararg args: Any) {
        val app = GffCommand()
        val cmd = CommandLine(app)

        val sw = StringWriter()
        cmd.out = PrintWriter(sw)

        val exitCode = cmd.execute(*(args.map(Any::toString).toTypedArray()))
        assertEquals(0, exitCode, "exit code")
    }

    @Test
    fun onex3() {
//        val list = listOf("/con/reference/con_ddd.dlg", "/area002.are")
        val list = listOf("/con/reference/con_ddd.dlg")
        list.forEach { str ->
            val file = f2(str)
            listOf("x", "r", "j").forEach { cmd ->
                runCommand2(cmd, file)
            }
        }
    }

    @Test
    fun asdfasdf() {
        runCommand2("d", f2("/area/modules/2q4_brothel.are"))
    }

    @Test
    fun strudty() {
        val f2 = f2("/git/area020.git")
        GffFile(f2).dumpStructs(System.out)
    }


    fun readGff(gffFile: GffFile): Git {
        logger.info("readGff {}", gffFile.file)
        val mapper = getBaseMapper()

        val structs = gffFile.structs
        val rootNode = structs.first().toRawJsonNode(mapper)

        (rootNode.get("AreaProperties") as ObjectNode?)?.let { areaProperties ->
            (areaProperties.get("StructId") as IntNode?)?.let { structIdNode ->
                val structId = structIdNode.intValue()
                val areaPropDeref = structs[structId].toRawJsonNode(mapper)
                rootNode.replace("AreaProperties", areaPropDeref)
            }
        }

        rootNode.expandListAll(mapper, structs)

        if (logger.isDebugEnabled) {
            rootNode.forEach {
                logger.debug("{}", it)
            }
        }

        return mapper.treeToValue(rootNode, Git::class.java)
    }

    fun ObjectNode.replaceWithArrayNode(nodeName: String, list: List<JsonNode>, mapper: ObjectMapper) {
        val outArray = mapper.createArrayNode()
        list.forEach {
            outArray.add(it)
        }
        this.set<JsonNode>(nodeName, outArray)
    }

    fun ObjectNode.expandListAll(mapper: ObjectMapper, structs: List<GffFile.GffStruct>) {
        this.properties().forEach { (name, node) ->
            if (node is ArrayNode) {
                val list = node.map { structs[it.intValue()].toRawJsonNode(mapper) }
                list.forEach { it.expandListAll(mapper, structs) }
                this.replaceWithArrayNode(name, list, mapper)
            }
        }
    }


}
