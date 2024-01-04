package core.ui.block

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import core.ui.app.theme.AppTheme
import core.ui.misc.extension.withClickable

@Composable
@NonRestartableComposable
fun ActionButtonBlock(
    modifier: Modifier = Modifier,
    icon: Any?,
    haptic: Boolean = false,
    placeholder: Boolean = false,
    innerSizeRation: Float = 0.6f,
    outerSize: Dp = AppTheme.size.actionButtonSize,
    color: Color = AppTheme.color.iconPrimary,
    overlay: @Composable (BoxScope.() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    if (overlay != null) {
        Box(
            modifier = modifier
                .size(outerSize)
                .clip(CircleShape)
                .withPlaceholder(placeholder)
                .withClickable(onClick = onClick, haptic = haptic),
            contentAlignment = Alignment.Center
        ) {
            IconBlock(
                modifier = Modifier.scale(innerSizeRation),
                model = icon,
                tint = color,
                size = outerSize
            )
            overlay(this)
        }
    } else {
        IconBlock(
            modifier = modifier
                .size(outerSize)
                .clip(CircleShape)
                .withClickable(onClick = onClick, haptic = haptic)
                .scale(innerSizeRation)
                .withPlaceholder(placeholder)
            ,
            model = icon,
            tint = color,
            size = Dp.Unspecified
        )
    }
}