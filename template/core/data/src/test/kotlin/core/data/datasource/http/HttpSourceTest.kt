package core.data.datasource.http

import com.google.gson.annotations.SerializedName
import core.data.misc.utils.GsonUtils
import core.testing.BaseUnitTest
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.request.get
import okhttp3.Request
import org.junit.Assert
import kotlin.test.Test

class HttpSourceTest : BaseUnitTest() {

    private val httpSource = HttpSource()
    private val okhttp = httpSource.okhttp
    private val ktor = httpSource.ktor

    private data class IpData(
        @SerializedName("ip")
        val ip: String
    )

    @Test
    fun `ktor - check simple get`() = performTest {
        val data = ktor.get("https://api64.ipify.org?format=json").body<IpData>()
        val text = ktor.get("https://api64.ipify.org").body<String>()
        Assert.assertEquals(data.ip, text)
    }

    @Test
    fun `ktor - check read timeout is applied`() = performTest {
        val source = HttpSource(timeout = 10L)
        try {
            source.ktor.get("https://api64.ipify.org?format=json").body<IpData>()
            Assert.fail()
        } catch (e: Exception) {
            e as HttpRequestTimeoutException
        }
    }

    @Test
    fun `okhttp - check simple get`() = performTest {
        val ip1 = okhttp
            .newCall(
                Request.Builder()
                    .url("https://api64.ipify.org?format=json")
                    .build()
            )
            .execute()
            .body!!
            .string()
            .let { GsonUtils.toObject(it, IpData::class.java) }!!
            .ip
        val ip2 = okhttp
            .newCall(
                Request.Builder()
                    .url("https://api64.ipify.org")
                    .build()
            )
            .execute()
            .body!!
            .string()
        Assert.assertEquals(ip1, ip2)
    }

}