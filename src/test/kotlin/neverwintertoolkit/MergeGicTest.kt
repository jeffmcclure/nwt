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

            val gicPath = gicDir.resolve(gitPath.name.removeSuffix(".git.json5") + ".gic.json5")
            println(gicPath)
            val gicFactory =
                GffFactory.getFactoryForFileName(gicPath) ?: throw RuntimeException("cannot find factory for $gicPath")
            val gic = gicFactory.parseJson(gicPath) as Gic

            git.applyGic(gic)


            gic.toJson()


            //val jsonSettings = JsonSettings(pretty = true, simplifyJson = true)
            globalSettings.simplifyJson = true
            gitPath.parent.resolve(gitPath.name).writeText(git.toJson())
        }
    }
}
