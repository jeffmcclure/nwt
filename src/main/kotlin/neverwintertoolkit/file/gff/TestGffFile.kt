package neverwintertoolkit.file.gff

import com.github.difflib.DiffUtils
import neverwintertoolkit.JsonSettings
import neverwintertoolkit.command.gff.GffOptions
import neverwintertoolkit.extractExtension1
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.name
import kotlin.io.path.writeText

class TestGffFile<T : GffObj>(
    val gffFactory: GffFactory<T>,
    val gffFile: Path,
    val gffFileCompare: Boolean,
    val debugTestMode: Boolean,
    val compareRawJson: Boolean,
    val compareAllJson: Boolean = false,
    val compareStructCounts: Boolean = true,
    val regex: List<Regex> = listOf()
) {
    val extension = extractExtension1(gffFile.name).second
    val list = mutableListOf<Path>()
    val gffOptions =  GffOptions()

    fun getFile(base: String): Path {
        val newFile = if (debugTestMode)
            Path.of("build/$base")
        else {
            Files.createTempFile("tmp", base)
        }
//        println("newFile=$newFile")

        list.add(newFile)
        return newFile
    }

    val gff0 = getFile("0$extension")
    val gff1 = getFile("1$extension")
    val gff3 = getFile("3$extension")
    val gff4 = getFile("4$extension")
    val gff5 = getFile("5$extension")

    val dmp0 = getFile("0a.json")
    val dmp1 = getFile("1a.json")
    val dmp3 = getFile("3a.json")
    val dmp4 = getFile("4a.json")
    val dmp5 = getFile("5a.json")

    val json1 = getFile("1.json")
    val json2 = getFile("2.json")
    val json3 = getFile("3.json")
    val json4 = getFile("4.json")
    val json5 = getFile("5.json")

    val json1NoStructId = getFile("1NoStructId.json")
    val json2NoStructId = getFile("2NoStructId.json")
    val json3NoStructId = getFile("3NoStructId.json")

    fun deleteFiles() {
        list.forEach(Files::deleteIfExists)
    }

    fun jsonTest(reportFail: (msg: String, e: Exception?) -> Unit) {
        /**
         * @return null on failure
         */
        fun eval(file1: Path, file2: Path, msg: String): Any? {
            return if (-1L != Files.mismatch(file1, file2)) {
                val diff = diffFiles(file1, file2)

                // area files - SkyBox is sometimes there and sometimes not there
                if (regex.any { it.matches(diff) }) {
                    true
                } else {
                    reportFail("mismatch for $msg ${file1.name} ${file2.name} \n$diff", null)
//                        throw RuntimeException("mismatch for $msg ${file1.name} ${file2.name} \n$diff", null)
                    null
                }
            } else
                true
        }

        list.forEach(Files::deleteIfExists)
        Files.copy(gffFile, gff0, StandardCopyOption.REPLACE_EXISTING)

        try {
            val xxx = GffFile(gff0, gffOptions).readObject()
            json1.writeText(xxx.toJson())
            dmp0.writeText(GffFile(gff0, gffOptions).toRawJson())

            json1NoStructId.writeText(GffFile(gff0, gffOptions).readObject().also { it.removeStructIds() }.toJson())

            val con0 = GffFile(gff0, gffOptions).readObject()
            con0.writeGff(gff1)

            if (logger.isTraceEnabled) {
                val x1 = ByteArrayOutputStream()
                val x2 = PrintStream(x1)
                GffFile(gff1, gffOptions).dump(x2) // TODO remove
                logger.trace("x={}", x1.toString(Charsets.UTF_8))
            }

            val gff = GffFile(gff1, gffOptions)
            dmp1.writeText(gff.toRawJson())

            val con = gff.readObject()
            json2.writeText(con.toJson())
            json2NoStructId.writeText(gff.readObject().also { it.removeStructIds() }.toJson())

            /*
             * 3rd iteration
             */
            val con1 = gffFactory.parseJson(json1)
            con1.writeGff(gff3) // renumberStructs = false so gff file binary compare is same
            json3.writeText(GffFile(gff3, gffOptions).readObject().toJson())
            dmp3.writeText(GffFile(gff3, gffOptions).toRawJson())

            val obj3 = GffFile(gff3, gffOptions).readObject().also { it.removeStructIds() }
            val txt = obj3.toJson(JsonSettings())
            json3NoStructId.writeText(txt)

            /*
             * 4th iteration
             */
            json4.writeText(GffFile(gff3, gffOptions).readObject().toJson(JsonSettings(pretty = true)))
            val con4 = gffFactory.parseJson(json4)
            con4.writeGff(gff4)
            dmp4.writeText(GffFile(gff4, gffOptions).toRawJson())

            /*
             * 5th iteration
             */
            json5.writeText(
                GffFile(gff4, gffOptions).readObject().toJson(JsonSettings(pretty = true))
            )
            val con5 = gffFactory.parseJson(json5)
            con5.writeGff(gff5)
            dmp5.writeText(GffFile(gff5, gffOptions).toRawJson())

            eval(json1NoStructId, json2NoStructId, gffFile.name) ?: return
            eval(json1NoStructId, json3NoStructId, gffFile.name) ?: return

            val base = GffFile(gff0, gffOptions).gffHeader
            listOf(gff1, gff3, gff4, gff5).forEach {
                val head = GffFile(it, gffOptions).gffHeader
                if (base.structCount != head.structCount
//                    || base.fieldCount != head.fieldCount
//                    || base.labelCount != head.labelCount
                ) {
                    reportFail(
                        "header mismatch structCount $gff0=${base.structCount} != ${head.structCount} " +
                                " labelCount $gff0=${base.labelCount} != ${head.labelCount} for $it}" +
                                " fieldCount $gff0=${base.fieldCount} != ${head.fieldCount} for $it}" +
                                "for $it}", null
                    )
                }
            }

            if (compareStructCounts) {
                val baseStructSummary = getStructSummary(gff0)
                listOf(gff1, gff3, gff4, gff5).forEach { path ->
                    val structSummary = getStructSummary(path)
                    if (baseStructSummary != structSummary) {
                        val allStructIds = baseStructSummary.map { it.structId }.toMutableSet().also { it.addAll(structSummary.map { it.structId }) }
                        val zipped = allStructIds.map { structId ->
                            val right = structSummary.find { it1 -> it1.structId == structId }?.count ?: 0
                            val left = baseStructSummary.find { it1 -> it1.structId == structId }?.count ?: 0
                            Merge(structId, left, right)
                        }

                        zipped.filter { it.leftCount == it.rightCount }.sortedBy { it.structId }.forEach {
                            logger.error("same structId={}, count={}", it.structId, it.leftCount)
                        }
                        zipped.filter { it.leftCount != it.rightCount }.sortedBy { it.structId }.forEach {
                            logger.error("diff structId={}, leftCount={}, rightCount={}", it.structId, it.leftCount, it.rightCount)
                        }

                        reportFail("3 struct mismatch $gff0 != $path", null)
                    }
                }
            }

            if (compareAllJson) {
                eval(json1, json2, gffFile.name) ?: return
                eval(json1, json3, gffFile.name) ?: return
            }

            /*
             * compare files
             */
            if (compareRawJson) {
//                eval(dmp0, dmp1, gffFile.name) ?: return
//                eval(dmp0, dmp3, gffFile.name) ?: return
//                eval(dmp0, dmp4, gffFile.name) ?: return
                // cannot compare to original dmp0 because use of NwnField.alwaysGenerate changes the baseline output from original gff
                eval(dmp1, dmp3, gffFile.name) ?: return
                eval(dmp1, dmp4, gffFile.name) ?: return
//            eval(dmp0, dmp5, gff0.name) ?: return
            }


            if (gffFileCompare) {
                eval(gff0, gff1, gffFile.name) ?: return
                eval(gff0, gff3, gffFile.name) ?: return
            }
        } catch (e: Exception) {
//            e.printStackTrace() // TODO
            reportFail(e.message ?: "Exception", e)
        }
    }

    data class Count(val structId: UInt, val count: Int)
    data class Merge(val structId: UInt, val leftCount: Int, val rightCount: Int)

    private fun getStructSummary(file: Path): Set<Count> {
        val retval = GffFile(file, gffOptions).structs.map { it.type }.groupBy { it }
            .entries.map { (key, valu) -> Count(key, valu.size) }.toSet()
        return retval
    }

    companion object {
        val logger = org.slf4j.LoggerFactory.getLogger(TestGffFile::class.java.toString())
    }
}

fun diffFiles(baseFile: Path, newFile: Path): String {
    val original = Files.readAllLines(baseFile)
    val revised = Files.readAllLines(newFile)

    val patch = DiffUtils.diff(original, revised)

    val b1 = ByteArrayOutputStream()
    val ps = PrintStream(b1)
    for (delta in patch.deltas) {
        ps.println(delta)
    }

    ps.flush()
    ps.close()
    return b1.toString(Charsets.UTF_8)
}
