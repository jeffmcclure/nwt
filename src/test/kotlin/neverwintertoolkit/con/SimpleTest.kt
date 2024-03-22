package neverwintertoolkit.con

import org.junit.jupiter.api.Test
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
            GffFile(file, GffOptions().apply { oOption = true; vOption = true }).dump(writer)
        }
    }

}