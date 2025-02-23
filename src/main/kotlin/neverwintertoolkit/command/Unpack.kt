package neverwintertoolkit.command

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Semaphore
import neverwintertoolkit.FileType
import neverwintertoolkit.Nwt
import neverwintertoolkit.file.erf.ErfFile
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.globalSettings
import java.io.PrintStream
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.name

class Unpack(
    val nwtJson: Path,
    val dir: Path = nwtJson.parent,
    val globalOptions: GlobalOptions = GlobalOptions(),
    val status: PrintStream,
    val useJson: Boolean,
    val threadCount: Int = Runtime.getRuntime().availableProcessors()
) {

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(Unpack::class.java.toString())
    }

    fun getTargets(): List<Nwt> {
        return Nwt.parseJson(nwtJson)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun unpackJar(source: Path? = null, erfJson: Boolean = false, unpackNcsFiles: Boolean = false) {
        val nwt = getTargets().first()
            logger.info("target = {}", nwt.source)

            val file = source ?: nwt.sourcePath
            val fname = file.name
            if (Files.notExists(file)) throw RuntimeException("does not exist $file")
            val erf = ErfFile(file, outStatus = status, globalOptions = globalOptions)
            if (erfJson)
                erf.extractErfJson(dir, globalOptions.status)

            globalOptions.logDebug { "threadCount=${threadCount}" }

            val context2 = newFixedThreadPoolContext(threadCount, "unpack")

            runBlocking(context2) {
                val ascope = this
                val allEntries = if (unpackNcsFiles)
                    erf.readAllEntries()
                else
                    erf.readAllEntries().filter { it.resType != FileType.kFileTypeNCS.id }

                val entryCount = allEntries.size
                var counter = 0
                val sem = Semaphore(1)
                allEntries.forEach { entry ->
                    val (pattern, loc) = nwt.rules?.firstOrNull { (pattern, loc) ->
                        val pathMatcher = FileSystems.getDefault().getPathMatcher("glob:$pattern")
                        pathMatcher.matches(Paths.get(entry.fileNameWithExtension))
                    } ?: Pair("*" + entry.fileExtension, "src/" + entry.fileExtension.substring(1)) // default is a directory under src/ with same extension

                    if (loc.isNotBlank()) {
                        ascope.launch {
                            val tar = dir.resolve(loc).resolve(entry.fileNameWithExtension)
                            val relative = Paths.get(loc).resolve(entry.fileNameWithExtension)
                            var entryName: String? = null
                            if (globalOptions.infoEnabled) {
                                val willJson = useJson && GffFactory.getFactoryForFileName(entry.fileNameWithExtension) != null
                                entryName = if (willJson) {
                                    "$relative${globalSettings.getJsonExtension()}"
                                } else {
                                    relative.toString()
                                }
                                sem.acquire()
                                try {
                                    println(" %s %5d / %5d %s %s".format(fname, ++counter, entryCount, "extracting", entryName))
                                } finally {
                                    sem.release()
                                }
                            }
                            erf.extractEntry(entry, tar, useJson, overwrite = false, toStdout = false)
                        }
                    } else {
                        globalOptions.logInfo { "%20s %10s %s".format(fname, "skipping", entry.fileNameWithExtension) }
                    }
                }
            }
        rebuildHakPath(nwtJson, dir, globalOptions)
    }
}
