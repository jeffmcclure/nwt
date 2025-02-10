package neverwintertoolkit

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import neverwintertoolkit.command.BuildCommand
import neverwintertoolkit.command.NwtCommand.Companion.startTime
import neverwintertoolkit.file.erf.ErfWriter
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import neverwintertoolkit.model.dlg.Dlg
import neverwintertoolkit.model.dlg.DlgSorter
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.io.path.*
import kotlin.system.exitProcess
import kotlin.time.Duration.Companion.milliseconds

class Build(val nwtJson: Path, val dir: Path = nwtJson.parent, val buildCommand: BuildCommand = BuildCommand()) {

    val status = buildCommand.status

    companion object {
        private val logger = org.slf4j.LoggerFactory.getLogger(Build::class.java.toString())
        private val jsonExtension = "(?i)\\.json5?$".toRegex()
    }

    val targets: List<Nwt> by lazy { Nwt.parseJson(nwtJson) }

    data class Rec(val aPath: Path, val baseName: String, val loc: String, val targetPath: Path)

    @OptIn(DelicateCoroutinesApi::class, ExperimentalPathApi::class)
    fun pack() {
        targets.forEach { nwt ->
            logger.info("target = {}", nwt.targetPath)

            val sfile = nwt.targetPath
            val file = Paths.get("target").resolve(sfile.name)

            val strings = listOf(
                ErfWriter.AString(((nwt.description?.plus(" ") ?: "") + (nwt.version ?: "")).trim(), 0),
            )
            val erfFile = ErfWriter(buildCommand, strings)

            buildCommand.logDebug { "threadCount=${buildCommand.threadCount}" }
            if (buildCommand.statusLogLevel > 1) status.println("threadCount=${buildCommand.threadCount}")
            val context2 = newFixedThreadPoolContext(buildCommand.threadCount, "pack")

            try {
                runBlocking(context2) {
                    val ascope = this
                    val added = ConcurrentHashMap<String, Boolean>()

                    // build list of files to compile or add
                    val autoRules: List<Pair<String, String>> =
                        dir.resolve("src").listDirectoryEntries().filter { it.isDirectory() }.map { theDir ->
                            Pair("*." + theDir.name, "src/${theDir.name}")
                        }

                    val allRules = mutableListOf<Pair<String, String>>()
                    val baseRules = nwt.rules ?: emptyList()
                    allRules.addAll(baseRules)
                    allRules.addAll(autoRules.filter { a -> baseRules.none { b -> a.second == b.second } })

                    allRules.add(Pair("*.dlgs", "src/dlg"))

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
//                            val x: Sequence<Path> =

                            theDir.walk(PathWalkOption.BREADTH_FIRST, PathWalkOption.INCLUDE_DIRECTORIES)
                                .filter { it.isRegularFile() }
                                .sortedWith(compareBy<Path> { if (GffFactory.isJsonFile(it)) 0 else 1 }.thenBy { it.name.lowercase() })
                                .map { aPath: Path ->
                                    aPath
                                }.toList().mapNotNull { aPath: Path ->
//                            theDir.listDirectoryEntries().filter { it.isRegularFile() }
//                                .sortedWith(compareBy<Path> { if (GffFactory.isJsonFile(it)) 0 else 1 }.thenBy { it.name.lowercase() })
//                                .mapNotNull { aPath ->
                                    val baseName = aPath.name.replace(jsonExtension, "")
                                    val matches = pathMatcher.matches(Paths.get(baseName))
                                    if (!matches) {
                                        buildCommand.logTrace { "nomatch $aPath" }
                                        null
                                    } else {
                                        buildCommand.logTrace { "match $aPath" }
//                                        if (added.putIfAbsent(aPath.name, true) != null) {
//                                            status.println("Skipping duplicate '${aPath.name}'")
//                                            null
//                                        } else {
                                        val (name, ext) = baseName.extractExtension3()
                                        val index = aPath.name.indexOf('.')

                                        val outBaseName = if (ext == ".dlgs")
                                            aPath.name.substring(0, index) + ".dlg"
                                        else
                                            baseName

                                        val targ = dir.resolve("target").resolve(loc).resolve(outBaseName)

                                        Rec(aPath, baseName, loc, targ)
                                    }
                                }
                        } else null
                    }.flatten() ?: emptyList()

                    val counter = AtomicInteger()
                    val size = list.size

                    buildCommand.logDebug { "list.size=${list.size}" }
                    val list2 = list.groupBy { it.targetPath }
                    buildCommand.logDebug { "list2.size=${list2.size}" }
                    buildCommand.logInfo { "Adding files..." }
                    list2.forEach { rec ->
                        ascope.launch {
                            logger.debug("processing of {}", rec)
                            val toAdd = processPaths(rec.value, counter, size)
                            erfFile.addFile(toAdd)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                System.err.println("Exiting due to error")
                exitProcess(3)
            }
            buildCommand.logInfo { "" }
            status.println("Writing $file...")
            erfFile.writeErf(file)
            buildCommand.logInfo { "Done in " + (System.currentTimeMillis() - startTime).milliseconds.toString() }
        }
    }

    val baseFormat = "  %5d / %5d : %s %s"

    private suspend fun processPaths(rec: List<Rec>, index: AtomicInteger, size: Int): Path {
        rec.firstOrNull { !GffFactory.isJsonFile(it.aPath) }?.let { rec1 ->
            buildCommand.logSuspend { baseFormat.format(index.incrementAndGet(), size, "Adding", rec1.aPath) }
            return rec1.aPath
        }

        // target artifact is the same for all
        val targ = dir.resolve("target").resolve(rec.first().loc).resolve(rec.first().baseName)
        if (targ.exists() && !rec.any { it.aPath.getLastModifiedTime() > targ.getLastModifiedTime() }) {
            rec.forEach {
                buildCommand.logSuspend {
                    baseFormat.format(
                        index.incrementAndGet(),
                        size,
                        "Adding cached",
                        targ
                    ) + " for ${it.aPath.name}"
                }
            }
            return targ
        }

//        buildCommand.logDebug { "size = ${rec.size}" }
        if (buildCommand.traceEnabled) {
            rec.forEach { aa ->
                buildCommand.logTrace { "$aa" }
            }
        }

        if (rec.any {
                it.baseName.lowercase().endsWith(".dlgs.json5") ||
                        it.baseName.lowercase().endsWith(".dlgs.json") ||
                        it.baseName.lowercase().endsWith(".dlgs")
            }) {
            buildCommand.logDebug { "one" }
            rec.forEach {
                buildCommand.logDebug { "$it" }
            }
            val arec = rec.firstOrNull {
                it.baseName.lowercase().endsWith(".dlg.json5") ||
                        it.baseName.lowercase().endsWith(".dlg.json") ||
                        it.baseName.lowercase().endsWith(".dlg")
            } ?: throw RuntimeException("none found")
            buildCommand.logDebug { "$arec" }
            val obj = processOnePath(index, size, arec, targ) as Dlg

//            val dlgs: List<URL> =
            rec.filter {
                it.baseName.lowercase().endsWith(".dlgs.json5") ||
                        it.baseName.lowercase().endsWith(".dlgs.json") ||
                        it.baseName.lowercase().endsWith(".dlgs")
            }.map { aaa: Rec ->
                buildCommand.logDebug { "two" }
                buildCommand.logSuspend {
                    baseFormat.format(index.incrementAndGet(), size, "Compiling and adding", aaa.aPath)
                }
                ReadPart().readPart(obj, aaa.aPath.toUri().toURL())
//                aaa.aPath.toUri().toURL()
            }
//            ReadPart().readPart(obj, *dlgs.toTypedArray())
            DlgSorter(obj).sorted().writeGff(targ)
//            obj.writeGff(targ)
        } else {
            rec.forEach { arec ->
                val obj = processOnePath(index, size, arec, targ)
                obj.writeGff(targ)
            }
        }

        return targ
    }

    private suspend fun processOnePath(
        index: AtomicInteger,
        size: Int,
        arec: Rec,
        targ: Path
    ): GffObj {
        buildCommand.logSuspend {
            baseFormat.format(index.incrementAndGet(), size, "Compiling and adding", arec.aPath)
        }
        val factory: GffFactory<out GffObj> = GffFactory.getFactoryForFileName(arec.aPath.name)
            ?: throw RuntimeException("Could not find factory for ${arec.aPath.name}")

        val obj1 = factory.parseJson(arec.aPath)

        targ.parent?.let { d ->
            if (!Files.isDirectory(d)) Files.createDirectories(d)
        }
        return obj1
    }

}
