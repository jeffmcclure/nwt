package neverwintertoolkit.command

import kotlinx.coroutines.sync.Semaphore
import picocli.CommandLine
import java.io.OutputStream
import java.io.PrintStream

open class GlobalOptions(prints: PrintStream? = null) {
    protected val logger = org.slf4j.LoggerFactory.getLogger(this::class.java)!!

    @CommandLine.Spec
    lateinit var spec: CommandLine.Model.CommandSpec // injected by picocli

    @CommandLine.Option(names = ["-q", "-0"], required = false, description = ["Suppress status output"])
    var qOption = false

    @CommandLine.Option(names = ["-r", "-1"], required = false, description = ["Regular level of status verbosity"])
    var rOption = false

    @CommandLine.Option(names = ["-v", "-2"], required = false, description = ["Verbose status output"])
    var vOption = false

    @CommandLine.Option(names = ["-w", "-3"], required = false, description = ["More verbose status output"])
    var wOption = false

    open fun copyTo(target: GlobalOptions) {
        target.qOption = qOption
        target.rOption = rOption
        target.vOption = vOption
        target.wOption = wOption
    }

    val statusLogLevel: Int by lazy {
        when {
            wOption -> 3
            vOption -> 2
            rOption -> 1
            qOption -> 0
            else -> 1
        }
    }

    val infoEnabled: Boolean by lazy { statusLogLevel > 0 }
    val debugEnabled: Boolean by lazy { statusLogLevel > 1 }
    val traceEnabled: Boolean by lazy { statusLogLevel > 2 }

    fun logError(block: () -> String?) {
        if (statusLogLevel > -1) {
            //status.println(block())
            System.err.println(block())
        }
    }

    fun logInfo(block: () -> String) {
        if (statusLogLevel > 0) {
            status.println(block())
        }
    }

    fun logInfoNo(block: () -> String) {
        if (statusLogLevel > 0) {
            status.print(block())
            status.flush()
        }
    }

    val semaphore = Semaphore(1)
    suspend fun infoSuspend(block: () -> String) {
        if (statusLogLevel > 0) {
            semaphore.acquire()
            try {
                status.println(block())
            } finally {
                semaphore.release()
            }
        }
    }

    suspend fun logSuspend(block: () -> String) {
//        println("statusLogLevel: $statusLogLevel")
        if (statusLogLevel > 1) {
            debugSuspend(block)
        } else {
            infoSuspendNo(block)
        }
    }

    suspend fun infoSuspendNo(block: () -> String) {
        if (statusLogLevel > 0) {
            semaphore.acquire()
            try {
//                status.print("\r" + block())
//                status.print("\u001b[K") // ANSI escape code to clear to the end of the line
//                status.print("\r")
                status.print("\r${block()}\u001b[K\r")
            } finally {
                semaphore.release()
            }
        }
    }

    suspend fun debugSuspend(block: () -> String) {
        if (statusLogLevel > 1) {
            semaphore.acquire()
            try {
                status.println(block())
            } finally {
                semaphore.release()
            }
        }
    }

    fun logDebug(block: () -> String) {
        if (statusLogLevel > 1) status.println(block())
    }

    fun logTrace(block: () -> String) {
        if (statusLogLevel > 2) status.println(block())
    }

    @CommandLine.Option(names = ["-e"], required = false, description = ["print status to stderr"])
    var eOption = false

    @CommandLine.Option(names = ["-o"], required = false, description = ["print status to stdout (default)"], arity = "0")
    open var oOption: Boolean? = null

    val status: PrintStream by lazy {
        when {
            prints != null -> prints
            eOption -> System.err.also { logger.debug("statusx=System.err") }
            qOption -> PrintStream(OutputStream.nullOutputStream()).also { logger.debug("statusx=nullOutput") }
            oOption == null || oOption == true -> System.out.also { logger.debug("statusx=System.out") } // put last to handle default
            else -> {
                RuntimeException("Should not get here").printStackTrace()
                System.err
            }
        }
    }
}