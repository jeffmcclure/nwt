package neverwintertoolkit.file.gff

import neverwintertoolkit.toBytes
import neverwintertoolkit.toBytesWORD
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import kotlin.io.path.outputStream
import java.nio.file.Path

abstract class GffWriterBase() {
    protected val fieldIndices = mutableListOf<Int>()
    protected val listIndices = mutableListOf<Int>()
    protected val fields = mutableListOf<GffFile.GffField>()
    protected val labels = mutableMapOf<String, Int>()
    protected val structs = mutableListOf<GffFile.GffStruct>()
    protected val fieldData = ByteArrayOutputStream()

    open fun writeGff(file: Path) {
        file.outputStream().use { out ->
            writeGff(out)
        }
    }

    open fun writeGff(out: OutputStream) {
        logger.trace("writeDlg() enter")
        /*
        GFF File Structure:
            header
            struct array
            field array
            label array
            field data block
            field indices array
            list indices array
         */
        generateStructs()
        generateFieldData()
        writeHeader(out)
        writeStructs(out)
        writeFieldArray(out)
        writeLabelArray(out)
        writeFieldDataBlock(out)
        writeFieldIndicesArray(out)
        writeListIndicesArray(out)
    }

    protected fun writeStructs(out: OutputStream) {
        logger.trace("writeStructs() writing structs")
        structs.forEach { struct2 ->
            GffFile.writeStruct(out, struct2)
        }
        logger.trace("writeStructs() writing return, structs={}", structs.size)
    }

    abstract fun createHeader(): GffFile.GffHeader

    open fun writeHeader(out: OutputStream) {
        //        @Suppress("SameParameterValue")
        val header = createHeader()
        header.apply {
            structOffset = 56u
            structCount = structs.size.toUInt()
            fieldOffset = structOffset + structCount * 12u
            fieldCount = fields.size.toUInt()
            labelOffset = fieldOffset + fieldCount * 12u
            labelCount = labels.size.toUInt()
            fieldDataOffset = labelOffset + labelCount * 16u
            fieldDataByteCount = fieldData.size().toUInt()
            fieldIndicesOffset = fieldDataOffset + fieldDataByteCount
            fieldIndicesByteCount = fieldIndices.size.toUInt() * 4u
            listIndicesOffset = fieldIndicesOffset + fieldIndicesByteCount
            listIndicesByteCount = listIndices.size.toUInt() * 4u
        }

        logger.trace("writing {}", header)
        GffFile.writeHeader(out, header)
    }

    abstract fun generateStructs()

    protected fun writeFieldArray(out: OutputStream) {
        fields.forEach { field ->
            out.write(field.type.id.toUInt().toBytes())
            out.write(field.labelIndex.toBytes())
            if (field.arr!!.size != 4) {
                throw RuntimeException("arr.size != 4")
            }
            out.write(field.arr!!)
        }
    }

    protected fun writeLabelArray(out: OutputStream) {
        labels.entries.toList().sortedBy { it.value }.forEach {
            val arr = it.key.padEnd(16, 0.toChar()).toByteArray(Charsets.ISO_8859_1)
            if (arr.size != 16) {
                throw RuntimeException("label size != 16 for ${it.key}")
            }
            out.write(it.key.padEnd(16, 0.toChar()).toByteArray(Charsets.ISO_8859_1))
        }
    }

    protected fun writeFieldDataBlock(out: OutputStream) {
        out.write(fieldData.toByteArray())
    }

    protected fun writeFieldIndicesArray(out: OutputStream) {
        fieldIndices.forEach { out.write(toBytes(it)) }
    }

    protected fun writeListIndicesArray(out: OutputStream) {
        listIndices.forEach { out.write(toBytes(it)) }
    }

