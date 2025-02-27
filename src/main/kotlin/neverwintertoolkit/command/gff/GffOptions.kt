package neverwintertoolkit.command.gff

import neverwintertoolkit.command.GlobalOptions
import neverwintertoolkit.file.gff.GffFile
import picocli.CommandLine
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.nio.file.Path
import kotlin.io.path.name

open class GffTextGenerator : GffOptions() {

    @CommandLine.Option(names = ["f", "-f"], required = false, description = ["generate output to files instead of standard out"])
    open var fOption = false

    fun foo(
        fileExtension: String,
        block: (GffFile, PrintStream) -> Unit
    ) {

        files.forEach { file ->
            val path = file.toPath()
            val outPath = if (fOption) {
                if (path.parent != null)
                    path.parent.resolve(path.name + fileExtension)
                else
                    Path.of(path.name + fileExtension)
            } else null

            val out1 = outPath?.let { PrintStream(FileOutputStream(it.toFile())) } ?: System.out!!
            try {
                val gff = GffFile(path, gffOptions = this, status = status)
                if (outPath != null) status.println("Writing_q $outPath")
                block(gff, out1)
                if (lOption) out1.println()
            } finally {
                if (outPath != null) out1.close()
            }
        }
    }
}

open class GffOptions constructor() : GlobalOptions() {

    open var mOption = false
    open var nOption = false
    open var structList: String? = null

    open var lOption = false
    open var files: List<File> = emptyList()
    open var zOption = false
}