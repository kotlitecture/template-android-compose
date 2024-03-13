@file:Suppress("UNCHECKED_CAST")

package core.data.datasource.http

import core.data.datasource.DataSource
import core.data.misc.extensions.globalSharedFlow
import core.data.misc.extensions.isCancellationException
import core.data.misc.utils.GsonUtils
import core.data.misc.utils.SslUtils
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.BrowserUserAgent
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.gson.GsonWebsocketContentConverter
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.retry
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.tinylog.Logger
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class HttpSource(
    private val retries: Int = 3,
    private val timeout: Long = 15_000L,
    private val retryInterval: Long = 3_000L,
    private val wsStopTimeout: Long = 60_000L,
    private val interceptors: List<Interceptor> = emptyList()
) : DataSource {

    private val flowCache = ConcurrentHashMap<Any, Flow<*>>()

    val okhttp by lazy {
        withDefaults(
            OkHttpClient.Builder(),
            interceptors
        ).build()
    }

    val ktor by lazy {
        HttpClient(OkHttp) {
            engine {
                config {
                    withDefaults(this, interceptors)
                }
            }
            install(ContentNegotiation) {
                gson {
                    GsonUtils.configure(this)
                }
            }
            install(WebSockets) {
                contentConverter = GsonWebsocketContentConverter()
            }
            install(HttpRedirect)
            install(HttpTimeout) {
                requestTimeoutMillis = timeout
                connectTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
            install(HttpRequestRetry) {
                retryOnServerErrors(retries)
                retryOnException(retries, true)
            }
            BrowserUserAgent()
            defaultRequest {
                headers {
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }

    fun clear() {
        flowCache.clear()
    }

    fun <T> get(key: Any, collector: suspend FlowCollector<T>.() -> Unit): Flow<T> {
        val stopTimeout = wsStopTimeout
        val cached = flowCache.computeIfAbsent(key) {
            globalSharedFlow(stopTimeout) {
                try {
                    Logger.debug("[HttpSource] collect :: {} - {}", key::class.java, key)
                    collector()
                } catch (th: Throwable) {
                    Logger.error(th, "[HttpSource] error :: {} - {}", key::class.java, key)
                    flowCache.remove(key)
                }
            }.withRetry(key)
        }
        Logger.debug("[HttpSource] cached :: {} - {}", flowCache.size, key)
        return cached as Flow<T>
    }

    private fun withDefaults(
        builder: OkHttpClient.Builder,
        interceptors: List<Interceptor>
    ): OkHttpClient.Builder {
        val logger = HttpLoggingInterceptor(Logger::debug)
            .also { it.setLevel(HttpLoggingInterceptor.Level.BODY) }
        interceptors.forEach(builder::addInterceptor)
        return SslUtils.withSslConfig(builder)
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .connectTimeout(timeout, TimeUnit.MILLISECONDS)
            .pingInterval(timeout, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(logger)
            .retryOnConnectionFailure(true)
    }

    private fun <T> Flow<T>.withRetry(key: Any): Flow<T> {
        return retry {
            flowCache.remove(key)
            Logger.error(it, "[HttpSource] :: withRetry")
            !it.isCancellationException().also { delay(retryInterval) }
        }
    }

}