    /**
     * @return null when not a DWORD
     */
    private fun GffFile.GffField.dataToByteArray(): ByteArray {
        val arr = ByteArray(4)
        when (type) {
            GffFile.GffFieldType.BYTE -> when (dataOrDataOffset) {
                is Int -> arr[0] = (dataOrDataOffset.toByte())
                is Byte -> arr[0] = (dataOrDataOffset)
                is UByte -> arr[0] = (dataOrDataOffset).toByte()
                is Short -> arr[0] = (dataOrDataOffset).toByte()
                else -> throw RuntimeException("Unexpected")
            }

            GffFile.GffFieldType.CHAR -> arr[0] = (dataOrDataOffset as Char).code.toByte()
            GffFile.GffFieldType.WORD -> {
                when (dataOrDataOffset) {
//                    is UInt -> toBytesWORD(arr, dataOrDataOffset)
                    is UShort -> toBytesWORD(arr, dataOrDataOffset)
                    else -> throw RuntimeException("unexpected")
                }
            }

            GffFile.GffFieldType.SHORT -> toBytes(arr, dataOrDataOffset as Short)
            GffFile.GffFieldType.DWORD -> when (dataOrDataOffset) {
                is UInt -> dataOrDataOffset.toBytes(arr)
                is Int -> toBytes(dataOrDataOffset, arr)
                is Long -> dataOrDataOffset.toUInt().toBytes(arr)
            }

            GffFile.GffFieldType.INT -> toBytes(dataOrDataOffset as Int, arr)
            GffFile.GffFieldType.FLOAT -> toBytes(arr, dataOrDataOffset as Float)

            GffFile.GffFieldType.DWORD64 -> {
                toBytes(fieldData.size(), arr)
                val dat = dataOrDataOffset as ULong
                val arr1 = toBytes(dat)
                fieldData.write(arr1)
            }

            GffFile.GffFieldType.INT64 -> {
                toBytes(fieldData.size(), arr)
                val dat = dataOrDataOffset as Long
                val arr1 = toBytes(dat)
                fieldData.write(arr1)
            }

            GffFile.GffFieldType.DOUBLE -> {
                toBytes(fieldData.size(), arr)
                val dat = dataOrDataOffset as Double
                val ln = dat.toBits()
                fieldData.write(toBytes(ln))
            }

            GffFile.GffFieldType.CExoString -> {
                toBytes(fieldData.size(), arr)
                val ar2 = (dataOrDataOffset as String).toByteArray(Charsets.ISO_8859_1)

                fieldData.write(toBytes(ar2.size))
                fieldData.write(ar2)
            }

            GffFile.GffFieldType.ResRef -> {
                toBytes(fieldData.size(), arr)
                val ar2 = (dataOrDataOffset as String).toByteArray(Charsets.ISO_8859_1)

                if (ar2.size > 16) throw RuntimeException("CResRef size should not exceed 16, value=${ar2.size}")

                fieldData.write(byteArrayOf(ar2.size.toByte()))
                fieldData.write(ar2)
            }

            GffFile.GffFieldType.CExoLocString -> {
                toBytes(fieldData.size(), arr)
                val dat = when (dataOrDataOffset) {
                    is String -> {
                        CExoLocString(UInt.MAX_VALUE.toLong(), listOf(GffFile.GffIdString(0, dataOrDataOffset)))
                    }

                    is CExoLocString -> dataOrDataOffset
                    else -> throw RuntimeException("unexpected class ${dataOrDataOffset::class.java}")
                }
                val size = dat.strings.sumOf { 8 + (it.string ?: "").length } + 8
                fieldData.write(toBytes(size))
                fieldData.write((dat.stringRef?.toUInt() ?: UInt.MAX_VALUE).toBytes())
                fieldData.write(toBytes(dat.strings.size))
                dat.strings.forEach {
                    fieldData.write(toBytes(it.id!!))
                    val ar3 = (it.string ?: "").toByteArray(Charsets.ISO_8859_1)
                    fieldData.write(toBytes(ar3.size))
                    fieldData.write(ar3)
                }
            }

            GffFile.GffFieldType.List -> {
                if (dataOrDataOffset !is List<*>) throw RuntimeException("unexpected type ${dataOrDataOffset::class.java.name}")
                toBytes(listIndices.size * 4, arr)

                dataOrDataOffset.firstOrNull()?.let {
                    if (it !is Number) {
                        throw RuntimeException("Encountered list of ${it::class.java.name}, label=${this.label}")
                    }
                }

                @Suppress("UNCHECKED_CAST") // we always expect List<Number> - if something else we need an exception anyway
                val dat = (dataOrDataOffset as List<Number>)
                listIndices.add(dat.size)
                // TODO add lists of non numbers,  e.g. Feat class
                listIndices.addAll(dat.map { it.toInt() })
            }

            GffFile.GffFieldType.VOID -> {
                toBytes(fieldData.size(), arr)
                val dat = dataOrDataOffset as ByteArray
                fieldData.write(dat.size.toUInt().toBytes()) // size in 4 bytes
                fieldData.write(dat) // followed by data
            }

            GffFile.GffFieldType.Struct -> {
                val one = dataOrDataOffset as GffFile.GffStruct
                toBytes(one.structId, arr)
            }
        }

        return arr
    }

    protected fun addFields(list: List<GffFile.GffField>) {
        list.forEach {
            addField(it)
        }
    }

