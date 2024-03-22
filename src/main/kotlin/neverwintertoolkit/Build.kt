package neverwintertoolkit

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import neverwintertoolkit.command.BuildCommand
import neverwintertoolkit.file.erf.ErfWriter
import neverwintertoolkit.file.gff.GffFactory
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.io.path.exists
import kotlin.io.path.getLastModifiedTime
import kotlin.io.path.isDirectory
import kotlin.io.path.isRegularFile
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.system.exitProcess

class Build(val nwtJson: Path, val dir: Path = nwtJson.parent, val buildCommand: BuildCommand = BuildCommand()) {

    val status = buildCommand.status

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(Build::class.java.toString())
        private val jsonExtension = "(?i)\\.json5?$".toRegex()
    }

    val targets: List<Nwt> by lazy { Nwt.parseJson(nwtJson) }

    class Rec(val aPath: Path, val baseName: String, val loc: String)

    @OptIn(DelicateCoroutinesApi::class)
    fun pack() {
        targets.forEach { nwt ->
            logger.info("target = {}", nwt.targetPath)

            val sfile = nwt.targetPath
            val file = Paths.get("target").resolve(sfile.name)

            val strings = if (!nwt.description.isNullOrBlank()) listOf(ErfWriter.AString(nwt.description, 0)) else emptyList()
            val erfFile = ErfWriter(buildCommand, strings)

            buildCommand.logDebug { "threadCount=${buildCommand.threadCount}" }
            if (buildCommand.statusLogLevel > 1) status.println("threadCount=${buildCommand.threadCount}")
            val context2 = newFixedThreadPoolContext(buildCommand.threadCount, "pack")

            try {
                runBlocking(context2) {
                    val ascope = this
                    val added = ConcurrentHashMap<String, Boolean>()

                    // build list of files to compile or add
                    val autoRules: List<Pair<String, String>> = dir.resolve("src").listDirectoryEntries().filter { it.isDirectory() }.map { theDir ->
                        Pair("*." + theDir.name, "src/${theDir.name}")
                    }

                    val allRules = mutableListOf<Pair<String, String>>()
                    val baseRules = nwt.rules ?: emptyList()
                    allRules.addAll(baseRules)
                    allRules.addAll(autoRules.filter { a -> baseRules.none { b -> a.second == b.second } })

                    if (buildCommand.debugEnabled) {
                        status.println("baseRules=" + baseRules.map { it.second })
                        status.println("autoRules=" + autoRules.map { it.second })
                        status.println("allRules=" + allRules.map { it.second })
                    }

                    val list = allRules.mapNotNull { (pattern, loc) ->
                        val theDir = dir.resolve(loc)
                        if (Files.isDirectory(theDir)) {
                            val pathMatcher = FileSystems.getDefault().getPathMatcher("glob:$pattern")

                            // sort so if the source and target are both present the .json source will be processed first
                            theDir.listDirectoryEntries().filter { it.isRegularFile() }.sortedBy { if (GffFactory.isJsonFile(it)) 0 else 1 }
                                .mapNotNull { aPath ->
                                    val baseName = aPath.name.replace(jsonExtension, "")
                                    val matches = pathMatcher.matches(Paths.get(baseName))
                                    if (matches) {
                                        if (added.putIfAbsent(aPath.name, true) != null) {
                                            status.println("Skipping duplicate '${aPath.name}'")
                                            null
                                        } else
                                            Rec(aPath, baseName, loc)
                                    } else null
                                }
                        } else null
                    }.flatten() ?: emptyList()

                    val counter = AtomicInteger()
                    val size = list.size
                    list.forEach { rec ->
                        ascope.launch {
                            logger.debug("processing of {}", rec)
                            val toAdd = processPath(rec, counter, size)
                            erfFile.addFile(toAdd)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                System.err.println("Exiting due to error")
                exitProcess(3)
            }
            status.println("Writing $file...")
            erfFile.writeErf(file)
            status.println("Done")
        }
    }

    private suspend fun processPath(rec: Rec, index: AtomicInteger, size: Int): Path {
        return if (GffFactory.isJsonFile(rec.aPath)) {
            val targ = dir.resolve("target").resolve(rec.loc).resolve(rec.baseName)
            Files.getLastModifiedTime(rec.aPath)
            if (targ.exists() && targ.getLastModifiedTime() >= rec.aPath.getLastModifiedTime()) {
                buildCommand.infoSuspend { "%5d / %5d : Adding cached %s".format(index.incrementAndGet(), size, targ) }
            } else {
                buildCommand.infoSuspend { "%5d / %5d : Compiling and adding %s".format(index.incrementAndGet(), size, rec.aPath) }
                val factory = GffFactory.getFactoryForFileName(rec.aPath.name)
                    ?: throw java.lang.RuntimeException("Could not find factory for ${rec.aPath.name}")
                val obj = factory.parseJson(rec.aPath)

                targ.parent?.let { d ->
                    if (!Files.isDirectory(d)) Files.createDirectories(d)
                }

                obj.writeGff(targ)
            }
            targ
        } else {
            buildCommand.infoSuspend { "%5d / %5d : Adding %s".format(index.incrementAndGet(), size, rec.aPath) }
            rec.aPath
        }
    }

}
