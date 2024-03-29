package neverwintertoolkit.file.gff

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.serde.annotation.Serdeable
import neverwintertoolkit.JsonSettings
import neverwintertoolkit.UByteOne
import neverwintertoolkit.UpperCamelCaseStrategy
import neverwintertoolkit.command.gff.GffOptions
import neverwintertoolkit.command.gff.genFieldCode2
import neverwintertoolkit.extractSigned16
import neverwintertoolkit.getBaseMapper
import neverwintertoolkit.globalSettings
import neverwintertoolkit.model.annotation.BlankBehavior
import neverwintertoolkit.model.annotation.NwnField
import neverwintertoolkit.model.git.HasStructId
import neverwintertoolkit.process16BitToUShort
import neverwintertoolkit.process32BitInt
import neverwintertoolkit.process32BitIntAsLong
import neverwintertoolkit.process32BitUInt
import neverwintertoolkit.process64BitIntAsLong
import neverwintertoolkit.process64BitIntAsULong
import neverwintertoolkit.read32BitNumber
import neverwintertoolkit.readDouble
import neverwintertoolkit.readFloat
import neverwintertoolkit.readNBytes
import neverwintertoolkit.readString
import neverwintertoolkit.readStringNullTerminated
import neverwintertoolkit.readUInt
import neverwintertoolkit.toBytes
import java.io.BufferedInputStream
import java.io.DataInput
import java.io.OutputStream
import java.io.PrintStream
import java.io.RandomAccessFile
import java.lang.Integer.max
import java.lang.Integer.min
import java.nio.file.Path
import javax.imageio.stream.ImageOutputStream
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.io.path.inputStream
import kotlin.io.path.outputStream
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.system.exitProcess

