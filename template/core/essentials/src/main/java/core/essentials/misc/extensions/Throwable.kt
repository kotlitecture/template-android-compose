package core.essentials.misc.extensions

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.utils.unwrapCancellationException
import java.util.concurrent.CancellationException

fun Throwable.isIgnoredException(): Boolean {
    return isCancellationException() || isHttpTimeoutException()
}

fun Throwable.isCancellationException(): Boolean {
    return unwrapCancellationException() is CancellationException
}

fun Throwable.isHttpTimeoutException(): Boolean {
    val exception = this
    return exception is HttpRequestTimeoutException ||
        exception is ConnectTimeoutException ||
        exception is SocketTimeoutException
}