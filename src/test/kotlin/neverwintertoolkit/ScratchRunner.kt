package neverwintertoolkit

import com.fasterxml.jackson.databind.ObjectMapper
import neverwintertoolkit.con.BaseTest
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.model.dlg.Dlg
import neverwintertoolkit.model.dlg.DlgSorter
import java.net.URL
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Arrays
import kotlin.io.path.getLastModifiedTime
import kotlin.io.path.writeText
import kotlin.test.DefaultAsserter.assertTrue
import kotlin.test.Ignore
import kotlin.test.Test

@Ignore
class ScratchRunner : BaseTest() {

    var yes = true

    @Test
    fun dir() {
        val x = listOf("one two", "three four").joinToString("\":\"")
        println(x)
//        val dir = Paths.get(System.getProperty("user.home"))
//        dir.listDirectoryEntries().filter { it.isDirectory() }.forEach {
//            println(it)
//        }
    }

    @Test
    fun nums() {
        var str = "1.5707942"
        str = "24.907188"
        val float = str.toFloat()
        val doub = str.toDouble()
        println("$str = $float = $doub")
    }

    @Test
    fun shift() {
        var str = Path.of("data/models_01.bif/vwp_s_blood_red.mdl")

        val rePatterns = FileSystems.getDefault().getPathMatcher("glob:*mdl")

        println(rePatterns.matches(str.fileName))

        val x = 3
        val y = 3 shl 20
        val z = 3 shr 20

        System.out.printf("%d %d %d\n", x, y, z)
    }

    @Test
    fun twows() {
        testx("one.json")
        testx("one.xml")
        testx("one.XML")
        testx("one.json5")
        testx("one")
        testx("one.")
    }

    fun testx(fname: String) {
        val start = System.currentTimeMillis()
        val pair = extractExtension1(fname)
        val end = System.currentTimeMillis()
        println("extractExtension  $pair, (${end - start}))")

        val start2 = System.currentTimeMillis()
        val pair2 = extractExtension2(fname)
        val end2 = System.currentTimeMillis()
        println("extractExtension2 $pair2, (${end2 - start2}))")
    }

    @Test
    fun twow() {
        println(Paths.get("foasdf").getLastModifiedTime())
    }

    @Test
    fun oneasdf() {
        val fn = "area014.git.json"

        val jsonExtension = "(?i)\\.json5?$".toRegex()

        println("matches " + fn.contains(jsonExtension))
        println("matches " + "jeff.git".contains(jsonExtension))
        println("matches " + "jeff.git.json5".contains(jsonExtension))
        println("matches " + "jeff.git.json5.txt".contains(jsonExtension))


//        assertTrue(fn, GffFactory.regex.first().matches(fn))
//        val matchResult = GffFactory.regex.first().matchEntire(fn)!!
//        println(matchResult.value)
        GffFactory.getFactoryForFileName(fn)!!

//        var diff = "[InsertDelta, position: 624, lines: [  \"SkyBox\" : 0,]]\n"
//        println(TestGffFile.regex.matches(diff))
    }

    @Test
    fun as32() {
        val one = io.micronaut.serde.ObjectMapper.create(mapOf())
    }

    @Test
    fun asdfasdfsad() {
        println(Float.SIZE_BITS)
        println(Float.SIZE_BYTES)
    }

    @Test
    fun regex2() {

        fun getFileType(str: String): String {
            val regex = "(?i).*(dlg|are).json".toRegex()
            val matchResult = regex.matchEntire(str) ?: throw RuntimeException("unknown file extension")
            return matchResult.groupValues[1].lowercase()
        }
//        val str = "jeff.mcclure.dlg.jsonx"
        val str = "jeff.mcclure.Dlg.json"
        println(getFileType(str))

    }

    @Test
    fun json() {
        val mapper = ObjectMapper()

        class One {
            var name: String? = null
        }

        class Two {
            var conditionalParam: Map<String, Any> = mapOf("z" to "a", "name" to "Jeff", "last" to "McClure", "num" to 3)
        }

        class Three {
            var conditionalParam: List<Map<String, Any>> = listOf(mapOf("z" to "a", "name" to "Jeff"), mapOf("last" to "McClure"))
        }
        println(mapper.writeValueAsString(One().apply { name = "Jeff" }))
        println(mapper.writeValueAsString(Two()))
        println(mapper.writeValueAsString(Three()))


    }