@Serdeable
@Introspected
@ReflectiveAccess
class GffFile constructor(
    val file: Path,
    val gffCommand: GffOptions = GffOptions(),
    val globalOffset: Long = 0L,
    val status: PrintStream = System.out,
    val entryName: String? = null
) {
    init {
        logger.trace("GffFile({})", file)
    }

    val gffHeader = readHeader()

    @Serdeable
    @Introspected
    @ReflectiveAccess
    data class GffHeader(
        var fileType: String,
        var fileVersion: String,
        var structOffset: UInt = 0u,
        var structCount: UInt = 0u,
        var fieldOffset: UInt = 0u,
        var fieldCount: UInt = 0u,
        var labelOffset: UInt = 0u,
        var labelCount: UInt = 0u,
        var fieldDataOffset: UInt = 0u,
        var fieldDataByteCount: UInt = 0u,
        var fieldIndicesOffset: UInt = 0u,
        var fieldIndicesByteCount: UInt = 0u,
        var listIndicesOffset: UInt = 0u,
        var listIndicesByteCount: UInt = 0u,
    ) {
//        val dateStr = "${buildYear + 1900}-${buildDay.inc().toString().padStart(3, '0')}"
//        val buildDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_ORDINAL_DATE)!!
//
//        fun print() {
//            val fmt = "%25s"
//            println("$fmt : '%s'".format("fileType", fileType))
//            println("$fmt : '%s'".format("version", version))
//            println("$fmt : %,d".format("languageCount", languageCount))
//            println("$fmt : %,d".format("localizedStringSize", localizedStringSize))
//            println("$fmt : %,d".format("entryCount", entryCount))
//            println("$fmt : %,d".format("offsetToLocalizedString", offsetToLocalizedString))
//            println("$fmt : %,d".format("offsetToKeyList", offsetToKeyList))
//            println("$fmt : %,d".format("offsetToResourceList", offsetToResourceList))
//            println("$fmt : %,d".format("buildYear", buildYear))
//            println("$fmt : %,d".format("buildDay", buildDay))
//            println("$fmt : %s".format("buildDate", buildDate.toString()))
//            println("$fmt : %,d".format("descriptionStrRef", descriptionStrRef))
//        }
    } // ErfHeader

    @ReflectiveAccess
    @Serdeable
    @Introspected
    @com.fasterxml.jackson.databind.annotation.JsonNaming(UpperCamelCaseStrategy::class)
    data class GffIdString(
        var id: Int? = 0,
        var string: String? = null
    ) {
        constructor(str: String) : this(0, str)

        override fun toString(): String {
            return "IdString(id=$id, string=$string, string.length=${string?.length} string.size=${string?.toByteArray()?.size} ${string?.toByteArray(Charsets.ISO_8859_1)?.size} ${
                string?.toByteArray(
                    Charsets.ISO_8859_1
                )?.size
            })"
        }

    }

    @Serdeable
    @Introspected
    @ReflectiveAccess
    data class GffStruct(
        val structId: Int = 0,
        val type: UInt = 0u,
        val dataOrDataOffset: UInt = 0u,
        val fieldCount: UInt = 0u,
        var fields: MutableList<GffField> = mutableListOf()
    ) {
        @OptIn(ExperimentalEncodingApi::class)
        fun toRawJsonNode(
            mapper: ObjectMapper = getBaseMapper(),
            mapFieldName: (label: String) -> String = ::mapFieldNameDefault
        ): ObjectNode {
            val node: ObjectNode = mapper.createObjectNode()
            node.put("StructId", this.structId)
            this.fields.sortedBy { it.label }.forEach { gffField ->
                val label = mapFieldName(gffField.label)
                when (val data = gffField.dataOrDataOffset) {
//                    is Byte -> node.put(label, data.toInt().toShort())
                    is Int -> node.put(label, data)
                    is Long -> node.put(label, data)
                    is Short -> node.put(label, data)
                    is UInt -> node.put(label, data.toLong())
                    is UByte -> node.put(label, data.toShort())
                    is UShort -> node.put(label, data.toInt())
                    is Double -> node.put(label, data)
                    is Float -> node.put(label, data)
                    is String -> node.put(label, data)
                    is Char -> {
                        System.err.println("char2:$data")
                        System.err.println("char3:" + data.code)
                        node.put(label, data.toString())
                    }

                    is CExoLocString -> {
                        val node2 = mapper.valueToTree<JsonNode>(data)
                        node.set(label, node2)
                    }

                    is List<*> -> {
                        val arrayNode = node.putArray(label)
                        data.forEach {
                            when (it) {
                                is Int -> arrayNode.add(it)
                                is Long -> arrayNode.add(it)
                                null -> arrayNode.addNull()
                                else -> System.err.println("Unexpected $it ${it::class.java.name}")
                            }
                        }
                    }

                    is GffStruct -> {
                        node.putIfAbsent(label, data.toRawJsonNode(mapper))
                    }

                    is ByteArray -> {
//                        val dat = kotlin.io.encoding.Base64.encode(data, 0, data.size)
//                        node.put(label, dat)
                        node.put(label, data)
                    }

                    else -> {
                        System.err.println("Unexpected type 3 " + data + " " + data::class.java.name)
                        RuntimeException("Unexpected type 3 " + data + " " + data::class.java.name).printStackTrace()
                        println()
                    }
                }
            }
            return node
        }

        /*
               fun <K : Any> mapToObject(java: KClass<K>): K {
                   val inst: K = java.createInstance()

                   // annotated "name" is key, value is reflection object
                   val map: Map<String, KMutableProperty<*>> = inst::class.declaredMemberProperties.map { member ->
                       if (member is KMutableProperty<*>) {
                           member.getter.findAnnotation<NwnField>()?.let { two ->
                               Pair(two.name, member)
                           }
                       } else null
                   }.filterNotNull().toMap()

                   this.fields.forEach { gffField ->
                       map[gffField.label]?.let { found: KMutableProperty<*> ->
                           when (gffField.type) {

                               GffFieldType.ResRef,
                               GffFieldType.CExoString -> {
                                   val dat = gffField.dataOrDataOffset as String
                                   if (dat.isNotEmpty()) {
                                       found.setter.call(inst, dat)
                                   }
                               }

                               GffFieldType.CHAR -> TODO()
                               GffFieldType.SHORT -> TODO()
                               GffFieldType.DWORD64 -> TODO()
                               GffFieldType.INT64 -> TODO()
                               GffFieldType.FLOAT -> found.setter.call(inst, gffField.dataOrDataOffset)
                               GffFieldType.DOUBLE -> TODO()

                               // same call in separate blocks for better stack traces
                               GffFieldType.WORD -> found.setter.call(inst, gffField.dataOrDataOffset)
                               GffFieldType.DWORD -> found.setter.call(inst, gffField.dataOrDataOffset)
                               GffFieldType.INT -> found.setter.call(inst, gffField.dataOrDataOffset)
                               GffFieldType.BYTE -> found.setter.call(inst, UByte.MAX_VALUE)
                               GffFieldType.VOID -> found.setter.call(inst, gffField.dataOrDataOffset)
                               GffFieldType.CExoLocString -> found.setter.call(inst, gffField.dataOrDataOffset)
                               GffFieldType.Struct -> TODO()
                               GffFieldType.List -> {
                                   val elementName = found.getter.findAnnotation<NwnField>()!!.elementName
                                   if (elementName.isNotEmpty()) {
                                       val list = gffField.dataOrDataOffset as List<Long>
                                       list.forEach { structId ->
                                           println("elementName=$elementName")
                                       }
                                   }

                               }
       //                        else -> {}
                           }
                       }
                   }

                   return inst
               }
       */
    }

    @Serdeable
    @Introspected
    @ReflectiveAccess
    open class GffField constructor(
        val type: GffFieldType,
        val labelIndex: UInt,
        val label: String,
        val dataOrDataOffset: Any,
        var fieldOrdinalId: Int? = null,
        var size: Int? = null,
        var offset: Long? = null
    ) {
        var arr: ByteArray? = null

        @OptIn(ExperimentalEncodingApi::class)
        override fun toString(): String {
            return "GffField(type = %16s  labelIndex = %3d  label = %16s  dataOrDataOffset = %30s  size=$size  offset=$offset)".format(
                type,
                labelIndex.toLong(),
                label,
                if (dataOrDataOffset is ByteArray) kotlin.io.encoding.Base64.encode(dataOrDataOffset, 0, dataOrDataOffset.size)
                else dataOrDataOffset.toString()
            )
        }
    }

    @Serdeable
    @Introspected
    @ReflectiveAccess
    enum class GffFieldType(val id: Int) {
        BYTE(0),              // UByte
        CHAR(1),
        WORD(2),              // UShort
        SHORT(3),
        DWORD(4),             // UInt
        INT(5),               // Int
        DWORD64(6),
        INT64(7),
        FLOAT(8),             // Float
        DOUBLE(9),            // Double
        CExoString(10),       // String
        ResRef(11),           // String
        CExoLocString(12),    // CExoLocString
        VOID(13),             // ByteArray
        Struct(14),
        List(15);

        companion object {
            private val map = entries.associateBy(GffFieldType::id)

            //            private val strMap = entries.associateBy(GffFieldType::toString)
            fun fromInt(type: Int) = map[type]
//            fun fromString(str: String) = strMap[str]
        }
    }

    private fun readHeader(): GffHeader {
        file.inputStream().buffered().use { input ->
            input.skip(globalOffset)
            return GffHeader(
                fileType = input.readString(4),
                fileVersion = input.readString(4),
                structOffset = input.readUInt(),
                structCount = input.readUInt(),
                fieldOffset = input.readUInt(),
                fieldCount = input.readUInt(),
                labelOffset = input.readUInt(),
                labelCount = input.readUInt(),
                fieldDataOffset = input.readUInt(),
                fieldDataByteCount = input.readUInt(),
                fieldIndicesOffset = input.readUInt(),
                fieldIndicesByteCount = input.readUInt(),
                listIndicesOffset = input.readUInt(),
                listIndicesByteCount = input.readUInt()
            )

//            val fileType = input . readString (4)
//            val fileVersion = input.readString(4)
//            val structOffset = input.readUInt()
//            val structCount = input.readUInt()
//            val fieldOffset = input.readUInt()
//            val fieldCount = input.readUInt()
//            val labelOffset = input.readUInt()
//            val labelCount = input.readUInt()
//            val fieldDataOffset = input.readUInt()
//            val fieldDataCount = input.readUInt()
//            val fieldIndicesOffset = input.readUInt()
//            val fieldIndicesCount = input.readUInt()
//            val listIndecesOffset = input.readUInt()
//            val listIndecesCount = input.readUInt()

//            return GffHeader( )
        }
    }

    val one = Runnable { "" }

    fun one(param: (structId: Int, struct: GffStruct) -> String) {

    }

    fun two(structId: Int, struct: GffStruct): String = "%6d - %s\n".format(structId, struct.toString())

    fun three() {
        one(::two)
        one({ structId, struct ->
            ""
        })
    }

    val x = Runnable { }

    val r = object : Runnable {
        override fun run() {

        }
    }

    val structs: List<GffStruct> by lazy { readStructs() }

    private fun readStructs(): List<GffStruct> {
        file.inputStream().buffered().use { input ->
            input.skip(globalOffset + gffHeader.structOffset.toLong())
            return (0 until gffHeader.structCount.toInt()).map { structId ->
                val struct = input.readStruct(structId)
//                logger.atTrace().log("")
//                logger.atTrace().log(java.util.function.Supplier<String> { "" })
//                logger.atTrace().log(java.util.function.Supplier<String> { })
//                logger.atTrace().log { println("lazy4"); "food %6d - %s\n".format(structId, struct.toString()) }
//                logger.atTrace().log { org.slf4j.spi.Supplier {} }
//                logger.atTrace().log {
//                    java.util.function.Supplier<String> {
//                        ""
//                    }
//                }
                loadStruct(struct)
                struct
            }
        }
    }

