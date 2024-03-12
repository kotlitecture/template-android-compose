package app.feature.webtonative

import androidx.compose.runtime.Composable
import core.ui.provideViewModel
import app.userflow.webtonative.WebToNativeLayout

@Composable
fun WebToNativeScreen(data: WebToNativeDestination.Data) {
    val viewModel: WebToNativeViewModel = provideViewModel()
    WebToNativeLayout(
        onBack = viewModel::onBack,
        onReady = { it.loadUrl(data.url) }
    )
}