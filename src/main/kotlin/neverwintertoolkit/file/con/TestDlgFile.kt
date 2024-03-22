package neverwintertoolkit.file.con

import neverwintertoolkit.JsonSettings
import neverwintertoolkit.command.gff.GffOptions
import neverwintertoolkit.file.gff.GffFile
import neverwintertoolkit.model.dlg.Dlg
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.name
import kotlin.io.path.writeText

class TestDlgFile constructor(
    val dlg: Path,
    val useReference: Boolean = true,
    val dlgFileCompare: Boolean = false,
    val dumpVerbose: Boolean = false,
    val logMessage: String = "",
    val debugTestMode: Boolean = false
) {
    val list = mutableListOf<Path>()

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

    val dlg0 = getFile("0.dlg")
    val dlg1 = getFile("1.dlg")
    val dlg3 = getFile("3.dlg")
    val dlg4 = getFile("4.dlg")
    val dlg5 = getFile("5.dlg")

    val dmp0 = getFile("0.txt")
    val dmp1 = getFile("1.txt")
    val dmp3 = getFile("3.txt")
    val dmp4 = getFile("4.txt")
    val dmp5 = getFile("5.txt")

    val json1 = getFile("1.json")
    val json2 = getFile("2.json")
    val json3 = getFile("3.json")
    val json4 = getFile("4.json")
    val json5 = getFile("5.json")

    fun deleteFiles() {
        list.forEach(Files::deleteIfExists)
    }

    fun jsonTest(reportFail: (msg: String, e: Exception?) -> Unit) {

        list.forEach(Files::deleteIfExists)
        Files.copy(dlg, dlg0, StandardCopyOption.REPLACE_EXISTING)

        try {
            val xxx = GffFile(dlg).readObject() as Dlg //Con.readGff(dlg0)
            json1.writeText(xxx.apply { /*renumberStructs()*/ }.toJson())
            GffFile(dlg0, GffOptions().apply { vOption = dumpVerbose; mOption = true }).dump(dmp0)

            val con0 = GffFile(dlg).readObject() as Dlg
            val reference = if (useReference) GffFile(dlg0) else null
//            con0.writeDlg(dlg1, reference)
            con0.writeGff(dlg1)


            val gff = GffFile(dlg1, GffOptions().apply { vOption = dumpVerbose; mOption = true })
            gff.dump(dmp1)

            val con = gff.readObject() as Dlg
            json2.writeText(con.apply { /* renumberStructs() */ }.toJson())

            /*
             * 3rd iteration
             */
            val con1 = Dlg.factory.parseJson(json1)
//            con1.writeDlg(dlg3, reference, renumberStructs = false) // renumberStructs = false so .dlg file binary compare is same
            con1.writeGff(dlg3)
            json3.writeText((GffFile(dlg3).readObject() as Dlg).apply { /*renumberStructs() */}.toJson())
            GffFile(dlg3, GffOptions().apply { vOption = dumpVerbose; mOption = true }).dump(dmp3)

            /*
             * 4th iteration
             */
            json4.writeText((GffFile(dlg3).readObject() as Dlg).apply { /*removeIndexField(); removeUnreferencedStructIds()*/ }
                .toJson(JsonSettings(pretty = true)))
            val con4 = Dlg.factory.parseJson(json4)
//            con4.writeDlg(dlg4, reference)
            con4.writeGff(dlg4)
            GffFile(dlg4, GffOptions().apply { vOption = dumpVerbose; mOption = true }).dump(dmp4)

            /*
             * 5th iteration
             */
            json5.writeText((GffFile(dlg4).readObject() as Dlg).apply { /*removeIndexField(); removeUnreferencedStructIds()*/ }
                .toJson(JsonSettings(pretty = true)))
            val con5 = Dlg.factory.parseJson(json5)
//            con5.writeDlg(dlg5, reference)
            con5.writeGff(dlg5)
            GffFile(dlg5, GffOptions().apply { vOption = dumpVerbose; mOption = true }).dump(dmp5)

            /**
             * @return null on failure
             */
            fun eval(file1: Path, file2: Path, msg: String): Any? {
                return if (-1L != Files.mismatch(file1, file2)) {
                    reportFail("mismatch for $msg $dmp1: file1=$file1, file2=$file2", null)
                    null
                } else
                    true
            }

            /*
             * compare files
             */
//            eval(dmp0, dmp1, dlg.name) ?: return
            /*
             * cannot compare after passing through json, null values are removed
            eval(dmp0, dmp3, dlg.name) ?: return
            eval(dmp0, dmp4, dlg.name) ?: return
            eval(dmp0, dmp5, dlg.name) ?: return
             */

            eval(json1, json2, dlg.name) ?: return
            eval(json1, json3, dlg.name) ?: return

            if (dlgFileCompare) {
                eval(dlg0, dlg1, dlg.name) ?: return

                // cannot compare after passing through json, json removes nulls
                // eval(dlg0, dlg3, dlg.name) ?: return
            }
        } catch (e: Exception) {
            e.printStackTrace()
            reportFail(e.message ?: "Exception", e)
        }
    }
}