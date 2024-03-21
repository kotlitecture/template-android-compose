package core.data.datasource.http

import com.google.gson.annotations.SerializedName
import core.data.datasource.http.okhttp.OkHttpSource
import core.data.misc.utils.GsonUtils
import core.testing.BaseUnitTest
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.websocket.wss
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.websocket.close
import io.ktor.websocket.readBytes
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import okhttp3.Request
import org.junit.Assert
import kotlin.test.Test

class OkHttpSourceTest : BaseUnitTest() {

    private val httpSource = OkHttpSource()
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
        val source = OkHttpSource(timeout = 10L)
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