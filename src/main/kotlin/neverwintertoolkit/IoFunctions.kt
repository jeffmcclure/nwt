package neverwintertoolkit

import java.io.BufferedInputStream
import java.io.DataInput
import java.io.InputStream
import java.io.OutputStream
import java.io.RandomAccessFile
import java.nio.file.Files
import java.nio.file.Path

fun InputStream.copyBytesTo(out: OutputStream, numBytesToCopy: Int) {
    var remaining = numBytesToCopy
    val buffer = ByteArray(1000)
    while (remaining > 0) {
        val numBytesRead = this.read(buffer, 0, remaining.coerceAtMost(buffer.size))
        if (numBytesRead > 0) {
            remaining -= numBytesRead
            out.write(buffer, 0, numBytesRead)
        } else {
            remaining = 0
        }
    }
}

//fun one() {
//    val x = 5L shl 10
//    val y = 5 shl 10
//    val z = 5u shl 10
//}

fun ByteArray.process32BitIntAsLong(): Long {
    return this[0].toUByte().toLong() +
            (this[1].toUByte().toLong() shl 8) +
            (this[2].toUByte().toLong() shl 16) +
            (this[3].toUByte().toLong() shl 24)
}

fun ByteArray.extractSigned16(isBigEndian: Boolean = false): Short {
    val v1: Int = this[0].toInt() and 0xff
    val v2 = this[1].toInt() and 0xff

    return if (isBigEndian)
        (v1 shl 8 or v2).toShort()
    else
        (v2 shl 8 or v1).toShort()
}

fun ByteArray.extractUnsigned32(isBigEndian: Boolean = false): Long {
    val v1 = this[0].toLong() and 0xffL
    val v2 = this[1].toLong() and 0xffL
    val v3 = this[2].toLong() and 0xffL
    val v4 = this[3].toLong() and 0xffL

    return if (isBigEndian)
        v1 shl 8 or v2 shl 8 or v3 shl 8 or v4
    else
        v4 shl 8 or v3 shl 8 or v2 shl 8 or v1
}

fun ByteArray.process64BitIntAsLong(): Long {
    return this[0].toUByte().toLong() +
            (this[1].toUByte().toLong() shl 8) +
            (this[2].toUByte().toLong() shl 16) +
            (this[3].toUByte().toLong() shl 24) +
            (this[3].toUByte().toLong() shl 32) +
            (this[3].toUByte().toLong() shl 40) +
            (this[3].toUByte().toLong() shl 48) +
            (this[3].toUByte().toLong() shl 56)
}

fun ByteArray.process64BitIntAsULong(): ULong {
    return this[0].toUByte().toULong() +
            (this[1].toUByte().toULong() shl 8) +
            (this[2].toUByte().toULong() shl 16) +
            (this[3].toUByte().toULong() shl 24) +
            (this[3].toUByte().toULong() shl 32) +
            (this[3].toUByte().toULong() shl 40) +
            (this[3].toUByte().toULong() shl 48) +
            (this[3].toUByte().toULong() shl 56)
}

fun ByteArray.process32BitUInt(startIndex: Int = 0): UInt {
    return this[startIndex + 0].toUByte().toUInt() +
            (this[startIndex + 1].toUByte().toUInt() shl 8) +
            (this[startIndex + 2].toUByte().toUInt() shl 16) +
            (this[startIndex + 3].toUByte().toUInt() shl 24)
}

fun ByteArray.process32BitInt(): Int {
    return this[0].toUByte().toInt() +
            (this[1].toUByte().toInt() shl 8) +
            (this[2].toUByte().toInt() shl 16) +
            (this[3].toUByte().toInt() shl 24)
}

fun ByteArray.readFloat(): Float {
    val binary = extractUnsigned32().toInt()
    return java.lang.Float.intBitsToFloat(binary)
}

fun ByteArray.readDouble(): Double {
    return java.lang.Double.longBitsToDouble(process64BitIntAsLong())
}

fun InputStream.readUShort(byteArray: ByteArray = ByteArray(2), off: Int = 0): UShort {
    this.read(byteArray, off, 2)
    return byteArray.process16BitToUShort()
}

