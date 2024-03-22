package neverwintertoolkit.file.erf

import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.serde.annotation.Serdeable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import neverwintertoolkit.FileType
import neverwintertoolkit.FileTypeIdMap
import neverwintertoolkit.command.GlobalOptions
import neverwintertoolkit.command.formatThousands
import neverwintertoolkit.command.printTable
import neverwintertoolkit.copyBytesTo
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffFile
import neverwintertoolkit.globalSettings
import neverwintertoolkit.process32BitIntAsLong
import neverwintertoolkit.read16BitNumber
import neverwintertoolkit.read32BitNumber
import neverwintertoolkit.readString
import neverwintertoolkit.readStringNullTerminated
import java.io.BufferedInputStream
import java.io.PrintStream
import java.io.RandomAccessFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.io.path.inputStream
import kotlin.io.path.name
import kotlin.io.path.writer

/**
 *   encapsulated file: an encapsulated resource file (ERF),
 *   BioWare Aurora Engine/Toolset files that use the ERF format include the following: .erf, .hak, .mod, and .nwm.
 */
class ErfFile(val file: Path, val globalOptions: GlobalOptions = GlobalOptions(), val outStatus: PrintStream = globalOptions.status) {

    val begin = mem()
    val start = mem()
    val erfHeader = readHeader()

//    init {
//        val end = mem()
//        println()
//        begin.print()
//        println()
//        start.print()
//        println()
//        end.print()
//    }

    fun listAllEntries(pattern: Regex?, printArchiveName: Boolean, out: PrintStream = this.outStatus) {
        val list = readAllEntries()
        val list1 = list.map { Pair(it.fileNameWithExtension, it.resourceSize.formatThousands()) }
        // filter after adding the extension
        val list2 = pattern?.let { regex ->
            list1.filter { regex.matches(it.first) }
        } ?: list1
        if (list2.isEmpty()) {
            out.println("No matching files" + if (printArchiveName) " ${file.name}" else "")
            return
        }
        val header = listOf("FileName", "Size")
        val lll2 = list2.map { listOf(it.first, it.second) }.toMutableList()
        lll2.add(0, header)
        printTable(lll2)
    }

    fun readAllEntries(): List<ErfFileEntry> {
        val keyList = readKeyList()
        val resourceList = readResourceList()
        return keyList.zip(resourceList).map { pair ->
            val entry = ErfFileEntry(
                fileName = pair.first.fileName,
                resourceId = pair.first.resourceId,
                resType = pair.first.resType,
                resourceSize = pair.second.resourceSize,
                offsetToResource = pair.second.offsetToResource
            )
            entry
        }
    }

    data class Mem(val totalMemory: Long, val freeMemory: Long) {
        fun print() {
//            println("totalMemory = %,d".format(totalMemory))
//            println("freeMemory  = %,d".format(freeMemory))
//            println("usedMemory  = %,d".format(totalMemory - freeMemory))
        }
    }

    private fun mem(): Mem {
        return Mem(
            totalMemory = Runtime.getRuntime().totalMemory(),
            freeMemory = Runtime.getRuntime().freeMemory()
        )
    }

    private fun readResourceList(): List<ErfFileEntry> {
        file.inputStream().buffered().use { input ->
            input.skip(erfHeader.offsetToResourceList)
            return (0 until erfHeader.entryCount.toInt()).map { readResource(input) }
        }
    }

    private fun readResource(input: BufferedInputStream): ErfFileEntry {
        return ErfFileEntry(
            offsetToResource = input.read32BitNumber(),
            resourceSize = input.read32BitNumber(),
            resourceId = 0L,
            fileName = "",
            resType = 0
        )
    }

    private fun readKeyList(): List<ErfFileEntry> {
        file.inputStream().buffered().use { input ->
            input.skip(erfHeader.offsetToKeyList)
            return (0 until erfHeader.entryCount.toInt()).map { readEntryKey(input) }
        }
    }


    private fun readEntryKey(input: BufferedInputStream): ErfFileEntry {
        return ErfFileEntry(
            fileName = input.readStringNullTerminated(16),
            resourceId = input.read32BitNumber(),
            resType = input.read16BitNumber(),
            offsetToResource = 0L,
            resourceSize = 0L
        ).also {
            input.skip(2)
        }
    }

    fun readStrings(): List<ErfString> {
        file.inputStream().buffered().use { input ->
            input.skip(erfHeader.offsetToLocalizedString)
            return (0..<erfHeader.languageCount.toInt()).map { stringNum ->
                readLocalizedStringList(input, stringNum)
            }
        }
    }

