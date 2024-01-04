package core.testing

import core.datasource.analytics.IAnalyticsSource
import core.datasource.config.IConfigSource
import core.di.TestAnalyticsSource
import core.di.TestConfigSource
import core.testing.MyUnitTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class MyUnitTestTest : MyUnitTest() {

    @Inject
    lateinit var configSource: IConfigSource

    @Inject
    lateinit var analyticsSource: IAnalyticsSource

    @Test
    fun `when injected config is test`() {
        Assert.assertTrue(configSource is TestConfigSource)
    }

    @Test
    fun `when injected analytics is test`() {
        Assert.assertTrue(analyticsSource is TestAnalyticsSource)
    }

}