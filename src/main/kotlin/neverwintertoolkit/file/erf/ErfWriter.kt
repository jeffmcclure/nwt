package neverwintertoolkit.file.erf

import neverwintertoolkit.FileTypeByExtensionMap
import neverwintertoolkit.command.GlobalOptions
import neverwintertoolkit.extractFileName
import neverwintertoolkit.toBytes
import java.io.OutputStream
import java.nio.file.Path
import java.time.LocalDate
import kotlin.io.path.exists
import kotlin.io.path.extension
import kotlin.io.path.fileSize
import kotlin.io.path.inputStream
import kotlin.io.path.isRegularFile
import kotlin.io.path.name
import kotlin.io.path.outputStream
import kotlin.math.min

enum class ErfFileType(val strVal: String) {
    ERF("ERF "),
    MOD("MOD "),
    NWM("NWM "),
    SAV("SAV "),
    HAK("HAK ");

    companion object {
        val erfFileTypeMap = ErfFileType.entries.associateBy { it.strVal }
    }
}

enum class ErfFileVersion(val strVal: String) {
    V1_0("V1.0");

    companion object {
        val erfFileVersionMap = ErfFileVersion.entries.associateBy { it.strVal }
    }
}

class ErfWriter(val options: GlobalOptions = GlobalOptions(), strings: List<AString> = emptyList()) {
    // default to an empty string if there are none
    val strings = strings.ifEmpty { listOf(AString("", 0)) }
    val files = mutableListOf<Path>()
    val names = mutableSetOf<String>()

    fun addFile(path: Path) {
        if (!path.isRegularFile()) {
            if (!path.exists())
                throw RuntimeException("$path does not exist")
            else
                throw RuntimeException("$path is not a regular file")
        }

        if ("erf.json" == path.name || "erf.json5" == path.name) {
//            options.logInfo { "found ${path.name}" }
//            setErfJson(path)
        } else {
            if (path.name.extractFileName().length > 16)
                throw RuntimeException("${path.name} base name is greater than 16 characters")

            synchronized(names) {
                if (names.contains(path.name)) {
                    options.logInfo { "ERF already contains ${path.name}" }
                } else {
                    names.add(path.name)
                    files.add(path)
                }
            }
        }
    }

    fun writeErf(erfTargetFile: Path) {
        val type = ErfFileType.erfFileTypeMap[erfTargetFile.extension.uppercase() + " "]
            ?: throw Exception("error finding type for extension '${erfTargetFile.extension}'")
        val version = ErfFileVersion.V1_0

        erfTargetFile.outputStream().use { out ->
            files.sortBy { it.name.lowercase() }
            val header = ErfHeader(type, version)
            writeHeader(out, header)
            writeLocalizedStringList(out)
            writeKeyList(out)
            writeResourceList(out, header)
            writeResourceData(out)
        }
    }

    private fun writeResourceData(out: OutputStream) {
        val count = files.size
        files.sortedBy { it.name.lowercase() }.forEachIndexed { index, aFile ->
            options.logTrace { "writing %5d / %5d %s".format(index, count, aFile.name) }
            aFile.inputStream().use { input ->
                input.copyTo(out)
            }
        }
    }

    private fun writeResourceList(out: OutputStream, header: ErfHeader) {
        var dataStart = header.offsetToResourceList + (8 * files.size).toUInt()
        files.forEach { aFile ->
            out.write(dataStart.toBytes())
            val size = aFile.fileSize()
            dataStart += size.toUInt()
            out.write(size.toUInt().toBytes())
        }
    }

    private fun writeKeyList(out: OutputStream) {
        var resId = 0
        files.forEach { aFile ->
            val bname = aFile.name
            val index = bname.lastIndexOf('.')
            val name = bname.substring(0, index)
            val extension = bname.substring(index)
            logger.trace("name={}, extension={}", name, extension)

            val nameArray = ByteArray(16)
            val nam = name.toByteArray()
            nam.copyInto(nameArray, 0, 0, min(nam.size, 16))
            out.write(nameArray)
            out.write(resId.toBytes())
            resId++
            val type = FileTypeByExtensionMap[extension] ?: throw RuntimeException("could not find extension '$extension'")
            logger.trace("type={}", type)
            out.write(type.id.toUInt().toBytes(), 0, 2)
            out.write(ByteArray(2))
        }

        // there is blank space after the key list.  Not documented
        val buf = ByteArray(8)
        repeat(files.size) {
            out.write(buf)
        }
    }

    private fun writeLocalizedStringList(out: OutputStream) {
        strings.forEach { aString ->
            out.write(aString.languageId!!.toBytes())
            val str = aString.stringValue!!.toByteArray()
            out.write(str.size.toBytes())
            out.write(str)
        }
    }

    data class ErfHeader(
        var fileType: ErfFileType,
        var fileVersion: ErfFileVersion,
        var languageCount: UInt = 0u,
        var localizedStringSize: UInt = 0u,
        var entryCount: UInt = 0u,
        var offsetToLocalizedString: UInt = 0u,
        var offsetToKeyList: UInt = 0u,
        var offsetToResourceList: UInt = 0u,
        var buildYear: Int = 0,
        var buildDay: Int = 0,
        var descriptionStrRef: UInt = 4294967295u // 4,294,967,295 is default?
    )

    private fun writeHeader(out: OutputStream, header: ErfHeader) {
        header.apply {
            languageCount = strings.size.toUInt()
            localizedStringSize = strings.sumOf { it.stringValue!!.toByteArray().size + 8 }.toUInt()
            entryCount = files.size.toUInt()
            offsetToLocalizedString = 160u
            offsetToKeyList = offsetToLocalizedString + localizedStringSize
            offsetToResourceList = offsetToKeyList + (files.size * (24 + 8)).toUInt() // 24 needed, plus 8 null at end.   see writeKeyList()
            buildYear = LocalDate.now().year - 1900
            buildDay = LocalDate.now().dayOfYear - 1
        }

        logger.trace("writing header {}", header)
        writeHeaderToFile(out, header)
    }

    private fun writeHeaderToFile(out: OutputStream, header: ErfHeader) {
        fun write(arr: ByteArray) {
            out.write(arr)
        }
        write(header.fileType.strVal.toByteArray())
        write(header.fileVersion.strVal.toByteArray())
        write(header.languageCount.toBytes())
        write(header.localizedStringSize.toBytes())
        write(header.entryCount.toBytes())
        write(header.offsetToLocalizedString.toBytes())
        write(header.offsetToKeyList.toBytes())
        write(header.offsetToResourceList.toBytes())
        write(header.buildYear.toBytes())
        write(header.buildDay.toBytes())
        write(header.descriptionStrRef.toBytes())
        write(ByteArray(116))
    }

    companion object {
        val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!
    }

    data class AString(
        var stringValue: String? = null,
        var languageId: Int? = null
    )

}
