package core.testing

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.tinylog.kotlin.Logger
import kotlin.system.measureTimeMillis

@RunWith(AndroidJUnit4::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.N]
)
abstract class MyAndroidTest {

    protected fun performTest(block: suspend CoroutineScope.() -> Unit) {
        runBlocking(Dispatchers.IO) {
            val time = measureTimeMillis { block() }
            Logger.debug("test duration :: {}", time)
        }
    }

    companion object {
        @JvmStatic
        @BeforeClass
        fun beforeClass() {
            FakeAndroidKeyStore.setup
        }
    }

}