    @Serdeable
    @Introspected
    @ReflectiveAccess
    data class ErfString(
        val stringIndex: Int,
        val languageId: Long,
        val stringSize: Long,
        val stringValue: String
    )

    private fun readLocalizedStringList(input: BufferedInputStream, stringIndex: Int): ErfString {
        val languageId = input.read32BitNumber()
        val stringSize = input.read32BitNumber()
        val stringValue = input.readStringNullTerminated(stringSize.toInt())
        return ErfString(
            stringIndex = stringIndex,
            languageId = languageId,
            stringSize = stringSize,
            stringValue = stringValue
        )
    }


    private fun readHeader(): ErfHeader2 {
        file.inputStream().buffered().use { input ->
            val fileType = input.readString(4)
            val version = input.readString(4)
            val languageCount = input.read32BitNumber()
            val localizedStringSize = input.read32BitNumber()
            val entryCount = input.read32BitNumber()
            val offsetToLocalizedString = input.read32BitNumber()
            val offsetToKeyList = input.read32BitNumber()
            val offsetToResourceList = input.read32BitNumber()
            val buildYear = input.read32BitNumber()
            val buildDay = input.read32BitNumber()
            val fourBytes = input.readNBytes(4)
            val descriptionStrRef = fourBytes.process32BitIntAsLong()
            //.erf, .hak, .mod, and .nwm.

            when (ErfFileType.erfFileTypeMap[fileType]) {
                ErfFileType.MOD,
                ErfFileType.ERF,
                ErfFileType.HAK -> {
                }

                else -> {
                    outStatus.println("Unexpected file type of '$fileType' for $file")
                }
            }

            return ErfHeader2(
                fileType = fileType,
                version = version,
                languageCount = languageCount,
                localizedStringSize = localizedStringSize,
                entryCount = entryCount,
                offsetToLocalizedString = offsetToLocalizedString,
                offsetToKeyList = offsetToKeyList,
                offsetToResourceList = offsetToResourceList,
                buildYear = buildYear,
                buildDay = buildDay,
                descriptionStrRef = descriptionStrRef
            )
        }
    }

    fun extractAll(
        targetDir: Path = Paths.get(System.getProperty("user.dir")),
        useJson: Boolean,
        toStdout: Boolean,
        noErf: Boolean,
        patterns: List<String> = emptyList(),
        status: PrintStream
    ): Int {
        if (!noErf) {
            extractErfJson(targetDir, status = status)
        }

        val list0 = readAllEntries()

        val rePattern = patterns.map { it.toRegex() }

        val list = if (rePattern.isEmpty())
            list0
        else
            list0.filter { entry -> rePattern.any { pattern -> entry.fileNameWithExtension.matches(pattern) } }

        if (list.isEmpty()) {
            println("No matching files" + if (globalOptions.debugEnabled) " ${file.name}" else "")
            return 1
        }

        val count = list.size

        list.forEachIndexed { index, entry ->
            try {
                val targetPath = targetDir.resolve(entry.fileNameWithExtension)
                extractEntry(entry, targetPath, useJson = useJson, toStdout = toStdout, status = { name ->
                    globalOptions.logInfo {
                        "Extracting ${index + 1}/$count $name"
                    }
                    if (globalOptions.infoEnabled) status.flush()
                })
            } catch (e: Exception) {
                logger.error("Error Extracting ${index + 1}/$count: " + e.message, e)
                System.err.println("Error Extracting ${index + 1}/$count: " + e.message)
            }
        }
        globalOptions.logInfo { "Done" }

        return 0
    }

