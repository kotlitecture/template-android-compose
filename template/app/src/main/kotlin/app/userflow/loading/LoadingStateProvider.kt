package app.userflow.loading

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import core.ui.theme.material3.AppTheme
import core.ui.provideViewModel

@Composable
fun LoadingStateProvider() {
    val viewModel: LoadingViewModel = provideViewModel(activityScope = true)
    val isLoading = viewModel.isLoadingStore.asStateValueNotNull()
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
            strokeWidth = 3.dp,
            color = AppTheme.color.textPrimary
        )
    }
    BackHandler {}
}