    @Test
    fun sort() {
        val defaultList = listOf("Animation", "AnimLoop", "Text", "Script", "ActionParams", "Delay", "Comment", "Sound", "Quest", "QuestEntry", "EntriesList")
        val fieldOrder = listOf("Quest", "Animation", "Text", "AnimLoop")

        println(defaultList.sortFirstListBySecondList(fieldOrder))
    }


    @Test
    fun list() {
        val map = mapOf(1 to "a")
        println(map.get(1))
        println(map.get(2))
        println(map[1])
        println(map[2])

        val list1 = mutableListOf(1, 2, 3)
        val list2 = mutableListOf(8, 9, 10)

        list1.addAll(list2)
        list1.addAll(list2)

        val fin = list1.groupBy { it }.filter { it.value.size > 1 }

        println(fin)

    }

    @Test
    fun unit() {
        println(0.toChar())
        if (yes) return
        val dat: Short = 3.toShort()
        val buffer: ByteBuffer = ByteBuffer.allocate(2)
        buffer.order(ByteOrder.LITTLE_ENDIAN)
        buffer.putShort(dat)
        val arr = buffer.array()

        val ret = ByteArray(2)
        val d2 = dat.toInt()
        ret[0] = (d2 and 0xff).toByte()
        ret[1] = (d2 shr 8 and 0xff).toByte()


        println("arr = " + arr.toTypedArray().contentToString())
        println("d2  = " + ret.toTypedArray().contentToString())

        assertTrue(
            { "arrays are not equal arr=${arr.toTypedArray().contentToString()} d2=${ret.toTypedArray().contentToString()}" },
            Arrays.compare(arr, ret) == 0
        )
    }

    @Test
    fun asdf() {
        println(UInt.MAX_VALUE)
        println(4294967295)

        println((255u and 0xFFu).toByte())
        println(listOf(1, 2, 3))
//        println(UByte.MAX_VALUE)
//        println()

        println((255u and 0xFFu).toByte())
        println((255u and 0xFFu).toUByte().toByte())
//        println("%X".format((255.toUInt() and 0xFFu).toByte()))
//        println((255.toUInt() and 0xFFu).toUByte())

//        println(UByte.MAX_VALUE.toByte())
//        println("%X".format(UByte.MAX_VALUE.toByte()))
//        println("%X".format(Byte.MAX_VALUE))
//        println("%X".format(Byte.MIN_VALUE))
//        println()

//        println(UByte.MAX_VALUE.toInt())
//        println(Integer.toBinaryString(UByte.MAX_VALUE.toByte().toInt()))
    }

    @Test
    fun regex() {
        val regexStr = "(\\d+)-(\\d+)"
        val regex = regexStr.toRegex()

        fun proc(input: String): List<Int> {
            val result = regex.find(input) ?: return listOf(input.toInt())

            val (startStr, endStr) = result.destructured
            val start = startStr.toInt() - 1
            val end = endStr.toInt()

            println("start=$start, end=$end")

            (start..end).map { it }
            return (start until end).toList()
        }

        fun match(input: String) {
            println("$input = ${proc(input)}")
        }

        match("3")
        match("3-4")
        match("10-19")
    }

    @Test
    fun one() {
        val x = (-1).toByte()
        val byte: Byte = (-1).toByte()
        val byte2 = 0xff

        println("x=$x")
        println("byte=$byte")
        println("byte2=$byte2")
//        println("byte=${Integer.toHexString(-1)}")
        println("byte=" + String.format("%02x", byte))
        println("byte2=" + String.format("%02x", byte2))
        println("byte2=" + byte2.toUByte())
        println("byte2=" + byte2.toInt())
        println("byte=" + byte.toUByte())
        println("byte=" + byte.toUByte().toInt())
        println("byte=" + byte.toInt())
    }
}


interface IntA {
    fun getMessage(): String = "Hello, IntA"
}

interface IntB {
    fun getMessage(): String = "Hello, IntB"
}