//    fun readStruct(structIndex: Long): GffStruct {
//        file.inputStream().buffered().use { input ->
//            input.skip(globalOffset + gffHeader.structOffset.toLong())
//            (0 until gffHeader.structCount.toInt()).forEach {
//                val struct = input.readStruct()
//                if (structIndex.toInt() == it) {
//                    print("%6d - %s\n".format(it, struct.toString()))
//                    return struct
//                }
//                if (struct.fieldCount > 1u) {
//                    readStruct(struct)
//                } else {
//                    println("don't know what to do with this")
//                }
//            }
//        }
//        throw RuntimeException("struct not found structIndex=$structIndex")
//    }

    private fun loadStruct(struct: GffStruct) {
        if (struct.fieldCount < 1u) {
            logger.trace("fieldCount < 1 : loadStruct() struct = {}", struct)
            return
        }

        RandomAccessFile(file.toFile(), "r").use { input ->

            fun procFieldIndex(fieldArrayIndex: UInt) {
                input.seek(globalOffset + (gffHeader.fieldOffset + fieldArrayIndex * 4u * 3u).toLong())
                val field = input.readField()
                struct.fields.add(field)
//                println("               $field")
            }

            if (struct.fieldCount == 1u) {
//                println("one field struct")
                procFieldIndex(struct.dataOrDataOffset)
            } else {
//                println("list struct fieldCount=${struct.fieldCount}")
                input.seek(globalOffset + (gffHeader.fieldIndicesOffset + struct.dataOrDataOffset).toLong())
                val fieldArrayIndices = (0 until struct.fieldCount.toInt()).map {
                    input.readUInt()
                }

                fieldArrayIndices.map { fieldArrayIndex ->
                    procFieldIndex(fieldArrayIndex)
//                input.seek((gffHeader.fieldOffset + fieldArrayIndex * 4u * 3u).toLong())
//                val field = input.readField()
//                val label = getLabel(field.labelIndex)
//                println("               $field; label=$label")
                }
            }
        }
    }

    fun cacheAllLabels() {
        file.inputStream().buffered().use { input ->
            input.skip(globalOffset + gffHeader.labelOffset.toLong())
            repeat(gffHeader.labelCount.toInt()) { index ->
                val str = input.readStringNullTerminated(16)
                labelCache[index.toUInt()] = str
            }
        }
    }

    fun printAllLabels(out: PrintStream = System.out) {
        file.inputStream().buffered().use { input ->
            input.skip(globalOffset + gffHeader.labelOffset.toLong())
            repeat(gffHeader.labelCount.toInt()) { index ->
                val str = input.readStringNullTerminated(16)
                out.println("label $index = $str")
            }
        }
    }

    var labelCache = mutableMapOf<UInt, String>()
        private set

    private fun getLabel(labelIndex: UInt): String {
        val label = labelCache[labelIndex]
        if (label != null)
            return label

        val newLabel = file.inputStream().buffered().use { input ->
            input.skip(globalOffset + (gffHeader.labelOffset + labelIndex * 16u).toLong())
            input.readStringNullTerminated(16)
        }

        labelCache[labelIndex] = newLabel

        return newLabel
    }

    private fun BufferedInputStream.readStruct(structId: Int): GffStruct {
        return GffStruct(
            structId = structId,
            type = this.readUInt(),
            dataOrDataOffset = this.readUInt(),
            fieldCount = this.readUInt()
        )
    }

