package core.dataflow.datasource.http

import com.google.gson.annotations.SerializedName
import core.testing.MyUnitTest
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.get
import org.junit.Assert
import kotlin.test.Test

class HttpSourceTest : MyUnitTest() {

    private val ktor by lazy { HttpSource().ktor }

    private data class IpData(
        @SerializedName("ip")
        val ip: String
    )

    @Test
    fun `check simple get`() = performTest {
        val data = ktor.get("https://api64.ipify.org?format=json").body<IpData>()
        val text = ktor.get("https://api64.ipify.org").body<String>()
        Assert.assertEquals(data.ip, text)
    }

    @Test
    fun `check read timeout is applied`() = performTest {
        val source = HttpSource(timeout = 10L)
        try {
            source.ktor.get("https://api64.ipify.org?format=json").body<IpData>()
            Assert.fail()
        } catch (e: Exception) {
            e as HttpRequestTimeoutException
        }
    }

}