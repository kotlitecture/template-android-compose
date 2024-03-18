package core.data.misc.utils

import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

class SslUtils {

    @Suppress("CustomX509TrustManager")
    private class TrustAllCerts : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) = Unit
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) = Unit
        override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
    }

    companion object {

        private val trustAllCerts = TrustAllCerts()

        private val trustAllSslContext: SSLContext? by lazy {
            runCatching {
                val context = runCatching { SSLContext.getInstance("TLS") }.getOrElse { SSLContext.getDefault() }
                context.init(null, arrayOf(trustAllCerts), null)
                context
            }.getOrNull()
        }

        fun getHostnameVerifier(): HostnameVerifier = HostnameVerifier { _, _ -> true }
        fun getSocketFactory(): SSLSocketFactory? = trustAllSslContext?.socketFactory
        fun getTrustManager(): X509TrustManager = trustAllCerts
    }
}