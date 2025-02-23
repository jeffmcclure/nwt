package neverwintertoolkit.command

import neverwintertoolkit.globalSettings
import java.util.concurrent.Callable
import kotlin.system.exitProcess

abstract class BaseCommand : Callable<Int>, GlobalOptions() {

    abstract fun called(): Int
    override fun call(): Int {
        try {
            return called()
        } catch (e: Exception) {
            logger.error("error", e)
            if (vOption) {
                e.printStackTrace()
            } else {
                logError { e::class.java.name + " " + e.message }
            }

            if (globalSettings.strictTestingMode) {
                throw e
            } else {
                exitProcess(1)
            }
        }
    }
}
