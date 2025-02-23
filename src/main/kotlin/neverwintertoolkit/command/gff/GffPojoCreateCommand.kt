package neverwintertoolkit.command.gff

import neverwintertoolkit.FileType
import neverwintertoolkit.command.VersionInfo
import neverwintertoolkit.file.erf.ErfFile
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffFile
import neverwintertoolkit.file.gff.toProgression
import picocli.CommandLine
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDateTime
import java.util.concurrent.Callable
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteExisting
import kotlin.io.path.deleteRecursively
import kotlin.io.path.name

@CommandLine.Command(
    name = "p",
    mixinStandardHelpOptions = true,
    description = ["Create POJO from JSON or XML"],
    versionProvider = VersionInfo::class
)
class GffPojoCreateCommand : GffOptions(), Callable<Int> {

    companion object {
        //        private val logger = org.slf4j.LoggerFactory.getLogger(GffPojoCreateCommand::class.java.toString())!!
        private val logger = org.slf4j.LoggerFactory.getLogger("neverwintertoolkit.command.GffPojoCreateCommand")
//        init {
//            println("zzz=" + GffPojoCreateCommand::class.java.name)
//        }
    }

    @CommandLine.Parameters(index = "0", description = ["The GFF file to read."], arity = "1..")
    override lateinit var files: List<File>

    @CommandLine.Option(names = ["u", "-u"], description = ["comma separated list of struct nums to process, e.g. '0,15-9,16,20-33,0,99-end'"])
    override var structList: String? = "0"

    @CommandLine.Option(names = ["t", "-t"], description = ["only process files of given type (e.g. are, git, gic, ifo, etc)"])
    var fileType: String? = null

    override fun call(): Int {
        val clasMap = DatumSet()

        files.forEach { aFile ->
            val aPath = aFile.toPath()
            if (ErfFile.isModuleFile(aPath)) {
                processErfFile(aPath, clasMap)
            } else {
                processOneFile(aFile, clasMap)
            }
        }

        globalOptions.status.println("Field Count=${clasMap.size}")
        val out = if (fOption) {
            globalOptions.status.println("writing to NewClass.kt")
            PrintWriter(FileWriter("NewClass.kt"))
        } else globalOptions.spec.commandLine().out
        genClasses(out, clasMap)
        out.close()

        globalOptions.status.flush()
        return 0
    }

    @OptIn(ExperimentalPathApi::class)
    private fun processErfFile(erfFile: Path, clasMap: DatumSet) {
        globalOptions.status.println("\nprocessing module $erfFile, Field Count=${clasMap.size}")
        val erf = ErfFile(erfFile, globalOptions)
        val dir = Files.createTempDirectory("")!!
        try {
            erf.readAllEntries().forEach { entry ->
                when (entry.fileType) {
                    FileType.kFileTypeGIC,
                    FileType.kFileTypeGIT,
                    FileType.kFileTypeDLG,
                    FileType.kFileTypeIFO,
                    FileType.kFileTypeARE,
                    FileType.kFileTypeUTC,
                    FileType.kFileTypeUTD,
                    FileType.kFileTypeUTE,
                    FileType.kFileTypeUTI,
                    FileType.kFileTypeUTP,
                    FileType.kFileTypeUTM,
                    FileType.kFileTypeUTS,
                    FileType.kFileTypeUTT,
                    FileType.kFileTypeUTW,
                    FileType.kFileTypeJRL,
                    FileType.kFileTypeFAC,
                    FileType.kFileTypeITP,
                    -> {
                        val target = dir.resolve(entry.fileNameWithExtension)
                        erf.extractEntry(entry, target, useJson = false, overwrite = false, toStdout = false)
                        processOneFile(target.toFile(), clasMap, erfFile)
                        target.deleteExisting()
                    }

                    else -> {}
                }
                entry.fileExtension

            }
        } finally {
            dir.deleteRecursively()
        }
    }

    private fun processOneFile(afile: File, clasMap: DatumSet, erfFile: Path? = null) {
        if (fileType != null) {
            if (fileType != GffFactory.getFileType(afile.name)) {
                globalOptions.status.println("skipping $afile")
                return
            }
        }
        val gff = GffFile(afile.toPath(), status = globalOptions.status, gffOptions = GffOptions(globalOptions))
        globalOptions.status.println("processing $afile, Field Count=${clasMap.size}")

        val structList = gff.structs

        val dumpList = this.structList!!.replace("end", (structList.size - 1).toString())

        val extension = GffFactory.getFileType(afile.name) ?: throw RuntimeException("could not determine extension for $afile")
        val className = extension.substring(0, 1).uppercase() + extension.substring(1)

        dumpList.split(",").map { term ->
//            status.format("term=%s\n", term)
            term.toProgression(structList.size - 1).map { structId: Int ->
//                status.format("calling captureClass structId=%d file=%s\n", structId, afile.name)
                captureClass(structList, clasMap, className, className, structList[structId].fields, gff.file.name, erfFile)
            }
        }
    }