fun RandomAccessFile.readUShort(byteArray: ByteArray = ByteArray(2), off: Int = 0): UShort {
    this.read(byteArray, off, 2)
    return byteArray.process16BitToUShort()
}

fun ByteArray.process16BitToUShort(): UShort {
    return (this[0].toUByte().toInt() + (this[1].toUByte().toInt() shl 8)).toUShort()
}

fun BufferedInputStream.read32BitNumber(): Long {
    return readNBytes(4).process32BitIntAsLong()
}

fun BufferedInputStream.read64BitNumber(): Long {
    return readNBytes(8).process64BitIntAsLong()
}

fun BufferedInputStream.readUInt(): UInt {
    val arr = readNBytes(4)
    if (arr.size != 4) {
        throw RuntimeException("Tried to read 4, by got ${arr.size}")
    }

    return arr.process32BitUInt()
}

fun RandomAccessFile.readUInt(): UInt {
    return readNBytes(4).process32BitUInt()
}

fun DataInput.readUInt(): UInt {
    return readNBytes(4).process32BitUInt()
}

fun DataInput.readNBytes(numBytes: Int): ByteArray {
    return ByteArray(numBytes).also {
        this.readFully(it)
    }
}

fun RandomAccessFile.readNBytes(numBytes: Int): ByteArray {
    return ByteArray(numBytes).also {
        val numRead = this.read(it)
        if (numRead != numBytes) {
            throw RuntimeException("tried to read $numBytes but read $numRead")
        }
    }
}

fun BufferedInputStream.read16BitNumber(): Int {
    val byteArray = this.readNBytes(2)
    return byteArray[0].toUByte().toInt() + (byteArray[1].toUByte().toInt() shl 8)
}

fun InputStream.readString(numBytes: Int): String {
    return String(this.readNBytes(numBytes), Charsets.ISO_8859_1)
}

fun BufferedInputStream.readString(numBytes: Int): String {
    return String(this.readNBytes(numBytes), Charsets.ISO_8859_1)
}

fun RandomAccessFile.readString(numBytes: Int): String {
    return String(this.readNBytes(numBytes), Charsets.ISO_8859_1)
}

private val zeroByte = 0.toByte()
fun BufferedInputStream.readStringNullTerminated(numBytes: Int): String {
    val arr = this.readNBytes(numBytes)
    var asdf = arr.indexOfFirst { it == zeroByte }
    if (asdf == -1)
        asdf = numBytes

    return String(arr, 0, asdf, Charsets.ISO_8859_1)
}

fun toBytesWORD(arr: ByteArray, uint: UInt) {
    arr[0] = (uint and 0xFFu).toByte()
    arr[1] = ((uint shr 8) and 0xFFu).toByte()
}

fun toBytesWORD(arr: ByteArray, ushort: UShort) {
    toBytesWORD(arr, ushort.toUInt())
}

fun toBytes(arr: ByteArray, short: Short) {
    val dat = short.toInt()
    arr[0] = (dat and 0xff).toByte()
    arr[1] = (dat shr 8 and 0xff).toByte()
}

//fun Short.toBytes(arr: ByteArray = ByteArray(2), offset: Int = 0):ByteArray {
//    var offset = offset
//    val dat = this.toInt()
//    arr[offset] = (dat and 0xff).toByte()
//    arr[+offset] = (dat shr 8 and 0xff).toByte()
//    return arr
//}

//fun UShort.toBytes(arr: ByteArray = ByteArray(2), offset: Int = 0):ByteArray {
//    var offset = offset
//    arr[offset] = (this and 0xffu).toByte()
//    arr[+offset] = ((this shr 8) and 0xff).toByte()
//    return arr
//}

fun toBytes(arr: ByteArray, dat1: Float) {
    val dat = java.lang.Float.floatToIntBits(dat1)
    toBytes(dat, arr)
}

fun RandomAccessFile.writeUInt(num: UInt) {
    this.write(num.toBytes())
}

