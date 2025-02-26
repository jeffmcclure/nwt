package neverwintertoolkit

import neverwintertoolkit.con.BaseTest
import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.model.gic.Gic
import neverwintertoolkit.model.git.Git
import java.nio.file.Path
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.io.path.writeText
import kotlin.test.Test

class MergeGicTest : BaseTest() {

    @Test
    fun one() {
        val dir = Path.of("/home/jeffmcclure/d1/d/lot/src")
        val gitDir = dir.resolve("git")
        val gicDir = dir.resolve("gic")

        gitDir.listDirectoryEntries().filter { it.name.endsWith(".git.json5") }.forEach { gitPath ->
            println(gitPath)
            val gitFactory =
                GffFactory.getFactoryForFileName(gitPath) ?: throw RuntimeException("cannot find factory for $gitPath")
            val git = gitFactory.parseJson(gitPath) as Git
            println(git.javaClass.name)

            val gicPath = gicDir.resolve(gitPath.name.removeSuffix(".git.json5") + ".gic.json5")
            println(gicPath)
            val gicFactory =
                GffFactory.getFactoryForFileName(gicPath) ?: throw RuntimeException("cannot find factory for $gicPath")
            val gic = gicFactory.parseJson(gicPath) as Gic
            println(gic.javaClass.name)

            git.gicList = gic.list

            gic.creatureList?.forEachIndexed { index, gic ->
                git.creatureList?.get(index)?.gicComment = gic.comment
            }

            gic.doorList?.forEachIndexed { index, gic ->
                git.doorList?.get(index)?.gicComment = gic.comment
            }

            gic.placeableList?.forEachIndexed { index, gic ->
                git.placeableList?.get(index)?.gicComment = gic.comment
            }

            gic.soundList?.forEachIndexed { index, gic ->
                git.soundList?.get(index)?.gicComment = gic.comment
                git.soundList?.get(index)?.gicPlayInToolset = gic.playInToolset
            }

            gic.triggerList?.forEachIndexed { index, gic ->
                git.triggerList?.get(index)?.gicComment = gic.comment
            }

            gic.waypointList?.forEachIndexed { index, gic ->
                git.waypointList?.get(index)?.gicComment = gic.comment
            }

            gic.storeList?.forEachIndexed { index, gic ->
                git.storeList?.get(index)?.gicComment = gic.comment
            }

            gic.toJson()


            //val jsonSettings = JsonSettings(pretty = true, simplifyJson = true)
            globalSettings.simplifyJson = true
            gitPath.parent.resolve(gitPath.name + "-2.json5").writeText(git.toJson())

        }
    }
}
