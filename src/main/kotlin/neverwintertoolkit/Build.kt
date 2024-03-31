package neverwintertoolkit

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import neverwintertoolkit.command.BuildCommand
import neverwintertoolkit.file.erf.ErfWriter
import neverwintertoolkit.file.gff.CExoLocString
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.GffObj
import neverwintertoolkit.model.dlg.Dlg
import neverwintertoolkit.model.dlg.DlgEntry
import neverwintertoolkit.model.dlg.DlgFoo
import neverwintertoolkit.model.dlg.DlgReply
import neverwintertoolkit.model.dlg.DlgsNpcResponse
import neverwintertoolkit.model.dlg.Dlgs
import neverwintertoolkit.model.dlg.DlgsPcChoice
import neverwintertoolkit.model.dlg.DlgSorter
import neverwintertoolkit.model.dlg.DlgStarting
import java.net.URL
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.ArrayList
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import kotlin.RuntimeException
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

    data class Rec(val aPath: Path, val baseName: String, val loc: String, val targetPath: Path)

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

                    allRules.add(Pair("*.dlg-part", "src/dlg"))

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
                            theDir.listDirectoryEntries().filter { it.isRegularFile() }
                                .sortedWith(compareBy<Path> { if (GffFactory.isJsonFile(it)) 0 else 1 }.thenBy { it.name.lowercase() })
                                .mapNotNull { aPath ->
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

                                        val outBaseName = if (ext == ".dlg-part")
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
            status.println("Writing $file...")
            erfFile.writeErf(file)
            status.println("Done")
        }
    }

    private suspend fun processPaths(rec: List<Rec>, index: AtomicInteger, size: Int): Path {
        rec.firstOrNull { !GffFactory.isJsonFile(it.aPath) }?.let { rec1 ->
            buildCommand.infoSuspend { "%5d / %5d : Adding %s".format(index.incrementAndGet(), size, rec1.aPath) }
            return rec1.aPath
        }

        // target artifact is the same for all
        val targ = dir.resolve("target").resolve(rec.first().loc).resolve(rec.first().baseName)
        if (targ.exists() && !rec.any { it.aPath.getLastModifiedTime() > targ.getLastModifiedTime() }) {
            buildCommand.infoSuspend { "%5d / %5d : Adding cached %s".format(index.incrementAndGet(), size, targ) }
            return targ
        }

//        buildCommand.logDebug { "size = ${rec.size}" }
        if (buildCommand.traceEnabled) {
            rec.forEach { aa ->
                buildCommand.logTrace { "$aa" }
            }
        }

        if (rec.any {
                it.baseName.lowercase().endsWith(".dlg-part.json5") ||
                        it.baseName.lowercase().endsWith(".dlg-part.json") ||
                        it.baseName.lowercase().endsWith(".dlg-part")
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
            rec.filter {
                it.baseName.lowercase().endsWith(".dlg-part.json5") ||
                        it.baseName.lowercase().endsWith(".dlg-part.json") ||
                        it.baseName.lowercase().endsWith(".dlg-part")
            }.forEach { aaa: Rec ->
                buildCommand.logDebug { "two" }
                buildCommand.infoSuspend { "%5d / %5d : Compiling and adding %s".format(index.incrementAndGet(), size, aaa.aPath) }
                readPart(obj, aaa.aPath.toUri().toURL())
            }
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
        buildCommand.infoSuspend { "%5d / %5d : Compiling and adding %s".format(index.incrementAndGet(), size, arec.aPath) }
        val factory: GffFactory<out GffObj> = GffFactory.getFactoryForFileName(arec.aPath.name)
            ?: throw RuntimeException("Could not find factory for ${arec.aPath.name}")

        val obj1 = factory.parseJson(arec.aPath)

        targ.parent?.let { d ->
            if (!Files.isDirectory(d)) Files.createDirectories(d)
        }
        return obj1
    }

}

fun readPart(adria: Dlg, res: URL) {
    val mapper = getBaseMapper()
    val root = mapper.readValue(res, Dlgs::class.java)

    val npc = mutableListOf<DlgsNpcResponse>()
    val pc = mutableListOf<DlgsPcChoice>()

    var entryNum = adria.entryList!!.size
    var replyNum = adria.replyList!!.size

    fun flatten(obj: DlgsNpcResponse) {
        npc.add(obj)
        obj.userChoices?.let { userList: ArrayList<DlgsPcChoice> ->
            userList.forEach { aUser ->
                pc.add(aUser)
                aUser.index = replyNum++
            }
            userList.forEach { user ->
                user.response?.let { response: DlgsNpcResponse ->
                    response.index = entryNum++
                    flatten(response)
                }
            }
        }
    }

    // find root in dlg we are merging into
    val foo2: DlgEntry = adria.entryList!!.firstOrNull {
        it.text?.strings?.first()?.string == root.npcSays
    } ?: throw RuntimeException("Could not find NPC starting statement '${root.npcSays}' with active='${root.appearIfScript}'")

    root.index = adria.entryList!!.indexOf(foo2)
    flatten(root)

    fun List<DlgsNpcResponse>.toEntryList(): List<DlgEntry> {
        return this.map { response ->
            DlgEntry().also { e ->
                val str = CExoLocString(response.npcSays!!)
                e.text = str
                e.script = response.onAppearScript
                e.responseId = response.responseId
                e.repliesList = response.userChoices?.map { dlgUserChoice ->
                    val dlgFoo = DlgFoo()
                    dlgFoo.active = dlgUserChoice.appearIfScript
                    dlgFoo.index = dlgUserChoice.index?.toUInt()
                    dlgFoo.isChild = false
                    dlgFoo
                }
            }
        }
    }

    fun List<DlgsPcChoice>.toReplyList(): List<DlgReply> {
        return this.map { xxx: DlgsPcChoice ->
            DlgReply().also { e ->
                val text = xxx.userSays
                e.text = CExoLocString(xxx.userSays!!)
                e.script = xxx.onSelectScript
                if (xxx.includeResponseId != null) {
                    e.includeResponseId = xxx.includeResponseId
                } else {
                    xxx.response?.let { response: DlgsNpcResponse ->
                        val foo = DlgFoo()
                        foo.active = response.appearIfScript
                        foo.index = response.index?.toUInt()
                        foo.isChild = false
                        e.entriesList = listOf(foo)
                    }
                }
            }
        }
    }

    val npcEntryList = npc.toEntryList()
    val pcReplyList = pc.toReplyList()

    pcReplyList.filter { it.includeResponseId != null }.forEach { reply: DlgReply ->
        val one: DlgsNpcResponse = npc.first { it.responseId == reply.includeResponseId }

        val foo = DlgFoo()
        foo.isChild = true
        foo.index = one.index?.toUInt()
        foo.active = one.appearIfScript

        reply.entriesList = listOf(foo)
    }

    pcReplyList.filter { it.includeResponseId != null }.forEach { reply: DlgReply ->
        val one: DlgsNpcResponse = npc.first { it.responseId == reply.includeResponseId }

        val foo = DlgFoo()
        foo.isChild = true
        foo.index = one.index?.toUInt()
        foo.active = one.appearIfScript

        reply.entriesList = listOf(foo)
    }

    adria.entryList = adria.entryList!! + npcEntryList.drop(1)
    adria.replyList = adria.replyList!! + pcReplyList

    var index = 0
    adria.entryList?.forEach { it.entryIndex = index++ }
    index = 0
    adria.replyList?.forEach { it.replyIndex = index++ }

    root.userChoices?.forEachIndexed { index: Int, userChoice ->
        patchIn(pcReplyList[index], pc, adria, root)
    }

}

fun patchIn(first: DlgReply, userChoiceList: MutableList<DlgsPcChoice>, adria: Dlg, obj: DlgsNpcResponse) {
    val userChoice = userChoiceList.firstOrNull { it.index == first.replyIndex!! }

    val foo = DlgFoo()
    foo.index = first.replyIndex!!.toUInt()
    foo.active = userChoice?.appearIfScript

    // look for top-level starting entry nodes
    val index = adria.startingList!!.firstOrNull {
        (it.active ?: "") == (obj.appearIfScript ?: "") && // allow blank to match null
                adria.entryList!![it.index!!.toInt()].text?.strings?.first()?.string == obj.npcSays
    }?.index?.toInt() ?: adria.replyList!!.firstNotNullOfOrNull {
        it.entriesList?.firstOrNull { dlgFoo: DlgFoo ->
            (dlgFoo.active ?: "") == (obj.appearIfScript ?: "") && // allow blank to match null
                    adria.entryList!![dlgFoo.index!!.toInt()].text?.strings?.first()?.string == obj.npcSays
        }
    }?.index?.toInt() ?: throw RuntimeException("Could not find NPC starting statement '${obj.npcSays}' with active='${obj.appearIfScript}'")

//    val foo5: DlgEntry = adria.entryList!!.firstOrNull {
//        it.text?.strings?.first()?.string == obj.npcSays
//    } ?: throw RuntimeException("Could not find NPC starting statement '${obj.npcSays}' with active='${obj.appearIfScript}'")

    val foo2 = adria.entryList!![index]
    foo2.repliesList = foo2.repliesList!! + listOf(foo)
}
