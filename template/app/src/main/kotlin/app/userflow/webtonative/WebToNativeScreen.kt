package app.userflow.webtonative

import androidx.compose.runtime.Composable

@Composable
fun WebToNativeScreen(data: WebToNativeDestination.Data?) {
    WebToNativeLayout(onReady = { it.loadUrl(data?.url.orEmpty()) })
}