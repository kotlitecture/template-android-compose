package app.userflow.loading

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import core.ui.provideViewModel
import core.ui.state.StoreState

@Composable
fun LoadingStateProvider(state: StoreState) {
    val viewModel: LoadingViewModel = provideViewModel()
    val isLoading = viewModel.isLoadingStore.asStateValueNotNull()
    LaunchedEffect(state) { viewModel.onBind(state) }
    if (!isLoading) return
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(40.dp),
            strokeWidth = 3.dp
        )
    }
    BackHandler {}
}