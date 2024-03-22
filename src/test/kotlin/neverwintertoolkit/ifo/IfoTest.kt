package neverwintertoolkit.ifo

import org.junit.jupiter.api.Test
import neverwintertoolkit.con.BaseTest
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffFile
import neverwintertoolkit.file.gff.GffObj
import neverwintertoolkit.getBaseMapper
import neverwintertoolkit.model.annotation.NwnField
import neverwintertoolkit.model.ifo.Ifo
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

class IfoTest : BaseTest() {
    private val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @Test
    fun one() {
        val one = Ifo()
        one::class.declaredMemberProperties.forEach { member ->
            member.getter.findAnnotation<NwnField>()?.let { two ->
                println("${member.name}: ${two.name}      ${two.type}")
            } ?: run {
                println("${member.name}: NULL")
            }
        }
    }

    @Test
    fun module() {
        val file = Paths.get("src/test/resources/ifo/module-lot.ifo")
        val obj: GffObj = GffFactory.getFactoryForFileName(file)!!.readGff(file)
        val ifoFactory = GffFactory.getFactoryForFileName(file)!!
        val obj2: Ifo = ifoFactory.readGff(file) as Ifo
        logger.debug("{}", obj.toJson())
        logger.debug("{}", obj2.toJson())
    }

    @Test
    fun getObj() {
        val file = Paths.get("src/test/resources/ifo/module-lot.ifo")
//        var ifo: Ifo = GffFile(file).readStructs().first().mapToObject(Ifo::class)
        var ifo: Ifo = GffFile(file).mapToObject(0, Ifo::class)
        println(ifo)
        var mapper = getBaseMapper()
        val json = mapper.writeValueAsString(ifo)
        logger.debug("{}", json)
    }

    @Test
    fun modulex() {
        try {
            testGffFile(Path.of("src/test/resources/ifo/module-lot.ifo"), compareStructId = true)
        } catch (e:Exception) {
            e.printStackTrace()
            throw e
        }
    }

    @Test
    fun dir() {
        testGffFiles(Paths.get("src/test/resources/ifo"), ".ifo", compareStructId = true)
    }

}