fun UInt.toBytes(arr: ByteArray = ByteArray(4), offset: Int = 0): ByteArray {
    var offset = offset
    arr[offset] = (this and 0xFFu).toByte()
    arr[++offset] = ((this shr 8) and 0xFFu).toByte()
    arr[++offset] = ((this shr 16) and 0xFFu).toByte()
    arr[++offset] = ((this shr 24) and 0xFFu).toByte()
    return arr;
}

fun Int.toBytes(arr: ByteArray = ByteArray(4), offset: Int = 0): ByteArray {
    var offset = offset
    arr[offset] = (this and 0xFF).toByte()
    arr[++offset] = ((this shr 8) and 0xFF).toByte()
    arr[++offset] = ((this shr 16) and 0xFF).toByte()
    arr[++offset] = ((this shr 24) and 0xFF).toByte()
    return arr
}

fun toBytes(dat: Int, arr: ByteArray = ByteArray(4)): ByteArray {
    arr[0] = (dat and 0xFF).toByte()
    arr[1] = ((dat shr 8) and 0xFF).toByte()
    arr[2] = ((dat shr 16) and 0xFF).toByte()
    arr[3] = ((dat shr 24) and 0xFF).toByte()
    return arr
}

fun toBytes(dat: Long): ByteArray {
    return byteArrayOf(
        (dat and 0xFF).toByte(),
        ((dat shr 8) and 0xFF).toByte(),
        ((dat shr 16) and 0xFF).toByte(),
        ((dat shr 24) and 0xFF).toByte(),
        ((dat shr 32) and 0xFF).toByte(),
        ((dat shr 40) and 0xFF).toByte(),
        ((dat shr 48) and 0xFF).toByte(),
        ((dat shr 56) and 0xFF).toByte(),
    )
}

fun toBytes(dat: ULong): ByteArray {
    return byteArrayOf(
        (dat and 0xFFu).toByte(),
        ((dat shr 8) and 0xFFu).toByte(),
        ((dat shr 16) and 0xFFu).toByte(),
        ((dat shr 24) and 0xFFu).toByte(),
        ((dat shr 32) and 0xFFu).toByte(),
        ((dat shr 40) and 0xFFu).toByte(),
        ((dat shr 48) and 0xFFu).toByte(),
        ((dat shr 56) and 0xFFu).toByte(),
    )
}

fun List<String>.sortFirstListBySecondList(fieldOrder: List<String>): List<String> {
    return this.sortedBy {
        val order = fieldOrder.indexOf(it)
        if (order == -1)
            Integer.MAX_VALUE
        else
            order
    }.filter { fieldOrder.indexOf(it) != -1 }
}

/**
 * returned extension starts with a period
 */
fun getExtension(fname: String): String {
    return extractExtension1(fname).second
}

/**
 * returned extension starts with a period
 */
fun extractExtension1(fname: String): Pair<String, String> {
    val index = fname.lastIndexOf('.')
    if (index == -1) throw RuntimeException("$fname does not have an extension")
    val name = fname.substring(0, index)
    val ext = fname.substring(index)
    return Pair(name, ext)
}

/**
 * returned extension starts with a period
 */
fun String.extractExtension3(): Pair<String, String> {
    val index = this.lastIndexOf('.')
    if (index == -1) throw RuntimeException("$this does not have an extension")
    val name = this.substring(0, index)
    val ext = this.substring(index)
    return Pair(name, ext)
}

fun String.extractFileName(): String {
    val index = this.lastIndexOf('.')
    if (index == -1) throw RuntimeException("$this does not have an extension")
    return this.substring(0, index)
}


private val extensionRegex = "^(.*)(\\.[^.]+)$".toRegex()

fun extractExtension2(fname: String): Pair<String, String> {
    val xxxx: MatchResult = extensionRegex.matchEntire(fname) ?: throw RuntimeException("$fname does not have an extension")
    return Pair(xxxx.groupValues[1], xxxx.groupValues[2])
}

fun Path.isDirEmpty(): Boolean {
    Files.newDirectoryStream(this).use { dirStream -> return !dirStream.iterator().hasNext() }
}