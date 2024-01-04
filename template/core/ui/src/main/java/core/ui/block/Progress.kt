package core.ui.block

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.ui.app.theme.AppTheme

@Composable
@NonRestartableComposable
fun CircularProgressBlock(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(32.dp),
            trackColor = AppTheme.color.highlightTextPrimary,
            color = AppTheme.color.progressPrimary
        )
    }
}