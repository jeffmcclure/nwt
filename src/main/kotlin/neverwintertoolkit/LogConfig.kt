package neverwintertoolkit

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.Configurator.ExecutionStatus
import ch.qos.logback.classic.util.DefaultJoranConfigurator
import ch.qos.logback.core.joran.spi.JoranException
import ch.qos.logback.core.util.Loader
import ch.qos.logback.core.util.OptionHelper
import java.io.File

/**
 * Save 20ms of startup time on MacPro M1 by providing our own implementation.
 * see /src/main/resources/META-INF/services/ch.qos.logback.classic.spi.Configurator
 *
 * Also avoids conflicts with other default log config files lying around.
 */
class LogConfig : DefaultJoranConfigurator() {

    override fun configure(context: LoggerContext?): ExecutionStatus {
        val myClassLoader = Loader.getClassLoaderOfObject(this)
        var url = Loader.getResource("logback-neverwintertoolkit.xml", myClassLoader)

        if (url == null) {
            val logbackConfigFile = OptionHelper.getSystemProperty("logback.configurationFile")
            if (logbackConfigFile != null) {
                val f = File(logbackConfigFile)
                if (f.exists())
                    url = f.toURI().toURL()
            }
        }

        if (url != null) {
            try {
                this.configureByResource(url)
            } catch (e: JoranException) {
                e.printStackTrace()
            }
        }

        return ExecutionStatus.DO_NOT_INVOKE_NEXT_IF_ANY
    }
}
