package app.feature.webtonative

import androidx.compose.runtime.Composable
import core.ui.mvvm.provideViewModel
import workflow.webtonative.WebToNativeLayout

@Composable
fun WebToNativeScreen(data: WebToNativeDestination.Data) {
    val viewModel: WebToNativeViewModel = provideViewModel()
    WebToNativeLayout(
        onBack = viewModel::onBack,
        onReady = { it.loadUrl(data.url) }
    )
}