    fun captureClass(
        structList: List<GffFile.GffStruct>,
        clasMap: DatumSet,
        baseName: String,
        className: String,
        fields: List<GffFile.GffField>,
        fname: String,
        erfFile: Path?,
    ) {
        fields.forEach { field ->
            when (field.type) {

                GffFile.GffFieldType.Struct -> {
                    val struct = field.dataOrDataOffset as GffFile.GffStruct
                    val newClassName = baseName + field.label.toJsonName()
                    clasMap.capture(Datum(className, field, fname, baseName, structType = structList[struct.structId].type, erfFile = erfFile)) // todo here
                    captureClass(structList, clasMap, baseName, newClassName, structList[struct.structId].fields, fname, erfFile)
                }

                GffFile.GffFieldType.List -> {

                    val className1 = baseName + field.label.listNameToClassName()

                    @Suppress("UNCHECKED_CAST")
                    val listValue = field.dataOrDataOffset as List<Number>
                    val structType = if (listValue.isNotEmpty()) {
                        structList[listValue.first().toInt()].type
                    } else null
                    clasMap.capture(Datum(className, field, fname, baseName, structType = structType))
                    val datum = Datum(className1, GffFile.GffField(GffFile.GffFieldType.VOID, 0u, "_placeholder_", Any(), null), fname, baseName)

                    clasMap.capture(datum)
                    listValue.forEach { structId ->
                        captureClass(structList, clasMap, baseName, className1, structList[structId.toInt()].fields, fname, erfFile)
                    }
                }

                else -> {
                    clasMap.capture(Datum(className, field, fname, baseName))
                }
            }
        }
    }


    private fun genClasses(out2: PrintWriter, clasMap: DatumSet) {
        out2.println("\n\n// Generated ${LocalDateTime.now()}")
        out2.println("import com.fasterxml.jackson.annotation.JsonProperty")
        out2.println("import neverwintertoolkit.annotation.NwnField")
        out2.println("import neverwintertoolkit.gff.CExoLocString")
        out2.println("import neverwintertoolkit.gff.GffObj")
        out2.println("import java.io.OutputStream")

        // this sorting will make it such that the "top level classes" (e.g. Git, Are, Dlg, etc.) appear at the top of the file, with remaining classes sorted alphabetically
        clasMap.calcResult.entries.toList().sortedBy { it.key }.sortedBy { it.value.first().className != it.value.first().baseName }
            .forEach { (className, fields) ->
                genClass(out2, className, fields)
            }
    }

    private fun genClass(out2: PrintWriter, clasName: String, fields: List<Datum>) {
        out2.println()
        out2.print("class $clasName")
        if (clasName == fields.first().baseName)
            out2.print(" : GffObj")
        out2.println(" {")
        if (clasName == fields.first().baseName)
            out2.println(
                "    override fun writeGff(output: OutputStream) {\n" +
                        "        TODO(\"Not yet implemented\")\n" +
                        "    }"
            )
        fields.filter { it.field.label != "_placeholder_" }.forEach { datum ->
            genFieldCode(datum, out2)
        }
        out2.println("}")
    }
}

private val re2 = "[_ \\d]".toRegex()

private fun String.listNameToClassName(): String {
//    val newName = this.replace(re2, "").replace("(?i)List".toRegex(), "")
    val newName = this.replace("(?i)List".toRegex(), "").replace("\\d".toRegex(), "")
        .split(re1).map { if (it.isEmpty()) "" else it.substring(0, 1).uppercase() + it.substring(1) }.joinToString("")

    return when {
        newName.isEmpty() -> "Some"
//        "class".equals(newName, ignoreCase = true) -> "ClassX"
        else -> newName
    }
}

private val re1 = "[_ ]".toRegex()
private fun String.toJsonName(): String {
    val ret = this.split(re1).map { if (it.isEmpty()) "" else it.substring(0, 1).uppercase() + it.substring(1) }.joinToString("")
//    val ret = this.replace(re1, "")
    return if ("Class".equals(ret, ignoreCase = true)) "ClassX" else ret
}


class Datum constructor(
    val className: String,
    val field: GffFile.GffField,
    val comment: String,
    val baseName: String,
    val structType: UInt? = null,
    val erfFile: Path? = null
) {

    val jsonFieldName = field.label.toJsonName()
    val javaFieldName = jsonFieldName.substring(0, 1).lowercase() + jsonFieldName.substring(1)

    val javaClassName = if (field.type == GffFile.GffFieldType.List)
        baseName + field.label.listNameToClassName()
    else
        field.label.toJsonName()

    override fun toString(): String {
        return "Datum(className='$className', field=$field, comment='$comment', erfFile='${erfFile}', baseName='$baseName', jsonName='$jsonFieldName', javaName='$javaClassName', structType=$structType)"
    }
}

