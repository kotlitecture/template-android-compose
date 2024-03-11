package core.testing

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.tinylog.kotlin.Logger
import kotlin.system.measureTimeMillis

/**
 * https://dagger.dev/hilt/testing.html
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(
    application = HiltTestApplication::class,
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.N]
)
abstract class MyHiltUnitTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun beforeTest() {
        hiltRule.inject()
    }

    protected open suspend fun doBeforeTest() {}

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