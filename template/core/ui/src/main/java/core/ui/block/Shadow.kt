package core.ui.block

import android.graphics.BlurMaskFilter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.ui.app.theme.AppTheme
import core.ui.misc.extension.toPx

@Stable
fun Modifier.withShadow(
    elevation: Dp = 8.dp,
    shape: Shape? = null,
    spotColor: Color? = null
): Modifier {
    return composed {
        shadow(
            elevation = elevation,
            spotColor = spotColor ?: AppTheme.color.shadowPrimary,
            shape = shape ?: RoundedCornerShape(AppTheme.size.cornerMd),
        )
    }
}

@Stable
fun Modifier.withAdvancedShadow(
    color: Color = Color.Unspecified,
    spread: Float = 0f,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    withBlurRadius: @Composable () -> Dp = { 8.dp },
    withBorderRadius: @Composable () -> Dp = { 16.dp },
    withTopShadow: @Composable () -> Boolean = { true },
    withBottomShadow: @Composable () -> Boolean = { true }
): Modifier {
    return composed {
        val topShadow = withTopShadow()
        val bottomShadow = withBottomShadow()
        val blurRadius = withBlurRadius().toPx()
        val borderRadius = withBorderRadius().toPx()
        drawBehind {
            drawIntoCanvas {
                val paint = Paint()
                val frameworkPaint = paint.asFrameworkPaint()
                val spreadPixel = spread.dp.toPx()
                val leftPixel = (0f - spreadPixel) + offsetX.toPx()
                val topPixel =
                    if (topShadow) {
                        (0f - spreadPixel) + offsetY.toPx()
                    } else {
                        borderRadius + blurRadius
                    }
                val rightPixel = (this.size.width + spreadPixel)
                val bottomPixel =
                    if (bottomShadow) {
                        (this.size.height + spreadPixel)
                    } else {
                        this.size.height - borderRadius - blurRadius
                    }
                if (blurRadius != 0f) {
                    frameworkPaint.maskFilter =
                        (BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL))
                }
                frameworkPaint.color = color.toArgb()
                it.drawRoundRect(
                    left = leftPixel,
                    top = topPixel,
                    right = rightPixel,
                    bottom = bottomPixel,
                    radiusX = borderRadius,
                    radiusY = borderRadius,
                    paint
                )
            }
        }
    }
}
