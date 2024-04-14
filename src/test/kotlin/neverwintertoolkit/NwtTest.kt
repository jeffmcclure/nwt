package neverwintertoolkit

import kotlin.test.Test
import neverwintertoolkit.command.Unpack
import neverwintertoolkit.con.BaseTest
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.pathString
import kotlin.test.assertEquals

class NwtTest : BaseTest() {

    @Test
    fun blank() {
        val file = getFile()
        Nwt().toJson(file)
        Nwt.parseJson(file)
    }

    @Test
    fun nwtVariables() {
        val path = Paths.get("src/test/resources/nwt/nwt.json5")
        val nwt: Nwt = Nwt.parseJson(path).first()
        assertEquals("build/NeverwinterNights/module000aa_0_0_0.mod", nwt.targetPath.pathString)
    }

    @Test
    fun nwt() {
        json2(Paths.get("src/test/resources/nwt/nwt.json5"), Paths.get("build/nwt1"))
    }

    @Test
    fun nwtTemplate() {
        json2(Paths.get("src/main/resources/neverwintertoolkit/nwt-template.json5"), Paths.get("build/nwt1"))
    }

    @Test
    fun nwtArray() {
        json2(Paths.get("src/test/resources/nwt/nwtArray.json5"), Paths.get("build/nwt1"))
    }

    fun json2(nwtJson: Path, dir: Path) {
        val nwt = Nwt.parseJson(nwtJson)
        nwt.forEach {
            it.toJson(getFile())
        }
        Nwt.toJson(nwt, getFile())
        val arr = Unpack(nwtJson, dir, status = System.out, useJson = true).getTargets()

        arr.forEach {
            println(it)
        }
    }
}