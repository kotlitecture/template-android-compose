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
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * A data source implementation that utilizes OkHttp as the underlying HTTP client.
 *
 * @param retries The number of retries to attempt in case of network errors.
 * @param timeout The timeout value for network operations, in milliseconds.
 * @param retryInterval The interval between retry attempts, in milliseconds.
 * @param wsStopTimeout The timeout for WebSocket connections to stop gracefully, in milliseconds.
 * @param interceptors List of interceptors to be applied to the OkHttp client.
 */
class HttpSource(
    private val retries: Int = 0,
    private val timeout: Long = 15_000L,
    private val retryInterval: Long = 3_000L,
    private val wsStopTimeout: Long = 60_000L,
    private val interceptors: List<Interceptor> = emptyList()
) : DataSource {

    /** Cache to store and manage flow instances. */
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
            if (retries > 0) {
                install(HttpRequestRetry) {
                    retryOnServerErrors(retries)
                    retryOnException(retries, true)
                }
            }
            BrowserUserAgent()
            defaultRequest {
                headers {
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }

    /**
     * Clears the flow cache.
     */
    fun clear() {
        flowCache.clear()
    }

    /**
     * Retrieves data as a flow from the specified key, optionally retrying on failure.
     *
     * This method can be used to obtain WebSocket connections, cache them for the same key,
     * and keep them open until there are no active subscribers within [wsStopTimeout] milliseconds.
     *
     * @param key The key associated with the data.
     * @param collector The collector to collect data into the flow.
     * @return A flow of the retrieved data.
     */
    fun <T> get(key: Any, collector: suspend FlowCollector<T>.() -> Unit): Flow<T> {
        val stopTimeout = wsStopTimeout
        val cached = flowCache.computeIfAbsent(key) {
            globalSharedFlow(stopTimeout) {
                try {
                    collector()
                } catch (th: Throwable) {
                    flowCache.remove(key)
                }
            }.withRetry(key)
        }
        return cached as Flow<T>
    }

    /**
     * Adds default interceptors and configurations to the OkHttp builder.
     *
     * @param builder The OkHttpClient.Builder instance to configure.
     * @param interceptors List of interceptors to be applied.
     * @return The configured OkHttpClient.Builder instance.
     */
    private fun withDefaults(
        builder: OkHttpClient.Builder,
        interceptors: List<Interceptor>
    ): OkHttpClient.Builder {
        interceptors.forEach(builder::addInterceptor)
        return withSslConfig(builder)
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .connectTimeout(timeout, TimeUnit.MILLISECONDS)
            .pingInterval(timeout, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
    }

    /**
     * Adds retry functionality to the flow with a specified key.
     *
     * @param key The key associated with the flow.
     * @return A flow with retry functionality.
     */
    private fun <T> Flow<T>.withRetry(key: Any): Flow<T> {
        return retry {
            flowCache.remove(key)
            !it.isCancellationException().also { delay(retryInterval) }
        }
    }

    /**
     * Adds SSL configuration to the OkHttpClient builder.
     *
     * @param builder The OkHttpClient.Builder instance to configure.
     * @return The configured OkHttpClient.Builder instance.
     */
    private fun withSslConfig(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        val factory = SslUtils.getSocketFactory() ?: return builder
        return builder
            .sslSocketFactory(factory, SslUtils.getTrustManager())
            .hostnameVerifier(SslUtils.getHostnameVerifier())
    }

}