    fun extractEntry(entry: ErfFileEntry, targetPath: Path, useJson: Boolean, toStdout: Boolean, status: (name: String) -> Unit = {}) {
        logger.debug("extract {}[{}] to {}", file, entry.fileNameWithExtension, targetPath)

        val convertToJson = useJson &&
                GffFactory.getFactoryForFileName(entry.fileNameWithExtension) != null // is json format supported

        if (toStdout) {
            if (convertToJson) {
                val gffFile = GffFile(file, globalOffset = entry.offsetToResource, status = outStatus, entryName = targetPath.name)
                val obj = gffFile.readObject()
                obj.writeJson(System.out)
            } else {
                file.toFile().inputStream().use { input ->
                    input.skip(entry.offsetToResource)
                    input.copyBytesTo(System.out, entry.resourceSize.toInt())
                }
            }
        } else {
            if (!Files.exists(targetPath.parent))
                Files.createDirectories(targetPath.parent)

            /**
             * @return true if extract should continue
             */
            fun overwriteCheck(path: Path): Boolean {
                val exists = Files.exists(path)
                if (exists) {
                    System.err.println("skipping existing file ${path.name}")
                }
                return !exists
            }

            if (convertToJson) {
                val name = targetPath.name + globalSettings.getJsonExtension()
                val gffFile = GffFile(file, globalOffset = entry.offsetToResource, status = outStatus, entryName = targetPath.name)
                val tpath = targetPath.parent?.resolve(name) ?: Paths.get(name)
                if (overwriteCheck(tpath)) {
                    status(name)
                    val obj = try {
                        gffFile.readObject()
                    } catch (e: Exception) {
                        throw RuntimeException("Error reading entry $entry", e)
                    }
                    obj.writeJson(tpath)
                }
            } else {
                if (overwriteCheck(targetPath)) {
                    status(targetPath.name)
                    RandomAccessFile(targetPath.toFile(), "rw").channel.use { out ->
                        RandomAccessFile(file.toFile(), "r").channel.use { inChannel ->
                            val transferred = inChannel.transferTo(entry.offsetToResource, entry.resourceSize, out)
                            if (transferred != entry.resourceSize) {
                                throw RuntimeException("Extracting $targetPath ... resourceSize=${entry.resourceSize} offsetToResource=${entry.offsetToResource} transferred=${transferred}")
                            }
                        }
                    }
                }
            }
        }
    }

    fun extractErfJson(dir: Path, status: PrintStream) {
        logger.debug("extractJson() enter")
        val target = dir.resolve("erf${globalSettings.getJsonExtension()}")
        status.println("Extracting ${target.name}")
        val strings = readStrings()

        val format = Json { prettyPrint = true }
        val json: JsonObject = buildJsonObject {
            put("fileType", erfHeader.fileType)
            put("version", erfHeader.version)
            put("languageCount", erfHeader.languageCount)
            put("entryCount", erfHeader.entryCount)
            put("localizedStringSize", erfHeader.localizedStringSize)
            put("buildDate", erfHeader.buildDate.toString())
            putJsonArray("strings") {
                strings.forEach { aString ->
                    add(buildJsonObject {
                        put("stringValue", aString.stringValue)
                        put("languageId", aString.languageId)
                    })
                }
            }
        }

        target.writer().use { writer ->
            writer.write(format.encodeToString(json))
        }
        logger.debug("extractJson() exit")
    }

    inner class ErfHeader2(
        val fileType: String,
        val version: String,
        val languageCount: Long,
        val localizedStringSize: Long,
        val entryCount: Long,
        val offsetToLocalizedString: Long,
        val offsetToKeyList: Long,
        val offsetToResourceList: Long,
        val buildYear: Long,
        val buildDay: Long,
        val buildDate: LocalDate = LocalDate.parse("${buildYear + 1900}-${buildDay.inc().toString().padStart(3, '0')}", DateTimeFormatter.ISO_ORDINAL_DATE),
        val descriptionStrRef: Long
    ) {

        fun print(out: PrintStream = outStatus) {
            val fmt = "%25s"
            out.println("$fmt : '%s'".format("fileType", fileType))
            out.println("$fmt : '%s'".format("version", version))
            out.println("$fmt : %,d".format("languageCount", languageCount))
            out.println("$fmt : %,d".format("localizedStringSize", localizedStringSize))
            out.println("$fmt : %,d".format("entryCount", entryCount))
            out.println("$fmt : %,d".format("offsetToLocalizedString", offsetToLocalizedString))
            out.println("$fmt : %,d".format("offsetToKeyList", offsetToKeyList))
            out.println("$fmt : %,d".format("offsetToResourceList", offsetToResourceList))
            out.println("$fmt : %,d".format("buildYear", buildYear))
            out.println("$fmt : %,d".format("buildDay", buildDay))
            out.println("$fmt : %s".format("buildDate", buildDate.toString()))
            out.println("$fmt : %,d".format("descriptionStrRef", descriptionStrRef))
        }
    } // ErfHeader


    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(ErfFile::class.java.toString())

        fun isModuleFile(path: Path): Boolean {

            when (GffFactory.getFileType(path.name)) {
                "mod", "nwm" -> {}
                else -> return false
            }

            path.inputStream().use { input ->
                return ErfFileType.MOD.strVal == input.readString(4)
            }
        }
    }
}

data class ErfFileEntry(
    val offsetToResource: Long,
    val resourceSize: Long,
    val fileName: String,
    val resourceId: Long,
    val resType: Int
) {
    val fileNameWithExtension: String
        get() = "${fileName}${fileExtension}"

    val fileExtension: String
        get() = FileTypeIdMap[resType]!!.extension

    val fileType: FileType
        get() = FileTypeIdMap[resType]!!
}
