package app.userflow.webtonative

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.webkit.ConsoleMessage
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView
import core.ui.misc.DisplayUnit
import core.ui.provideViewModel
import org.tinylog.Logger

@SuppressLint("JavascriptInterface")
@Composable
fun WebToNativeLayout(
    modifier: Modifier = Modifier,
    viewModel: WebToNativeViewModel = provideViewModel(),
    isDarkMode: @Composable () -> Boolean = { isSystemInDarkTheme() },
    onBack: () -> Unit = viewModel::onBack,
    onPageStarted: (url: String) -> Unit = {},
    onPageFinished: (url: String) -> Unit = {},
    onResourceLoading: (url: String) -> Unit = {},
    shouldOverrideUrlLoading: (request: WebResourceRequest) -> Boolean = { false },
    onReady: (webView: WebView) -> Unit
) {
    DarkModeHandler(viewModel = viewModel, isDarkMode = isDarkMode)
    WebHistoryHandler(viewModel = viewModel, onBack = onBack)
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val webViewHeight = remember { mutableStateOf(0) }
        AndroidView(modifier = Modifier
            .fillMaxSize()
            .heightIn(min = Dp.Infinity)
            .statusBarsPadding()
            .navigationBarsPadding()
            .onSizeChanged { webViewHeight.value = it.height },
            factory = { context ->
                viewModel.loadedStore.set(false)
                WebView(context).apply {
                    viewModel.webViewStore.set(this)
                    addJavascriptInterface(viewModel, WebToNative.ID)
                    webViewClient = object : WebViewClient() {
                        override fun onPageStarted(
                            view: WebView?,
                            url: String,
                            favicon: Bitmap?
                        ) {
                            viewModel.loadedStore.set(false)
                            onPageStarted(url)
                            super.onPageStarted(view, url, favicon)
                        }

                        override fun shouldInterceptRequest(
                            view: WebView?,
                            request: WebResourceRequest
                        ): WebResourceResponse? {
                            onResourceLoading(request.url.toString())
                            return super.shouldInterceptRequest(view, request)
                        }

                        override fun onPageFinished(view: WebView?, url: String) {
                            Logger.debug("onPageFinished :: {}", url)
                            val height = DisplayUnit.PX.toDp(webViewHeight.value).toInt()
                            evaluateJavascript(
                                """
                                        javascript:(function() {
                                            document.body.style.height='${height}px';
                                        })()
                                """.trimIndent(),
                                null
                            )
                            val id = url.hashCode().toLong()
                            postVisualStateCallback(id, object : WebView.VisualStateCallback() {
                                override fun onComplete(requestId: Long) {
                                    if (id == requestId) {
                                        onPageFinished(url)
                                    }
                                }
                            })
                            viewModel.loadedStore.set(true)
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest
                        ): Boolean {
                            Logger.debug("shouldOverrideUrlLoading :: {}", request.url)
                            return shouldOverrideUrlLoading(request)
                        }

                        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                            Logger.debug("onReceivedError :: {}", error?.description)
                            super.onReceivedError(view, request, error)
                        }

                        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                            Logger.debug("onReceivedSslError :: {}", error?.url)
                            super.onReceivedSslError(view, handler, error)
                        }

                        override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                            Logger.debug("onReceivedHttpError :: {}", errorResponse?.reasonPhrase)
                            super.onReceivedHttpError(view, request, errorResponse)
                        }
                    }
                    webChromeClient = object : WebChromeClient() {
                        override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                            Logger.debug("onConsoleMessage :: {}", consoleMessage.message())
                            return super.onConsoleMessage(consoleMessage)
                        }
                    }
                    settings.apply {
                        loadWithOverviewMode = true
                        builtInZoomControls = false
                        displayZoomControls = false
                        offscreenPreRaster = true
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        databaseEnabled = true
                        useWideViewPort = true
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        setRenderPriority(WebSettings.RenderPriority.HIGH)
                        cacheMode = WebSettings.LOAD_DEFAULT
                        setSupportMultipleWindows(false)
                        setSupportZoom(false)
                    }
                    onReady(this)
                }
            }
        )
    }
}

@Composable
private fun DarkModeHandler(
    viewModel: WebToNativeViewModel,
    isDarkMode: @Composable () -> Boolean
) {
    val webSettings = viewModel.webViewStore.asStateValue()?.settings ?: return
    val isDark = isDarkMode.invoke()
    LaunchedEffect(webSettings, isDark) {
        viewModel.onSetDarkMode(isDark)
    }
}

@Composable
private fun WebHistoryHandler(
    viewModel: WebToNativeViewModel,
    onBack: () -> Unit,
) {
    BackHandler {
        val webView = viewModel.webViewStore.get()
        if (webView != null && webView.canGoBack()) {
            webView.goBack()
        } else {
            onBack()
        }
    }
}