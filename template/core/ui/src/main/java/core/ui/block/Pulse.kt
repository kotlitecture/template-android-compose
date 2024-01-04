package core.ui.block

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.ui.app.theme.AppTheme
import core.ui.misc.extension.toPx

@Composable
@NonRestartableComposable
fun PulsingCircle(
    modifier: Modifier = Modifier,
    color: Color = AppTheme.color.negative,
    radius: Dp = 4.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val pulse by remember { mutableStateOf(true) }
    val pulseMin = radius.times(1.2f)
    val pulseMax = radius.times(1.6f)
    val circleColor = if (pulse) color else AppTheme.color.textSecondary
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = pulseMin.toPx(),
        targetValue = pulseMax.toPx(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseRadius"
    )
    Canvas(modifier = modifier.size(pulseMax)) {
        val center = Offset(size.width / 2, size.height / 2)
        drawCircle(
            circleColor,
            radius = radius.toPx(),
            center = center
        )
        if (pulse) {
            drawCircle(
                circleColor.copy(alpha = 0.4f),
                radius = pulseRadius,
                center = center
            )
        }
    }
}