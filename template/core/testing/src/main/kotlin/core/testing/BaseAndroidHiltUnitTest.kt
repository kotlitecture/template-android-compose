package core.testing

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

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
abstract class BaseAndroidHiltUnitTest : BaseAndroidUnitTest() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun beforeTest() {
        hiltRule.inject()
    }

}