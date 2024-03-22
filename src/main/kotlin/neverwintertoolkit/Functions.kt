package neverwintertoolkit

import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


val UByteZero = 0u.toUByte()
val UByteOne = 1u.toUByte()

fun getNwt(inputDir: File? = null): Nwt {
    return Nwt.parseJson(getNwtJsonFile(inputDir)).first()
}

fun getNwtJsonFile(inputDir: File?): Path {
    val dir: Path = inputDir?.toPath() ?: Paths.get(System.getProperty("user.dir"))
    val nwtJsonFile = dir.resolve("nwt.json5")
    if (Files.exists(nwtJsonFile)) return nwtJsonFile
    val file2 = dir.resolve("nwt.json")
    if (Files.exists(file2)) return file2

    throw RuntimeException("neither nwt.json5 nor nwt.json exists")
}

fun CharSequence.fastIsBlank(): Boolean {
    for (i in 0..<length) {
        val c = this[i]
        if (!Character.isWhitespace(c) &&
            c != '\u00a0' && c != '\u2007' && c != '\u202f'
        ) {
            return false
        }
    }
    return true
}

fun CharSequence?.fastIsNullOrBlank(): Boolean {
    return this == null || this.fastIsBlank()
}