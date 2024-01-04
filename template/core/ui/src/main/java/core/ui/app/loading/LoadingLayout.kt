package core.ui.app.loading

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import core.ui.R
import core.ui.app.theme.AppTheme
import core.ui.app.theme.blur
import core.ui.block.SmallTextSecondary
import core.ui.block.Spacer8
import core.ui.misc.extension.withCornerRadius
import core.ui.mvvm.provideViewModel

@Composable
fun LoadingLayout(content: @Composable () -> Unit) {
    val viewModel: LoadingViewModel = provideViewModel(singleInstance = true) {}
    content()
    LoadingStateHandler(viewModel)
    InternetStateHandler(viewModel)
}

@Composable
private fun LoadingStateHandler(viewModel: LoadingViewModel) {
    if (!viewModel.isLoading()) return
    Dialog(
        onDismissRequest = {}, properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .withCornerRadius(16.dp)
                .background(AppTheme.color.surfacePrimary)
                .size(80.dp)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center),
                strokeWidth = 3.dp,
                color = AppTheme.color.textPrimary
            )
        }
    }
}

@Composable
private fun InternetStateHandler(viewModel: LoadingViewModel) {
    val background = AppTheme.color.negative.blur()
    val textColor = AppTheme.color.white
    AnimatedVisibility(
        visible = !viewModel.isOnline(),
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer8()
            SmallTextSecondary(
                text = stringResource(R.string.error_internet), color = textColor
            )
            Spacer8()
        }
    }
}