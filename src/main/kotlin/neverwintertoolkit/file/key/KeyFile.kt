package neverwintertoolkit.file.key

import neverwintertoolkit.FileTypeIdMap
import neverwintertoolkit.command.GlobalOptions
import neverwintertoolkit.file.bif.BifFile
import neverwintertoolkit.readString
import neverwintertoolkit.readStringNullTerminated
import neverwintertoolkit.readUInt
import neverwintertoolkit.readUShort
import java.io.BufferedInputStream
import java.io.RandomAccessFile
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.PathMatcher
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.io.path.inputStream
import kotlin.io.path.name

class KeyFile(val file: Path, val globalOptions: GlobalOptions = GlobalOptions()) {
    val status = globalOptions.status

    val keyHeder: KeyHeader by lazy { readHeader() }

    data class KeyHeader(
        val fileType: String,
        val fileVersion: String,
        val bifCount: UInt = 0u,
        val keyCount: UInt = 0u,
        val offsetToFileTable: UInt = 0u,
        val offsetToKeyTable: UInt = 0u,
        var buildYear: UInt = 0u,
        var buildDay: UInt = 0u,
        var buildDate: LocalDate = LocalDate.parse(
            "${buildYear.toInt() + 1900}-${buildDay.inc().toString().padStart(3, '0')}",
            DateTimeFormatter.ISO_ORDINAL_DATE
        ),
        val reserved: ByteArray = ByteArray(0)
    )

    private fun readHeader(): KeyHeader {
        file.inputStream().buffered().use { input ->
            val fileType = input.readString(4)
            val fileVersion = input.readString(4)
            val bifCount = input.readUInt()
            val keyCount = input.readUInt()
            val offsetToFileTable = input.readUInt()
            val offsetToKeyTable = input.readUInt()
            val buildYear = input.readUInt()
            val buildDay = input.readUInt()
            var reserved = input.readNBytes(32)

//            val dateStr = "${buildYear.toInt() + 1900}-${buildDay.inc().toString().padStart(3, '0')}"
//            val buildDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_ORDINAL_DATE)

            if ("KEY " != fileType) throw RuntimeException("Warning fileType != 'KEY ' for $file")

            return KeyHeader(
                fileType = fileType,
                fileVersion = fileVersion,
                bifCount = bifCount,
                keyCount = keyCount,
                offsetToFileTable = offsetToFileTable,
                offsetToKeyTable = offsetToKeyTable,
                buildYear = buildYear,
                buildDay = buildDay,
                reserved = reserved,
            )
        }
    }

    data class FileEntry(
        val index: Int,
        val fileSize: UInt,
        val filenameOffset: UInt,
        val filenameSize: UShort,
        val drives: UShort,
        val fileName: String
    )

    fun list(patterns: List<String>, printArchiveName: Boolean) {
        globalOptions.logTrace { keyHeder.toString() }
        listFiles(patterns)
        listKeys(patterns, printArchiveName, false)
    }

    fun extract(patterns: List<String>, printArchiveName:Boolean, toStdout: Boolean = false) {
        listKeys(patterns, printArchiveName=printArchiveName, extract = true, toStdout = toStdout)
    }

    val files: List<FileEntry> by lazy { readFiles() }

    private fun listFiles(patterns: List<String> = emptyList()) {
        val rePatterns: List<PathMatcher> = patterns.map { FileSystems.getDefault().getPathMatcher("glob:${it.lowercase()}") } ?: emptyList()

        files.forEach { fileEntry: FileEntry ->
            if (globalOptions.traceEnabled || rePatterns.isEmpty() || rePatterns.any { it.matches(Path.of(fileEntry.fileName.lowercase()).fileName) })
                if (globalOptions.traceEnabled)
                    status.println(fileEntry)
                else
                    status.println(fileEntry.fileName)
        }
    }

