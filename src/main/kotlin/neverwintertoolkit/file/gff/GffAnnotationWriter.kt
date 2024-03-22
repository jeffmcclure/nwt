package neverwintertoolkit.file.gff

import neverwintertoolkit.globalSettings
import neverwintertoolkit.model.annotation.BlankBehavior
import neverwintertoolkit.model.annotation.NwnField
import neverwintertoolkit.model.dlg.DlgReply
import neverwintertoolkit.model.git.HasStructId
import java.io.OutputStream
import java.nio.file.Path
import kotlin.io.path.outputStream
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

open class GffAnnotationWriter constructor(val gffObj: GffObj) : GffWriterBase() {

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(GffAnnotationWriter::class.java)
    }

    fun writeIfo(file: Path) {
        file.outputStream().use { out ->
            writeIfo(out)
        }
    }

    fun writeIfo(output: OutputStream) {
        writeGff(output)
    }

    override fun createHeader(): GffFile.GffHeader = GffFile.GffHeader(
        fileType = "IFO ",
        fileVersion = "V3.2"
    )

    override fun generateStructs() {
        if (structs.isNotEmpty()) throw RuntimeException("structs is not empty")

        structs.add(GffFile.GffStruct()) // placeholder

        val fieldList = gffFields(gffObj)
        structs[0] = fieldListToStruct(fieldList, 0, UInt.MAX_VALUE)
    }

    private fun gffFields(obj1: Any): List<GffFile.GffField> {
        val map: Map<String, Pair<KMutableProperty<*>, NwnField>> = obj1::class.declaredMemberProperties.map { member ->
            if (member is KMutableProperty<*>) {
                member.getter.findAnnotation<NwnField>()?.let { two ->
                    Pair(two.name, Pair(member, two))
                }
            } else null
        }.filterNotNull().toMap()

        if (globalSettings.simplifyJson && obj1 is DlgReply) {
            obj1.populateDefaultValues()
        }

        val fieldList = map.values.sortedBy { it.second.gffOrder }.mapNotNull { (value: KMutableProperty<*>, nwnField: NwnField) ->
            val nwnField = value.getter.findAnnotation<NwnField>()!!

            // this is need for multi-type Value variable
            if (nwnField.type.isEmpty()) {
                val atype = map["Type"]!!.first.getter.call(obj1) as UInt
                val theval = value.getter.call(obj1)
                when {
                    // 1u == INT
                    atype == 1u && theval is Int -> {
                        createFieldINT(theval, nwnField.name)
                    }

                    // 3u == CExoString
                    atype == 3u && theval is String -> {
                        createFieldCExoString(theval, nwnField.name)
                    }

                    // 2u == FLOAT
                    atype == 2u && theval is Float -> {
                        createFieldFloat(theval, nwnField.name)
                    }

                    // 2u == FLOAT jackson read value from json as Double
                    atype == 2u && theval is Double -> {
                        createFieldFloat(theval.toFloat(), nwnField.name)
                    }

                    theval is String -> {
                        createFieldCExoString(theval, nwnField.name)
                    }

                    theval == null -> {
                        null
                    }

                    else -> {
                        TODO("$atype $theval")
                    }
                }
//                when (val uknown = value.getter.call(obj1)) {
//                    is Int -> {
//                        createFieldINT(uknown, nwnField.name)
//                    }
//
//                    else -> {
//                        println("here")
//                        TODO()
//                    }
//                }
            } else
                when (val type = GffFile.GffFieldType.valueOf(nwnField.type)) {
                    GffFile.GffFieldType.BYTE -> {
//                        val theVal: UByte? = value.getter.call(obj1) as UByte?
//                        val theVal2: UByte? = if (theVal == null && nwnField.blankBehavior == BlankBehavior.DEFAULT_VALUE)
//                            nwnField.defaultValue.toUByte()
//                        else theVal
//                        val className = value.getter.returnType.toString()
                        val returnType = value.getter.returnType
//                        val className2 = value.getter.returnType.toString().replace("?", "")
                        if (returnType.classifier == Boolean::class) {
                            val theVal3: Boolean? = value.getter.call(obj1) as Boolean?
                                ?: if (nwnField.blankBehavior == BlankBehavior.DEFAULT_VALUE) nwnField.defaultValue.toBoolean() else null
                            createFieldByteBoolean(theVal3, nwnField.name)
                        } else {
                            val theVal3: UByte? = value.getter.call(obj1) as UByte?
                                ?: if (nwnField.blankBehavior == BlankBehavior.DEFAULT_VALUE) nwnField.defaultValue.toUByte() else null

                            createFieldByte(theVal3, nwnField.name)
                        }
                    }

                    GffFile.GffFieldType.CHAR -> {
                        TODO(nwnField.type)
                    }

                    GffFile.GffFieldType.WORD -> createFieldWORD(value.getter.call(obj1) as UShort?, nwnField.name)

                    GffFile.GffFieldType.SHORT -> createFieldShort(value.getter.call(obj1) as Short?, nwnField.name)

                    GffFile.GffFieldType.DWORD -> createFieldDWORD(value.getter.call(obj1) as UInt?, nwnField.name)

                    GffFile.GffFieldType.INT -> createFieldINT(value.getter.call(obj1) as Int?, nwnField.name)

                    GffFile.GffFieldType.DWORD64 -> {
                        TODO(nwnField.type)
                    }

                    GffFile.GffFieldType.INT64 -> {
                        TODO(nwnField.type)
                    }

                    GffFile.GffFieldType.FLOAT -> createFieldFloat(value.getter.call(obj1) as Float?, nwnField.name)

                    GffFile.GffFieldType.DOUBLE -> createFieldDouble(value.getter.call(obj1) as Double?, nwnField.name)

                    GffFile.GffFieldType.CExoString -> createFieldCExoString(
                        (value.getter.call(obj1) as String?) ?: if (nwnField.blankBehavior == BlankBehavior.GENERATE) "" else null,
                        nwnField.name
                    )

                    GffFile.GffFieldType.ResRef -> createFieldResRef(
                        (value.getter.call(obj1) as String?) ?: if (nwnField.blankBehavior == BlankBehavior.GENERATE) "" else null,
                        nwnField.name
                    )

                    GffFile.GffFieldType.CExoLocString -> createField(value.getter.call(obj1) as CExoLocString?, nwnField.name)

                    GffFile.GffFieldType.VOID -> createFieldVoid(value.getter.call(obj1) as ByteArray?, nwnField.name)

                    GffFile.GffFieldType.Struct -> {
                        val aval = value.getter.call(obj1)
                        aval?.toStruct(nwnField.structType.toUInt())?.let { newStruct ->
                            createFieldStruct(newStruct, nwnField.name)
                        }
                    }

                    GffFile.GffFieldType.List -> {
                        val vals = value.getter.call(obj1) as List<*>?
                        if (vals is List<*>) {
                            if (vals.isEmpty()) {
                                createFieldList(listOf<Int>(), nwnField.name)
                            } else {
                                createFieldList(vals.filterNotNull().mapIndexed { index, one ->
                                    val finalIndex = if (nwnField.indexedStructId) {
                                        index.toUInt()
                                    } else if (one is HasStructId) {
                                        one.structId ?: nwnField.structType.toUInt()
                                    } else {
                                        nwnField.structType.toUInt()
                                    }

                                    val newStructId: Int = if (nwnField.elementName.isNotBlank() && nwnField.elementType.isNotBlank()) {
                                        when (one) {
                                            is String -> {
                                                fieldListToStruct(
                                                    listOf(
                                                        createFieldString(
                                                            one,
                                                            nwnField.elementName,
                                                            GffFile.GffFieldType.valueOf(nwnField.elementType)
                                                        )
                                                    ), structType = finalIndex
                                                ).structId
                                            }

                                            else -> {
                                                throw RuntimeException("Unexpected type ${vals.first()!!::class.simpleName}")
                                            }
                                        }
                                    } else {
                                        one.toStruct(finalIndex).structId
                                    }

                                    newStructId
                                }, nwnField.name)
                            }
                        } else if (nwnField.blankBehavior == BlankBehavior.GENERATE) {
                            createFieldList(listOf<Int>(), nwnField.name)
                        } else
                            null
                    }
                }
        }
        return fieldList
    }

    private fun Any.toStruct(structType: UInt): GffFile.GffStruct {
        val structId = structs.size
        structs.add(GffFile.GffStruct())

        val fieldList = gffFields(this)

        return fieldListToStruct(fieldList, structId = structId, structType = structType)
    }
}