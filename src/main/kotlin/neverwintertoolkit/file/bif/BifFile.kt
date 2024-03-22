package neverwintertoolkit.file.bif

import neverwintertoolkit.command.GlobalOptions
import neverwintertoolkit.copyBytesTo
import neverwintertoolkit.readString
import neverwintertoolkit.readUInt
import java.io.OutputStream
import java.nio.file.Path
import kotlin.io.path.inputStream
import kotlin.io.path.outputStream

class BifFile(val file: Path, val globalOptions: GlobalOptions = GlobalOptions()) {
    val status = globalOptions.status

    val bifHeader: BifHeader by lazy { readHeader() }

    data class BifHeader(
        val fileType: String,
        val fileVersion: String,
        val variableResourceCount: UInt = 0u,
        val fixedResourceCount: UInt = 0u,
        val variableTableOffset: UInt = 0u
    )

    private fun readHeader(): BifHeader {
        file.inputStream().buffered().use { input ->
            val head = BifHeader(
                fileType = input.readString(4),
                fileVersion = input.readString(4),
                variableResourceCount = input.readUInt(),
                fixedResourceCount = input.readUInt(),
                variableTableOffset = input.readUInt()
            )

            if ("BIFF" != head.fileType) throw RuntimeException("Warning fileType != 'BIFF' for $file")
            return head
        }
    }


    fun list() {
        status.println(bifHeader)
    }

    data class VariableResourceEntry(
        val id: UInt,
        val resourceEntryIndex: Int,
        val offset: UInt,
        val fileSize: UInt,
        val resourcetype: UInt
    )

    fun getVariableEntry(index: Int): VariableResourceEntry {
        return file.inputStream().buffered().use { input ->
            input.skip(bifHeader.variableTableOffset.toLong() + 16 * index)
            val id = input.readUInt()
            val fileIndex = id.toInt() shr 20
            val resourceIndex = id.toInt() - (fileIndex shl 20)
            VariableResourceEntry(
                id = id,
                resourceEntryIndex = resourceIndex,
                offset = input.readUInt(),
                fileSize = input.readUInt(),
                resourcetype = input.readUInt()
            )
        }

    }

    fun extract(resourceIndex: Int, out: OutputStream) {
        val entry = getVariableEntry(resourceIndex)
        return file.inputStream().buffered().use { input ->
            input.skip(entry.offset.toLong())
            input.copyBytesTo(out, entry.fileSize.toInt())
        }
    }

    fun extract(resourceIndex: Int, target: Path) {
        target.outputStream().use { out ->
            extract(resourceIndex, out)
        }
    }

}