    private fun readFiles(): List<FileEntry> {
        return RandomAccessFile(file.toFile(), "r").use { input ->
            (0..<keyHeder.bifCount.toInt()).map { index ->
                input.seek(keyHeder.offsetToFileTable.toLong() + index * 12)

                val fileSize = input.readUInt()
                val filenameOffset = input.readUInt()
                val filenameSize = input.readUShort()
                val drives = input.readUShort()

                input.seek(filenameOffset.toLong())
                val fileName = input.readString(filenameSize.toInt()).replace('\\', '/')

                val fileEntry = FileEntry(
                    index = index,
                    fileSize = fileSize,
                    filenameOffset = filenameOffset,
                    filenameSize = filenameSize,
                    drives = drives,
                    fileName = fileName
                )

                fileEntry
            }
        }
    }

    data class KeyEntry(
        val resRef: String,
        val resourceType: UShort,
        val resId: UInt,
        val fileIndex: Int? = null,
        val resourceIndex: Int? = null,
        val fullPath: Path
    )

    fun listKeys(patternsx: List<String>, extract: Boolean, printArchiveName: Boolean, toStdout: Boolean = false) {
        val rePatterns: List<PathMatcher> = patternsx.map { FileSystems.getDefault().getPathMatcher("glob:${it.lowercase()}") } ?: emptyList()

        file.inputStream().buffered().use { input: BufferedInputStream ->
            // input.seek(keyHeder.offsetToKeyTable.toLong() + it * 22)
            input.skip(keyHeder.offsetToKeyTable.toLong())
            repeat(keyHeder.keyCount.toInt()) {

                val resRef = input.readStringNullTerminated(16)
                val resourceType = input.readUShort()
                val resId = input.readUInt()
                val fileIndex = resId.toInt() shr 20
                val resourceIndex = resId.toInt() - (fileIndex shl 20)

                val fileEntry = files[fileIndex]
                val resourceName = resRef.lowercase() + FileTypeIdMap[resourceType.toInt()]!!.extension
                val dir = Paths.get(fileEntry.fileName)
                val fullPath = dir.resolve(resourceName)

                val keyEntry = KeyEntry(
                    resRef = resRef,
                    resourceType = resourceType,
                    resId = resId,
                    fileIndex = fileIndex,
                    resourceIndex = resourceIndex,
                    fullPath = fullPath
                )

                if (rePatterns.isEmpty() || rePatterns.any { it.matches(fullPath.fileName) }) {
                    if (extract) {
                        val bifFile = file.parent.parent.resolve(Path.of(fileEntry.fileName))
                        val bif = BifFile(bifFile, globalOptions)
                        val varResourcEntry = bif.getVariableEntry(resourceIndex)

                        if (!toStdout) {
                            if (!Files.isDirectory(dir))
                                Files.createDirectories(dir)

                            if (printArchiveName)
                                status.format("%s Extracting: %-50s %10d", file.name, fullPath, varResourcEntry.fileSize.toLong())
                            else
                                status.format(" Extracting: %-50s %10d", fullPath, varResourcEntry.fileSize.toLong())
                            status.flush()
                            bif.extract(resourceIndex, fullPath)
                            status.println()
                        } else {
                            if (printArchiveName)
                                System.out.format("Extracting: %-50s %10d\n", fullPath, varResourcEntry.fileSize.toLong())
                            else
                                System.out.format("%s Extracting: %-50s %10d\n", file.name, fullPath, varResourcEntry.fileSize.toLong())
                            bif.extract(resourceIndex, System.out)
                        }
                    } else {
                        if (globalOptions.traceEnabled)
                            if (printArchiveName)
                                status.format("%s %s", file.name, keyEntry)
                            else
                                status.println(keyEntry)
                        else if (globalOptions.debugEnabled) {
                            val bifFile = file.parent.parent.resolve(Path.of(fileEntry.fileName))
                            val bif = BifFile(bifFile, globalOptions)
                            val varResourcEntry = bif.getVariableEntry(resourceIndex)

                            status.format("%-50s %10d\n", keyEntry.fullPath, varResourcEntry.fileSize.toLong())
                        } else
                            status.println(fullPath)
                    }
                }
            }
        }
    }

}