//    private fun RandomAccessFile.readStruct(structId: Int): GffStruct {
//        return GffStruct(
//            structId = structId,
//            type = this.readUInt(),
//            dataOrDataOffset = this.readUInt(),
//            fieldCount = this.readUInt()
//        )
//    }

    private fun DataInput.readStruct(structId: Int): GffStruct {
        return GffStruct(
            structId = structId,
            type = this.readUInt(),
            dataOrDataOffset = this.readUInt(),
            fieldCount = this.readUInt()
        )
    }

    private fun ByteArray.readField(): GffField {
        val type = try {
            GffFieldType.fromInt(this.process32BitInt()) ?: throw RuntimeException("GffFieldType type not found ${this.process32BitInt()}")
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
        val labelIndex = this.process32BitUInt(4)
        val label = getLabel(labelIndex)
        val dataOrDataOffset = this.copyOfRange(8, 12)
        val dat = readFieldData(type, dataOrDataOffset, label)

        return GffField(
            type = type,
            labelIndex = labelIndex,
            label = label,
            dataOrDataOffset = dat.data,
            size = dat.size,
            offset = dat.offset
        )
    }

    private fun BufferedInputStream.readField(): GffField {
        return this.readNBytes(12).readField()
    }

    private fun RandomAccessFile.readField(): GffField {
        return this.readNBytes(12).readField()
    }

    class FieldData(val data: Any, val size: Int? = null, val offset: Long? = null)

    private fun readFieldData(type: GffFieldType, dataOrDataOffset: ByteArray, label: String): FieldData {
//        println("readFieldData() enter")
        when (type) {
            GffFieldType.BYTE -> return FieldData(dataOrDataOffset[0].toUByte())
            GffFieldType.CHAR -> return FieldData(dataOrDataOffset[0].toUByte().toShort())
//            GffFieldType.CHAR -> {
//                val one = dataOrDataOffset[0]
//                val onea = one.toUByte()
//                val two = onea.toInt()
//                val three = two.toChar()
//                val four = three.code
//                System.err.println("$label: $one | $onea | $two | $three | $four")
//                return three
//                return dataOrDataOffset[0].toInt().toChar()
//            }
            GffFieldType.WORD -> return FieldData(dataOrDataOffset.process16BitToUShort())
            GffFieldType.SHORT -> return FieldData(dataOrDataOffset.extractSigned16())
            GffFieldType.DWORD -> return FieldData(dataOrDataOffset.process32BitUInt())
            GffFieldType.INT -> return FieldData(dataOrDataOffset.process32BitInt())
            GffFieldType.FLOAT -> return FieldData(dataOrDataOffset.readFloat())
            else -> {}
        }

        RandomAccessFile(file.toFile(), "r").use { input: RandomAccessFile ->
            val offset = dataOrDataOffset.process32BitIntAsLong()
            input.seek(globalOffset + gffHeader.fieldDataOffset.toLong() + offset)
//            System.err.println("complex type=${type}, offset=${offset}")
            when (type) {
                GffFieldType.DWORD64 -> {
//                    logger.trace("size=8")
                    return FieldData(input.readNBytes(8).process64BitIntAsULong(), 8, offset) // 64bits
                }

                GffFieldType.INT64 -> {
//                    logger.trace("size=8")
                    return FieldData(input.readNBytes(8).process64BitIntAsLong(), 8, offset) // 64bits
                }

                GffFieldType.DOUBLE -> {
//                    logger.trace("size=8")
                    return FieldData(input.readNBytes(8).readDouble(), 8, offset) // 64bits
                }

                GffFieldType.CExoString -> {
                    val size = input.readNBytes(4).process32BitIntAsLong()
                    logger.trace("CExoString.size={}", { size + 4 })
                    return if (size > 0) {
                        FieldData(input.readString(size.toInt()), size.toInt(), offset)
                    } else {
                        FieldData("", 0, offset)
                    }
                }

                GffFieldType.ResRef -> {
                    val size = input.readByte().toUByte().toInt()
//                    val x = input.readNBytes(1)[0]
//                    val size = x.toUByte().toInt()
//                    logger.trace("ResRef x=$x, size=$size, offset=$offset")
                    logger.trace("ResRef.size={}", { size + 1 })

                    if (size > 16) {
                        System.err.println("ResRef size > 16 at $size")
                    }

                    return if (size == 0)
                        FieldData("", 0, offset)
                    else
                        FieldData(input.readString(size), size, offset)
                }

                GffFieldType.CExoLocString -> {
                    val totalSize = input.readNBytes(4).process32BitIntAsLong()
                    logger.trace("totalSize={}", { totalSize + 4 })
                    val sb = input.readNBytes(4)
                    val stringRef = sb.process32BitIntAsLong()
//                    if (stringRef == 0xFFFFFFFF) { // does not reference dialog.tlk
//                    }
                    val stringCount = input.readNBytes(4).process32BitIntAsLong()
                    val strings = (0 until stringCount).map {
                        val stringId = input.readNBytes(4).process32BitIntAsLong().toInt()
                        val stringLength = input.readNBytes(4).process32BitIntAsLong()
                        val theString = input.readString(stringLength.toInt())
//                        logger.debug("stringNum=$stringNum, stringId=$stringId, theString=$theString")
                        GffIdString(stringId, theString)
                    }
                    return FieldData(CExoLocString(stringRef, strings), totalSize.toInt(), offset)
                }

                GffFieldType.VOID -> {
                    val length = input.readNBytes(4).process32BitIntAsLong()
//                    String encodedString =
//                    Base64.getEncoder().encodeToString(originalInput.getBytes());
                    val byteArray = input.readNBytes(length.toInt())
//                    val one = Base64.encode(byteArray, 0, byteArray.size)
//                    return "void length $length data:$one"
                    return FieldData(byteArray, length.toInt(), offset)
                }

                GffFieldType.Struct -> {
                    return FieldData(GffStruct(structId = offset.toInt()), -1, offset)
                }

                GffFieldType.List -> {
//                    println("offset=$offset")
                    input.seek(globalOffset + gffHeader.listIndicesOffset.toLong() + offset)
                    val listSize = input.readNBytes(4).process32BitIntAsLong()
                    //println("listSize=$listSize")
                    val structIndexes = (0 until listSize).map {
                        input.readNBytes(4).process32BitIntAsLong()
                    }
                    return FieldData(structIndexes, listSize.toInt(), offset)
                }

                else -> throw RuntimeException("unexpected type $type")
            }

//            throw RuntimeException("unexpected type $type")
        }
    }

    fun printAllFields(out: PrintStream = System.out) {
        file.inputStream().buffered().use { input ->
            input.skip(globalOffset + gffHeader.fieldOffset.toLong())
            repeat(gffHeader.fieldCount.toInt()) { index ->
                val field = input.readField()
                out.println("field $index = $field")
            }
        }
    }

    fun printAllFieldIndices(out: PrintStream = System.out) {
        file.inputStream().buffered().use { input ->
            input.skip(globalOffset + gffHeader.fieldIndicesOffset.toLong())
            repeat(gffHeader.fieldIndicesByteCount.toInt() / 4) { index ->
                val field = input.read32BitNumber()
                out.println("fieldIndices $index = $field")
            }
        }
    }

    fun printListIndices(out: PrintStream = System.out) {
        file.inputStream().buffered().use { input ->
            input.skip(globalOffset + gffHeader.listIndicesOffset.toLong())
//            println("numListIndices   = %,9d".format(numListIndices))
            out.println("listIndicesOffset = %,9d".format(gffHeader.listIndicesOffset.toLong()))
            out.println("listIndicesBytes  = %,9d".format(gffHeader.listIndicesByteCount.toLong()))

            var listNum = 0
            var bytes = 0
            while (bytes < gffHeader.listIndicesByteCount.toInt()) {
                val numListIndices = input.read32BitNumber().toInt()
                bytes += 4
                val theList = (0 until numListIndices).map {
                    bytes += 4
                    input.read32BitNumber()
                }
//                println("bytes=$bytes list num $listNum = $theList ")
                out.println("list num $listNum = $theList")
                listNum++
            }
        }
    }

    fun printHeader(out: PrintStream = System.out) {
//        fun printHeader(out: PrintStream = System.out) {
        out.println("$gffHeader")
        out.flush()
    }

    fun dump(file: Path) {
        PrintStream(file.outputStream()).use {
            dump(it)
        }
    }

    fun dump(out: PrintStream = gffCommand.status) {
        println(file)
        printHeader(out)
        cacheAllLabels()
        printStructs(structs, out)

        if (gffCommand.debugEnabled) {
            out.println()
            out.println()
            printAllLabels(out)

            out.println()
            out.println()
            printAllFields(out)

            out.println()
            out.println()
            printAllFieldIndices(out)

            out.println()
            out.println()
            printListIndices(out)
        }
    }

    fun dumpStructs(out: PrintStream = System.out) {
        cacheAllLabels()
        val set = mutableSetOf<Pair<String, Int>>()
        structs.forEach { struct ->
            struct.fields.forEach { field ->
                when (field.type) {
                    GffFieldType.List -> {
                        @Suppress("UNCHECKED_CAST")
                        val structIndexes: List<Long> = field.dataOrDataOffset as List<Long>
                        structIndexes.forEach { strInd ->
                            set.add(Pair(field.label, structs[strInd.toInt()].type.toInt()))
                        }
                    }

                    GffFieldType.Struct -> {}
                    else -> {}
                }

            }

        }
        set.toList().sortedBy { it.second }.sortedBy { it.first }.forEach {
            out.printf("%s %d\n", it.first, it.second)
        }
    }

    private fun printStructs(list: List<GffStruct>, out: PrintStream = System.out) {

        fun printStruct(index: Int, out: PrintStream = System.out) {
            val struct = list[index]
            if (gffCommand.mOption) {
                out.println(
                    "%3d - GffStruct(type=%s, fieldCount: %d)".format(
                        index,
                        struct.type,
                        struct.fieldCount.toLong()
                    )
                )
            } else {
                out.println(
                    "%3d - GffStruct(type=%s, dataOrDataOffset=%s, fieldCount: %d)".format(
                        index,
                        struct.type,
                        struct.dataOrDataOffset,
                        struct.fieldCount.toLong()
                    )
                )
            }
            if (!gffCommand.nOption) {
                struct.fields.forEach {
                    out.println("%8s %s".format("", it))
                }
            }
        }

        val dumpList = (gffCommand.structList ?: "0-end").replace("end", (list.size - 1).toString())

        dumpList.split(",").map { term ->
            term.toProgression(list.size - 1).map { index ->
                printStruct(index, out)
            }
        }
    }

    private fun getFactory(): GffFactory<out GffObj> {
        return GffFactory.getFactoryForType(gffHeader.fileType)//?.also { println("factory = ${gffHeader.fileType} = " + it::class.java.name) }
            ?: throw RuntimeException("unexpected type '${gffHeader.fileType}'")
    }

    fun readObject(): GffObj {
        return this.getFactory().readGff(this)
    }

//    fun getMapper(jsonSettings: JsonSettings = JsonSettings()): ObjectMapper {
//        return this.getFactory().getMapper(jsonSettings)
//    }

    fun toRawJson(jsonSettings: JsonSettings = JsonSettings()): String {
        return structs.toRawJson(getFactory().getMapper(jsonSettings))
    }

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(GffFile::class.java.toString())
        lateinit var ous: ImageOutputStream

        fun writeHeader(out: OutputStream, header: GffHeader) {

            header.apply {
                out.write(fileType.toByteArray(Charsets.ISO_8859_1))
                out.write(fileVersion.toByteArray(Charsets.ISO_8859_1))
                out.write(structOffset.toBytes())
                out.write(structCount.toBytes())
                out.write(fieldOffset.toBytes())
                out.write(fieldCount.toBytes())
                out.write(labelOffset.toBytes())
                out.write(labelCount.toBytes())
                out.write(fieldDataOffset.toBytes())
                out.write(fieldDataByteCount.toBytes())
                out.write(fieldIndicesOffset.toBytes())
                out.write(fieldIndicesByteCount.toBytes())
                out.write(listIndicesOffset.toBytes())
                out.write(listIndicesByteCount.toBytes())
            }
        }

        fun writeStruct(out: OutputStream, struct: GffStruct) {
            out.write(struct.type.toBytes())
            out.write(struct.dataOrDataOffset.toBytes())
            out.write(struct.fieldCount.toBytes())
        }

    }

    @OptIn(ExperimentalStdlibApi::class)
    fun <K : Any> mapToObject(structId: Int, java: KClass<K>): K {
        val struct0 = structs[structId]
        val inst: K =
            try {
                java.createInstance()
            } catch (e: Exception) {
                throw RuntimeException("Error creating instance for $file class=$java structId=$structId", e)
            }

        // annotated "name" is key, value is reflection object
        val map: Map<String, Pair<KMutableProperty<*>, NwnField>> = inst::class.declaredMemberProperties.mapNotNull { member ->
            if (member is KMutableProperty<*>) {
                member.getter.findAnnotation<NwnField>()?.let { two ->
                    Pair(two.name, Pair(member, two))
                }
            } else null
        }.toMap()

        struct0.fields.forEach { gffField ->
            map[gffField.label]?.let { (found: KMutableProperty<*>, two: NwnField) ->
                when (gffField.type) {

                    GffFieldType.ResRef,
                    GffFieldType.CExoString -> {
                        val dat = gffField.dataOrDataOffset as String?
                        if (dat?.isNotEmpty() == true || two.blankBehavior == BlankBehavior.RETAIN) {
                            try {
                                found.setter.call(inst, dat ?: "")
                            } catch (e: Exception) {
                                val className = if (dat == null) "null" else dat::class.java.name
                                status.println("error mapping object $file, structId=$structId, ${found.getter.returnType.toString()}, $dat $className")
                                e.printStackTrace(status)
                            }
                        }
                    }

                    GffFieldType.CHAR -> TODO()
                    GffFieldType.SHORT -> found.setter.call(inst, gffField.dataOrDataOffset)
                    GffFieldType.DWORD64 -> TODO()
                    GffFieldType.INT64 -> TODO()
                    GffFieldType.FLOAT -> found.setter.call(inst, gffField.dataOrDataOffset)
                    GffFieldType.DOUBLE -> TODO()

                    // same call in separate blocks for better stack traces
                    GffFieldType.WORD -> found.setter.call(inst, gffField.dataOrDataOffset)
                    GffFieldType.DWORD -> found.setter.call(inst, gffField.dataOrDataOffset)
                    GffFieldType.INT -> found.setter.call(inst, gffField.dataOrDataOffset)
                    GffFieldType.BYTE -> {
                        if (found.getter.returnType.classifier == Boolean::class) {
                            found.setter.call(inst, gffField.dataOrDataOffset == UByteOne)
                        } else {
                            found.setter.call(inst, gffField.dataOrDataOffset)
                        }
                    }

                    GffFieldType.VOID -> found.setter.call(inst, gffField.dataOrDataOffset)
                    GffFieldType.CExoLocString -> found.setter.call(inst, gffField.dataOrDataOffset)
                    GffFieldType.Struct -> {
                        // TODO this is stupid, but I can't figure how to do it properly with the API
                        val className = found.getter.returnType.toString().replace("?", "")
                        val theClass = Class.forName(className).kotlin
                        val struct = gffField.dataOrDataOffset as GffStruct
                        val obj = mapToObject(struct.structId, theClass)
                        found.setter.call(inst, obj)
                    }

                    GffFieldType.List -> {

                        /*
                         * field type of List<String> with String for mapped value
                         */
                        if (gffField.dataOrDataOffset is List<*>) {
                            if (gffField.dataOrDataOffset.isEmpty()) {
                                if (two.blankBehavior == BlankBehavior.RETAIN) {
                                    found.setter.call(inst, emptyList<Number>())
                                }
                            } else if (gffField.dataOrDataOffset.first() is Number) {
                                @Suppress("UNCHECKED_CAST")
                                val list = gffField.dataOrDataOffset as List<Number>


                                val nwnField: NwnField = found.getter.findAnnotation<NwnField>()!!

                                // TODO this is stupid, but I can't figure how to do it properly with the API
                                val a99 = found.getter.returnType.toString()
                                val className = a99.substring(a99.indexOf('<') + 1, a99.indexOf('>'))

                                val alist =
                                    if (nwnField.elementName.isNotBlank() && nwnField.elementType.isNotBlank() && (className == "String" || className == "kotlin.String")) {
                                        list.map { number ->
                                            val first = structs[number.toInt()].fields.first().dataOrDataOffset as String
                                            first
                                        }
                                    } else {
                                        val theClass = Class.forName(className).kotlin

                                        list.map { number ->
                                            mapToObject(number.toInt(), theClass).also { x ->
                                                if (x is HasStructId) {
                                                    x.structId = structs[number.toInt()].type
                                                }
                                            }
                                        }
                                    }
                                found.setter.call(inst, alist)
                            }
                        }
                    }
                }
            } ?: run {
                val msg = "field not found ${gffField.label} on class ${inst::class.java.name} $gffField for " +
                        if (entryName != null) "'${entryName}' in " else {
                            ""
                        } +
                        "'${file}' ${gffField.genFieldCode2()}"
                System.err.println(msg)
                if (globalSettings.strictTestingMode)
                    throw RuntimeException(msg)
            }
        }

        return inst
    }

}

private val redact = "[_ ]".toRegex()

fun mapFieldNameDefault(label: String): String {
    return label.replace(redact, "")
        .replace("ID", "Id")
}

fun List<GffFile.GffStruct>.toRawJson(
    mapper: ObjectMapper = getBaseMapper(),
    mapFieldName: (label: String) -> String = ::mapFieldNameDefault
): String {
    return mapper.writeValueAsString(this.toRawJsonNode(mapper, mapFieldName))
}

fun List<GffFile.GffStruct>.toRawJsonNode(
    mapper: ObjectMapper = getBaseMapper(),
    mapFieldName: (label: String) -> String = ::mapFieldNameDefault
): JsonNode {
    val root = mapper.createArrayNode()
    this.forEach { gffStruct ->
        root.add(gffStruct.toRawJsonNode(mapper, mapFieldName))
    }

    return root
}

fun String.toProgression(max: Int): IntProgression {
    val regex = "(\\d+)-(\\d+)".toRegex()
    val result = regex.find(this) ?: return this.toInt()..this.toInt()

    val (startStr, endStr) = result.destructured
    val start = max(0, min(startStr.toInt(), max))
    val end = max(0, min(endStr.toInt(), max))

    return if (end < start)
        (start downTo end)
    else
        start..end
}