    protected fun addFieldsAndIndices(list: List<GffFile.GffField>) {
        list.forEach {
            addField(it)
        }
        fieldIndices.addAll(list.mapNotNull { it.fieldOrdinalId })
    }

    protected fun addField(field: GffFile.GffField): GffFile.GffField {
        field.fieldOrdinalId = fields.size
        fields.add(field)
        return field
    }

    protected fun getLabelId(fieldName: String): Int {
        return labels[fieldName] ?: run {
            val fieldId = labels.size
            labels[fieldName] = fieldId
            fieldId
        }.also {
            logger.trace("getLabelId({}) = {}", fieldName, it)
        }
    }

//    protected fun addFieldINT(num: Int, fieldName: String, type: GffFile.GffFieldType): GffFile.GffField {
//        return createFieldINT(num, fieldName).also {
//            fields.add(it)
//        }
//    }

    protected fun createFieldINT(num: Int?, fieldName: String): GffFile.GffField? {
        val num = num ?: return null
        return GffFile.GffField(
            type = GffFile.GffFieldType.INT,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = num,
        )
    }

//    protected fun createFieldByte(num: Short?, fieldName: String): GffFile.GffField? {
//        val num = num ?: return null
//        return GffFile.GffField(
//            type = GffFile.GffFieldType.BYTE,
//            labelIndex = getLabelId(fieldName).toUInt(),
//            label = fieldName,
//            dataOrDataOffset = num,
//        )
//    }

    protected fun createFieldByte(num: UByte?, fieldName: String): GffFile.GffField? {
        val num = num ?: return null
        return GffFile.GffField(
            type = GffFile.GffFieldType.BYTE,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = num,
        )
    }

    protected fun createFieldShort(num: Short?, fieldName: String): GffFile.GffField? {
        val num = num ?: return null
        return GffFile.GffField(
            type = GffFile.GffFieldType.SHORT,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = num,
        )
    }

//    protected fun createField(num: Long?, fieldName: String, type: GffFile.GffFieldType): GffFile.GffField {
//        val newNum = num ?: if (fieldName == "DelayEntry") 0 else if (fieldName == "AnimLoop") 1 else 0
//        return GffFile.GffField(
//            type = type,
//            labelIndex = getLabelId(fieldName).toUInt(),
//            label = fieldName,
//            dataOrDataOffset = newNum,
//        )
//    }

    protected fun createFieldFloat(num: Float?, fieldName: String): GffFile.GffField? {
        val newNum = num ?: return null
        return GffFile.GffField(
            type = GffFile.GffFieldType.FLOAT,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = newNum,
        )
    }

    protected fun createFieldDouble(num: Double?, fieldName: String): GffFile.GffField? {
        val newNum = num ?: return null
        return GffFile.GffField(
            type = GffFile.GffFieldType.DOUBLE,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = newNum,
        )
    }

    protected fun createField(num: UInt?, fieldName: String, type: GffFile.GffFieldType): GffFile.GffField? {
        if (type != GffFile.GffFieldType.WORD && type != GffFile.GffFieldType.DWORD) throw RuntimeException("UInt must be WORD or DWORD not $type")

        val newNum = num ?: return null
        return GffFile.GffField(
            type = type,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = newNum,
        )
    }

    protected fun createFieldWORD(num: UShort?, fieldName: String): GffFile.GffField? {
        val num = num ?: return null
        return GffFile.GffField(
            type = GffFile.GffFieldType.WORD,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = num
        )
    }

    protected fun createFieldDWORD(num: UInt?, fieldName: String): GffFile.GffField? {
        val num = num ?: return null
        return GffFile.GffField(
            type = GffFile.GffFieldType.DWORD,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = num
        )
    }

    protected fun createFieldVoid(num: ByteArray?, fieldName: String): GffFile.GffField? {
        val num = num ?: return null
        return GffFile.GffField(
            type = GffFile.GffFieldType.VOID,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = num
        )
    }


//    protected fun createField(yesOrNo: Boolean?, fieldName: String, type: GffFile.GffFieldType): GffFile.GffField {
//        val newNum = if (yesOrNo == true) 1 else 0
//        val labelId = getLabelId(fieldName)
//        return GffFile.GffField(
//            type = type,
//            labelIndex = getLabelId(fieldName).toUInt(),
//            label = fieldName,
//            dataOrDataOffset = newNum,
//            fieldOrdinalId = fields.size
//        )
//    }

