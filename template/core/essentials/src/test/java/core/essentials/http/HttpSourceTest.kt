package core.essentials.http

import com.google.gson.annotations.SerializedName
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import core.essentials.AbstractTest
import org.junit.jupiter.api.Assertions
import kotlin.test.Test

class HttpSourceTest : AbstractTest() {

    private val ktor by lazy { HttpSource().ktor }

    private data class IpData(
        @SerializedName("ip")
        val ip: String
    )

    @Test
    fun `check simple get`() = performTest {
        val data = ktor.get("https://api64.ipify.org?format=json").body<IpData>()
        val text = ktor.get("https://api64.ipify.org").body<String>()
        Assertions.assertEquals(data.ip, text)
    }

    @Test
    fun `check read timeout is applied`() = performTest {
        val source = HttpSource(timeout = 10L)
        try {
            source.ktor.get("https://api64.ipify.org?format=json").body<IpData>()
            Assertions.fail()
        } catch (e: Exception) {
            e as HttpRequestTimeoutException
        }
    }

}