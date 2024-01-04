package core.essentials

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.tinylog.kotlin.Logger
import kotlin.system.measureTimeMillis

abstract class AbstractTest {

    protected fun performTest(block: suspend CoroutineScope.() -> Unit) {
        runBlocking(Dispatchers.IO) {
            val time = measureTimeMillis { block() }
            Logger.debug("test duration :: {}", time)
        }
    }

}