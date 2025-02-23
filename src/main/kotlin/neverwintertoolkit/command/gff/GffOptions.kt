package neverwintertoolkit.command.gff

import neverwintertoolkit.command.GlobalOptions
import neverwintertoolkit.file.gff.GffFile
import picocli.CommandLine
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.nio.file.Path
import kotlin.io.path.name

open class GffOptions constructor(val globalOptions: GlobalOptions = GlobalOptions()) {

    open var mOption = false
    open var nOption = false
    open var structList: String? = null

    @CommandLine.Option(names = ["f", "-f"], required = false, description = ["generate output to files instead of standard out"])
    open var fOption = false

    open var lOption = false
    open var files: List<File> = emptyList()
    open var zOption = false

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

            val out2 = outPath?.let { PrintStream(FileOutputStream(it.toFile())) } ?: System.out!!
            try {
                val gff = GffFile(path, gffOptions = this, status = globalOptions.status)
                if (outPath != null) globalOptions.status.println("Writing $outPath")
                block(gff, out2)
                if (lOption) out2.println()
            } finally {
                if (outPath != null) out2.close()
            }
        }
    }
}