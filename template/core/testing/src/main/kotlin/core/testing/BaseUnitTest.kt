package core.testing

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

abstract class BaseUnitTest {

    protected fun performTest(block: suspend CoroutineScope.() -> Unit) = runBlocking(Dispatchers.IO) {
        block()
    }

}