    protected fun createFieldByteBoolean(yesOrNo: Boolean?, fieldName: String): GffFile.GffField {
        val newNum = if (yesOrNo == true) 1 else 0
        return GffFile.GffField(
            type = GffFile.GffFieldType.BYTE,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = newNum,
            fieldOrdinalId = fields.size
        )
    }

    protected fun createFieldBlank(): GffFile.GffField {
        return GffFile.GffField(
            type = GffFile.GffFieldType.VOID,
            labelIndex = 0u,
            label = "",
            dataOrDataOffset = "",
        )
    }

//    protected fun addField(str: String?, fieldName: String, type: GffFile.GffFieldType): GffFile.GffField {
//        return createField(str, fieldName, type).also {
//            it.fieldOrdinalId = fields.size
//            fields.add(it)
//        }
//    }

    protected fun createFieldResRef(str: String?, fieldName: String): GffFile.GffField? {
        val str = str ?: return null
        return GffFile.GffField(
            type = GffFile.GffFieldType.ResRef,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = str ?: "",
        )
    }

    protected fun createFieldCExoString(str: String?, fieldName: String): GffFile.GffField? {
        val str = str ?: return null
        return GffFile.GffField(
            type = GffFile.GffFieldType.CExoString,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = str
        )
    }

    protected fun createFieldString(str: String?, fieldName: String, type: GffFile.GffFieldType): GffFile.GffField {
        return GffFile.GffField(
            type = type,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = str ?: "",
        )
    }

//    protected fun addField(dat: CExoLocString?, fieldName: String): GffFile.GffField {
//        return createField(dat, fieldName).also {
//            fields.add(it)
//        }
//    }

    protected fun createField(dat: CExoLocString?, fieldName: String): GffFile.GffField? {
        val dat = dat ?: return null
//        if (dat == null || dat.isBlank) return null
        return GffFile.GffField(
            type = GffFile.GffFieldType.CExoLocString,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = dat ?: CExoLocString(UInt.MAX_VALUE.toLong(), emptyList()),
        )
    }

    protected fun createFieldList(num: List<*>, fieldName: String, fieldOrdinalId: Int? = null): GffFile.GffField {
        return GffFile.GffField(
            type = GffFile.GffFieldType.List,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = num,
        )
    }

    protected fun createFieldStruct(struct: GffFile.GffStruct, fieldName: String, fieldOrdinalId: Int? = null): GffFile.GffField {
        return GffFile.GffField(
            type = GffFile.GffFieldType.Struct,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = struct,
        )
    }

    protected fun createField(map: Map<String, Any>, @Suppress("SameParameterValue") fieldName: String): GffFile.GffField {

        // add struct for each name/value pair
        val structIdList = map.entries.mapIndexed { index, entry ->
            val newStruct = GffFile.GffStruct(
                structId = structs.size,
                type = index.toUInt(),
                dataOrDataOffset = fieldIndices.size.toUInt() * 4u,
                fieldCount = 2u
            )
            val newFields = listOf(
                createFieldString(entry.key, "Key", GffFile.GffFieldType.CExoString),
                createFieldString(entry.value.toString(), "Value", GffFile.GffFieldType.CExoString)
            )
            addFieldsAndIndices(newFields)
            structs.add(newStruct)
            newStruct.structId
        }

        return GffFile.GffField(
            type = GffFile.GffFieldType.List,
            labelIndex = getLabelId(fieldName).toUInt(),
            label = fieldName,
            dataOrDataOffset = structIdList,
        )
    }

    protected fun generateFieldData() {
        fields.forEach { field ->
            field.arr = field.dataToByteArray()
        }
    }

    protected fun addFieldList(num: List<*>, fieldName: String): GffFile.GffField {
        return createFieldList(num, fieldName).also {
            it.fieldOrdinalId = fields.size
            fields.add(it)
        }
    }

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(GffWriterBase::class.java)
    }

    fun fieldListToStruct(fieldList: List<GffFile.GffField>, structId: Int? = null, structType: UInt): GffFile.GffStruct {

        val newStruct = GffFile.GffStruct(
            structId = structId ?: structs.size,
            type = structType,
            dataOrDataOffset = if (fieldList.size > 1) {
                fieldIndices.size.toUInt() * 4u
            } else if (fieldList.size == 1) {
                fields.size.toUInt()
            } else 0u,
            fieldCount = fieldList.size.toUInt()
        )

        if (structId != null) {
            structs[structId] = newStruct
        } else {
            structs.add(newStruct)
        }

        addFields(fieldList)

        if (fieldList.size > 1)
            fieldIndices.addAll(fieldList.map { it.fieldOrdinalId!! })

        return newStruct
    }

}