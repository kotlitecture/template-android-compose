package core.ui.block

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable

@Composable
@NonRestartableComposable
fun VisibleWithScale(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut(),
        visible = visible
    ) {
        content()
    }
}