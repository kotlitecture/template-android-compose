package core.ui.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import core.ui.app.theme.AppTheme
import core.ui.misc.extension.withCornerRadius

@Composable
@NonRestartableComposable
fun ChipBlock(
    modifier: Modifier = Modifier,
    size: Dp = Dp.Unspecified,
    cornerRadius: Dp = size.times(0.35f),
    tone: Color = AppTheme.materialColor.secondary,
    toneAlpha: Float = 0.2f,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .size(size)
            .withCornerRadius(cornerRadius)
            .background(tone.copy(alpha = toneAlpha)),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        content()
    }
}