class DatumSet {
    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(DatumSet::class.java)
    }

    val calcResult: Map<String, List<Datum>>
        get() {
            val one = set.filter { it.field.type == GffFile.GffFieldType.CExoString }
            val two = one.filter { it.field.label.endsWith("Table") }

            val grouping: Map<String, List<Datum>> = set.groupBy { it.className }
            // for each class
            return grouping.entries.associate { (className: String, fields: List<Datum>) ->
                var fields = fields
                if (className.endsWith("VarTable")) {
                    val names = fields.map { it.field.label }.toSet()
                    val varTableField = setOf("Value", "Type", "Name", "_placeholder_", "Comment")
                    if (varTableField.containsAll(names) && (names.size == 4 || names.size == 5)) {
                        val tlist = fields.filter { it.field.label != "Value" }.toMutableList()
                        val virst = fields.first()
                        tlist.add(Datum(virst.className, GffFile.GffField(GffFile.GffFieldType.WORD, 0u, "Value", Any()), "synthetic", virst.baseName))
                        fields = tlist
                    }
                }

                // for each field name
                val fieldList: List<Datum> = fields.groupBy { it.field.label }.entries.map { (fieldName, values) ->
                    val values2 = values.filter { it.structType != null }
                    val values3 = values2.ifEmpty { values }
                    // if the field is duplicated with multiple types, use the one that is most prevalent
                    val ggg: List<List<Datum>> = values3.groupBy { it.field.type }.values.sortedByDescending { it.size }
                    ggg.first().first()
                }
                Pair(className, fieldList)
            }
        }

    val size: Int
        get() = set.size

    val set = HashSet<Datum>()
    fun capture(datum: Datum) {
        logger.trace("capture() {}", datum)
        set.add(datum)
    }
}

private fun genFieldCode(datum: Datum, out2: PrintWriter) {
    var typeStr = ""
    val type = if (datum.comment == "synthetic" && datum.field.label == "Value")
        "Any"
    else {
        typeStr = datum.field.type.toString()
        when (datum.field.type) {
            GffFile.GffFieldType.BYTE -> "UByte"
            GffFile.GffFieldType.CHAR -> "Char"
            GffFile.GffFieldType.WORD -> "UShort"
            GffFile.GffFieldType.SHORT -> "Short"
            GffFile.GffFieldType.DWORD -> "UInt"
            GffFile.GffFieldType.INT -> "Int"
            GffFile.GffFieldType.DWORD64 -> "ULong"
            GffFile.GffFieldType.INT64 -> "Long"
            GffFile.GffFieldType.FLOAT -> "Float"
            GffFile.GffFieldType.DOUBLE -> "Double"
            GffFile.GffFieldType.CExoString -> "String"
            GffFile.GffFieldType.ResRef -> "String"
            GffFile.GffFieldType.CExoLocString -> "CExoLocString"
            GffFile.GffFieldType.VOID -> "ByteArray"
            GffFile.GffFieldType.Struct -> datum.baseName + datum.jsonFieldName
            GffFile.GffFieldType.List -> {
                "List<${datum.javaClassName}>"
            }
        }
    }

    val ttt = if (datum.structType != null) ", structType = ${datum.structType}" else ""

    out2.format("\n    @get:NwnField(name = \"%s\", type = \"%s\"%s)\n", datum.field.label, typeStr, ttt)
    out2.format("    @get:JsonProperty(\"${datum.jsonFieldName}\")\n")
    out2.format("    @set:JsonProperty(\"${datum.jsonFieldName}\")\n")
    out2.format("    var %s: %s? = null\n", datum.javaFieldName, type)
}

fun GffFile.GffField.genFieldCode2(): String {
    val field = this
    val typeStr = field.type.toString()

    val type = when (field.type) {
        GffFile.GffFieldType.BYTE -> "UByte"
        GffFile.GffFieldType.CHAR -> "Char"
        GffFile.GffFieldType.WORD -> "UShort"
        GffFile.GffFieldType.SHORT -> "Short"
        GffFile.GffFieldType.DWORD -> "UInt"
        GffFile.GffFieldType.INT -> "Int"
        GffFile.GffFieldType.DWORD64 -> "ULong"
        GffFile.GffFieldType.INT64 -> "Long"
        GffFile.GffFieldType.FLOAT -> "Float"
        GffFile.GffFieldType.DOUBLE -> "Double"
        GffFile.GffFieldType.CExoString -> "String"
        GffFile.GffFieldType.ResRef -> "String"
        GffFile.GffFieldType.CExoLocString -> "CExoLocString"
        GffFile.GffFieldType.VOID -> "ByteArray"
        GffFile.GffFieldType.Struct -> "Struct"
        GffFile.GffFieldType.List -> {
            "List<>"
        }
    }

    val jsonFieldName = field.label.toJsonName()
    val javaFieldName = jsonFieldName.substring(0, 1).lowercase() + jsonFieldName.substring(1)

    val sw = java.io.StringWriter()
    val out2 = PrintWriter(sw)
    out2.format("\n    @get:NwnField(name = \"%s\", type = \"%s\"%s)\n", field.label, typeStr, "")
    out2.format("    @get:JsonProperty(\"${jsonFieldName}\")\n")
    out2.format("    @set:JsonProperty(\"${jsonFieldName}\")\n")
    out2.format("    var %s: %s? = null\n", javaFieldName, type)
    return sw.toString()
}