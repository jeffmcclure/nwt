package neverwintertoolkit

import neverwintertoolkit.con.BaseTest
import neverwintertoolkit.file.erf.ErfFile
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertFalse

class ErfFileTest : BaseTest() {

    @Test
    fun test1() {
        val path = f("/mod/module000a.mod")
        assert(Files.exists(path)) { "$path file does not exist" }
        assertFalse(ErfFile(path).readAllEntries().isEmpty(), "empty list")
    }

    @Test
    fun extract() {
        val path = f("/mod/module000a.mod")
        assert(Files.exists(path)) { "$path file does not exist" }

        ErfFile(path).extractAll(getDir(), noErf = false, toStdout = false, status = System.out, useJson = true)
    }

}
