package neverwintertoolkit.con

import neverwintertoolkit.file.gff.GffFactory
import neverwintertoolkit.file.gff.TestGffFile
import neverwintertoolkit.globalSettings
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Random
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import kotlin.io.path.exists
import kotlin.io.path.name
import kotlin.math.absoluteValue
import kotlin.test.AfterTest
import kotlin.test.DefaultAsserter
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


open class BaseTest(var debugTestMode: Boolean = false) {

    companion object {
        init {
            System.setProperty("WORK_DIR", "build")
            globalSettings.strictTestingMode = true
        }

        private val logger = org.slf4j.LoggerFactory.getLogger(BaseTest::class.java)!!

    }

    init {
        globalSettings.simplifyJson = false
    }

    /**
     * set to true when you are doing desktop testing.  When true:
     *   o) Keeps around temporary files after tests complete
     *   o) Disables test concurrency
     *   o) Uses known file names vs system generated names
     */


    val list = mutableListOf<Path>()
    val dirList = mutableListOf<Path>()

    fun getFile(base: String? = null, suffix: String = ""): Path {
        val base = base ?: Random().nextLong().absoluteValue.toString()

        val newFile = if (debugTestMode)
            Paths.get("build").resolve(base + suffix).also {
                Files.deleteIfExists(it)
            }
        else {
            Files.createTempFile(base, suffix).also {
                Files.deleteIfExists(it)
            }
        }

        list.add(newFile)
        return newFile
    }

    fun getDir(): Path {
        val newFile = Files.createTempDirectory(Path.of("build"), "tmp")
        dirList.add(newFile)
        return newFile
    }

    @AfterTest
    fun afterTest() {
        val action = if (debugTestMode) "skipping" else "deleting"
        list.forEach {
            logger.error("afterAll() $action {}", it)
        }
        if (!debugTestMode)
            list.forEach(Files::deleteIfExists)

        dirList.forEach {
            logger.error("afterAll() $action {}", it)
            if (!debugTestMode)
                it.deleteDirectory()
        }
    }

    fun f(namex: String): Path {
        val name = "src/test/resources$namex"
        val file = Path.of(name)
        DefaultAsserter.assertTrue("$name does not exist", file.exists())
        return file
    }

    fun testGffFile(
        gffFile: Path,
        compareBinary: Boolean = false,
        dmpFileCompare: Boolean = false,
        logMessage: String = "",
        compareStructId: Boolean = false,
        compareAllJson: Boolean = false,
        compareStructCounts: Boolean = true,
        regex: List<Regex> = listOf()
    ) {
        logger.info("{}jsonTest({})", logMessage, gffFile)
        val gffFactory = GffFactory.getFactoryForFileName(gffFile) ?: throw RuntimeException("Could not create factory for ${gffFile.name}")
        logger.info("gffFactory = {}", gffFactory::class.java.name)

        val dat = TestGffFile(
            gffFactory, gffFile,
            gffFileCompare = compareBinary,
            debugTestMode = debugTestMode,
            compareRawJson = compareStructId,
            compareAllJson = compareAllJson,
            compareStructCounts = compareStructCounts,
            regex = regex
        )
        try {
            dat.jsonTest { msg, e ->
                if (e != null)
                    throw RuntimeException(msg, e)
                else
                    throw RuntimeException(msg)
            }
        } finally {
            if (!debugTestMode)
                dat.deleteFiles()
        }
    }

    fun testGffFiles(
        dir: Path, extension: String,
        compareStructId: Boolean = false,
        compareAllJson: Boolean = false,
        skip: Set<String> = setOf()
    ) {
        val list = Files.list(dir).filter {
            it.name.lowercase().endsWith(extension) && !skip.contains(it.name)
        }.toList()
        val log = "%,5d of " + "%,5d ".format(list.size)

        if (debugTestMode) {
            val list2 = list.mapIndexed { index, file ->
                testGffFile(
                    file, compareBinary = false, dmpFileCompare = false,
                    compareAllJson = compareAllJson,
                    compareStructId = compareStructId, logMessage = log.format(index + 1)
                )
            }
            assertNotEquals(0, list2.size) // ensure at least one file tested
        } else {
            val threadCount = 3
            logger.info("numProcessors={}", { Runtime.getRuntime().availableProcessors() })
            logger.info("threadCount={}", threadCount)
            val executor = Executors.newFixedThreadPool(threadCount)
            val fails = mutableListOf<Pair<Path, Throwable>>()
            val cnt = AtomicLong()
            list.mapIndexed { index, file ->
                executor.submit {
                    try {
                        testGffFile(
                            file, compareBinary = false, dmpFileCompare = false,
                            compareAllJson = compareAllJson,
                            compareStructId = compareStructId, logMessage = log.format(index + 1)
                        )
                    } catch (e: Throwable) { // need to catch TODO()
                        fails.add(Pair(file, e))
//                    if (debugTestMode) if (cnt.incrementAndGet() > 10) executor.shutdownNow()
                        logger.error("index={} path={}", index, file, e)
                    }
                }
            }

            executor.shutdown()
            executor.awaitTermination(1, TimeUnit.HOURS)
            fails.forEachIndexed { index, (path, exception) ->
                logger.error("index={} path={}", index, path, exception)
            }
            fails.forEachIndexed { index, (path, exception) ->
                logger.error("index={} path={}", index, path)
            }
            logger.info("list.size={}", list.size)
            logger.info("passed={}", list.size - fails.size)
            assertEquals(0, fails.size)
            assertNotEquals(0, list.size)
        }
    }
}

fun Path.deleteDirectory() {
    Files.walk(this).use { dirStream ->
        dirStream
            .map { obj: Path -> obj.toFile() }
            .sorted(Comparator.reverseOrder())
            .forEach { obj: File -> obj.delete() }
    }
}

