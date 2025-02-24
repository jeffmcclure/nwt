package neverwintertoolkit.con

import kotlin.test.Test
import neverwintertoolkit.command.gff.GffOptions
import neverwintertoolkit.file.gff.GffFile
import java.io.PrintStream
import java.nio.file.Path
import kotlin.io.path.outputStream

class SimpleTest : BaseTest() {

    @Test
    fun simple() {
        simple(f("/con/reference/con_aaa.dlg")) // 13 structs
    }

    fun simple(file: Path) {
        PrintStream(Path.of("build/out.txt").outputStream()).use { writer ->
            writer.println("$file")
            GffFile(file, GffOptions().apply { globalOptions.oOption = true; globalOptions.vOption = true }).dump(writer)
        }
    }

}