package core.ui.app.loading

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import core.ui.mvvm.provideViewModel
import org.tinylog.Logger

@Composable
fun LoadingBackHandler(id: String, onBack: () -> Unit) {
    val viewModel: LoadingViewModel = provideViewModel(singleInstance = true) {}
    Logger.debug("LoadingBackHandler :: init :: {} -> viewModel={}", id, viewModel)
    BackHandler {
        if (!viewModel.isLoading()) {
            Logger.debug("LoadingBackHandler :: invoke :: {} -> viewModel={}", id, viewModel)
            onBack.invoke()
        }
    }
}