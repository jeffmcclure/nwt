package neverwintertoolkit.command

import kotlin.time.Duration.Companion.milliseconds

fun humanReadableDuration(millis: Long): String {
    val sb = StringBuilder()
    val hours = millis / 60_000 / 60
    if (hours > 0) {
        sb.append("$hours hours, ")
    }
    val mins = (millis / 60_000) % 60
    if (mins > 0 || !sb.isEmpty()) {
        sb.append("$mins minutes, ")
    }

    val secs = (millis % 60_000) / 1000
    val ms = millis % 1000

    if (secs > 0 || !sb.isEmpty())
        sb.append("$secs.$ms seconds")
    else
        return "$ms milliseconds"

    return sb.toString()
}

fun main() {
    println("Hello, World! " + System.currentTimeMillis() % 1000)

    test(123)
    test(68_123)
    test(60_000 * 90 + 68_123)
    test(60_000 * 999 + 68_123)
    test(60_000 * 23) // 23 mins
    test(60*60_000 * 23) // 23 hours
    test(24*60_000*60*5 + 60_000 * 90 + 68_123)
}

fun test(ms: Long) {
    println("$ms = " + humanReadableDuration(ms))
    println("$ms = " + ms.milliseconds)
}