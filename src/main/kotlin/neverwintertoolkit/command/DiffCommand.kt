package neverwintertoolkit.command

import neverwintertoolkit.file.erf.ErfFile
import neverwintertoolkit.getNwt
import neverwintertoolkit.globalSettings
import picocli.CommandLine
import java.io.File
import java.io.FileInputStream
import java.nio.channels.FileChannel
import java.nio.file.Files
import java.nio.file.Path
import java.text.NumberFormat
import kotlin.io.path.fileSize
import kotlin.io.path.isSameFileAs

@CommandLine.Command(
    name = "diff",
    mixinStandardHelpOptions = true,
    versionProvider = VersionInfo::class,
    description = ["Compare two erf archives.  If files are not provided, then the source and target from nwt.json5 will be used."],
    hidden = false
)
class DiffCommand : BaseCommand() {

    @CommandLine.Parameters(index = "0", description = ["file to compare"], arity = "0..1")
    var file1: File? = null

    @CommandLine.Parameters(index = "1", description = ["file to compare"], arity = "0..1")
    var file2: File? = null

    override fun called(): Int {
        var path1: Path? = null
        var path2: Path? = null
        if (file1 == null && file2 == null) {
            val nwt = getNwt()
            path1 = nwt.sourcePath
            path2 = nwt.targetPath
        } else if (file1 == null || file2 == null) {
            throw RuntimeException("file1 and file2 must be both null or both not-null")
        } else {
            path1 = file1!!.toPath()
            path2 = file2!!.toPath()
        }

        diff(path1!!, path2!!)
        return 0
    }

    private fun diff(path1: Path, path2: Path) {
        logInfo { "file1=$path1" }
        logInfo { "file2=$path2" }
        if (path1.isSameFileAs(path2)) {
            logInfo { "file1 and file2 are the same file" }
            return
        }
        if (path1.fileSize() == path2.fileSize() && Files.mismatch(path1, path2) == -1L) {
            logInfo { "file1 and file2 are identical" }
            return
        }

        val entries1 = ErfFile(path1, globalOptions = this).readAllEntries()
        val entries2 = ErfFile(path2, globalOptions = this).readAllEntries()

        val names1 = entries1.map { it.fileNameWithExtension }
        val names2 = entries2.map { it.fileNameWithExtension }

        val all = names1.union(names2).map { name ->
            val entry1 = entries1.firstOrNull { it.fileNameWithExtension == name }
            var size1: Long? = null
            var size2: Long? = null
            var status = ""
            if (entry1 != null) {
                size1 = entry1.resourceSize
                val entry2 = entries2.firstOrNull { it.fileNameWithExtension == name }
                if (entry2 != null) {
                    size2 = entry2.resourceSize

                    status = if (entry1.resourceSize != entry2.resourceSize) {
                        "different size"
                    } else {
                        var equal = false
                        FileInputStream(path1.toFile()).use { ins1 ->
                            FileInputStream(path2.toFile()).use { ins2 ->
                                val ch1 = ins1.channel
                                val xxx1 = ch1.map(FileChannel.MapMode.READ_ONLY, entry1.offsetToResource, entry1.resourceSize)
                                val ch2 = ins2.channel
                                val xxx2 = ch2.map(FileChannel.MapMode.READ_ONLY, entry2.offsetToResource, entry2.resourceSize)
                                equal = xxx1.equals(xxx2);
                            }
                        }
                        if (equal) "exact match" else "same size"
                    }
                } else {
                    status = "missing right"
                }
            } else {
                status = "missing left"
            }
            listOf(name, size1.formatThousands(), size2.formatThousands(), status)
        }

        val lll = all.sortedWith(compareBy<List<String>> { it[3].trim() }.thenBy { it[0].trim() }).toMutableList()
        lll.add(0, listOf("filename", "left size", "right size", "comparison"))
        printTable(lll)

        val both = names1.intersect(names2.toSet())
        val file1 = names1.minus(names2.toSet())
        val file2 = names2.minus(names1.toSet())

        logInfo { "\npresent in both=${both.sorted().joinToString(",")}" }
        logInfo { "\nmissing from file2=${file1.sorted().joinToString(",")}" }
        logInfo { "\nmissing from file1=${file2.sorted().joinToString(",")}" }
    }

}

fun printTable(all: List<List<Any?>>) {
    val sizes = ArrayList<Int>()
    repeat(all.first().size) {
        sizes.add(0)
    }
    val all2: List<List<String>> = all.map { row ->
        row.mapIndexed { index: Int, value: Any? ->
            val newVal = value?.toString() ?: ""
            val theSize = newVal.length
            if (theSize > sizes[index])
                sizes[index] = theSize
            newVal
        }
    }

    fun List<String>.printRow(char: Char = ' ') {
        val row2 = this.mapIndexed { index, str -> str.padStart(sizes[index], char) }
        println(row2.joinToString("$char|$char", "|$char", "$char|"))
    }

    all2.first().printRow()
    (0..<sizes.size).map { "" }.printRow('-')
    all2.drop(1).forEach { row ->
        row.printRow()
    }
}


//    val format = NumberFormat.getNumberInstance(Locale.US)
private val format = NumberFormat.getNumberInstance()

fun Long?.formatThousands(): String {
    return if (this == null) ""
    else format.format(this)
}

fun Int?.formatThousands(): String {
    return if (this == null) ""
    else format.format(this)
}
