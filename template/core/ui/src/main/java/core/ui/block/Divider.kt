package core.ui.block

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.ui.app.theme.AppTheme

@Immutable
data class DividerBlockAppearance(
    val color: Color,
    val thickness: Dp
) {
    companion object {
        @Stable
        @Composable
        fun primary(color: Color = AppTheme.color.dividerPrimary) = DividerBlockAppearance(
            thickness = 1.dp,
            color = color,
        )

        @Stable
        @Composable
        fun secondary(color: Color = AppTheme.color.dividerSecondary) = DividerBlockAppearance(
            thickness = 1.dp,
            color = color,
        )
    }
}

@Composable
@NonRestartableComposable
fun DividerBlock(
    modifier: Modifier = Modifier,
    appearance: DividerBlockAppearance = DividerBlockAppearance.primary()
) {
    Divider(
        modifier = modifier.fillMaxWidth(),
        thickness = appearance.thickness,
        color = appearance.color
    )
}