@file:OptIn(ExperimentalComposeUiApi::class)

package core.ui.block

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import core.ui.app.theme.AppTheme

@Immutable
data class PlaceholderAppearance(
    val width: Dp?,
    val cornerTopStart: Dp,
    val cornerTopEnd: Dp,
    val cornerBottomStart: Dp,
    val cornerBottomEnd: Dp,
    val colorPrimary: Color,
    val colorSecondary: Color,
    val consumeEvents: Boolean
) {
    companion object {
        @Stable
        @Composable
        fun default(
            width: Dp? = null,
            consumeEvents: Boolean = false,
            cornerRadius: Dp = AppTheme.size.cornerSm,
            colorPrimary: Color = AppTheme.color.placeholderPrimary,
            colorSecondary: Color = AppTheme.color.placeholderSecondary,
        ): PlaceholderAppearance = remember {
            PlaceholderAppearance(
                colorSecondary = colorSecondary,
                consumeEvents = consumeEvents,
                cornerTopStart = cornerRadius,
                cornerTopEnd = cornerRadius,
                cornerBottomStart = cornerRadius,
                cornerBottomEnd = cornerRadius,
                colorPrimary = colorPrimary,
                width = width
            )
        }

        @Stable
        @Composable
        fun noCorners(
            width: Dp? = null,
            consumeEvents: Boolean = false,
            colorPrimary: Color = AppTheme.color.placeholderPrimary,
            colorSecondary: Color = AppTheme.color.placeholderSecondary,
        ): PlaceholderAppearance = default(
            colorSecondary = colorSecondary,
            consumeEvents = consumeEvents,
            colorPrimary = colorPrimary,
            cornerRadius = 0.dp,
            width = width
        )

        @Stable
        @Composable
        fun topCorners(
            width: Dp? = null,
            consumeEvents: Boolean = false,
            cornerRadius: Dp = AppTheme.size.cornerSm,
            colorPrimary: Color = AppTheme.color.placeholderPrimary,
            colorSecondary: Color = AppTheme.color.placeholderSecondary,
        ): PlaceholderAppearance = remember {
            PlaceholderAppearance(
                colorSecondary = colorSecondary,
                consumeEvents = consumeEvents,
                cornerTopStart = cornerRadius,
                cornerTopEnd = cornerRadius,
                cornerBottomStart = 0.dp,
                cornerBottomEnd = 0.dp,
                colorPrimary = colorPrimary,
                width = width
            )
        }
    }
}

@Stable
fun Modifier.withPlaceholder(
    visible: Boolean,
    appearance: PlaceholderAppearance? = null
): Modifier {
    return if (visible) {
        composed {
            val appearanceRef = appearance ?: PlaceholderAppearance.default()
            var modifier = placeholder(
                visible = true,
                color = appearanceRef.colorPrimary,
                shape = RoundedCornerShape(
                    topStart = appearanceRef.cornerTopStart,
                    topEnd = appearanceRef.cornerTopEnd,
                    bottomStart = appearanceRef.cornerBottomStart,
                    bottomEnd = appearanceRef.cornerBottomEnd
                ),
                highlight = PlaceholderHighlight.fade(appearanceRef.colorSecondary)
            )
            if (appearanceRef.consumeEvents) {
                modifier = modifier.pointerInteropFilter { true }
            }
            if (appearanceRef.width != null) {
                modifier = modifier.width(appearanceRef.width)
            }
            modifier
        }
    } else {
        this
    }
}