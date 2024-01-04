@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)

package core.ui.misc.extension

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import core.ui.app.theme.AppTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Stable
fun Modifier.withClickable(
    haptic: Boolean = false,
    ripple: Boolean = true,
    enabled: Boolean = true,
    bounded: Boolean = true,
    onClick: (() -> Unit)?
): Modifier {
    if (onClick == null) return this
    return composed {
        val onClickHandler =
            if (haptic) {
                val view = LocalView.current
                {
                    view.hapticTap()
                    onClick.invoke()
                }
            } else {
                onClick
            }
        val indication = if (ripple) rememberRipple(bounded = bounded) else null
        clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = indication,
            onClick = onClickHandler,
            enabled = enabled
        )
    }
}

@Stable
fun Modifier.withBounceClickable(bounceScale: Float = 0.9f, onClick: () -> Unit) = composed {
    val scale = remember { mutableStateOf(1f) }
    val scaleAnimate = animateFloatAsState(scale.value, label = "", animationSpec = AppTheme.anim.springTaDam())
    this
        .graphicsLayer {
            scaleX = scaleAnimate.value
            scaleY = scaleAnimate.value
        }
        .pointerInput(onClick) {
            detectTapGestures(
                onPress = {
                    scale.value = bounceScale
                    awaitPointerEventScope {
                        val change = waitForUpOrCancellation()
                        scale.value = 1f
                        if (change != null) {
                            onClick.invoke()
                        }
                    }
                }
            )
        }
}

@Stable
fun Modifier.withRepeatableClick(
    interval: Long = 100,
    haptic: Boolean = true,
    onClick: () -> Boolean
) = composed {
    val isPressed = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val onClickHandler =
        if (haptic) {
            val view = LocalView.current
            {
                if (onClick.invoke()) {
                    view.hapticTap()
                }
            }
        } else {
            onClick
        }
    var job: Job? = null
    pointerInteropFilter { event ->
        when (event.action) {
            android.view.MotionEvent.ACTION_DOWN -> {
                isPressed.value = true
                job?.cancel()
                job = scope.launch {
                    var first = true
                    while (isActive && isPressed.value) {
                        onClickHandler()
                        if (first) {
                            delay(interval * 3)
                        } else {
                            delay(interval)
                        }
                        first = false
                    }
                }
            }

            android.view.MotionEvent.ACTION_UP -> {
                isPressed.value = false
                job?.cancel()
            }
        }
        true
    }
}

@Stable
fun Modifier.withCornerRadius(cornerRadius: Dp) = clip(RoundedCornerShape(cornerRadius))

@Stable
fun Modifier.withDashedBorder(width: Dp, radius: Dp, color: Color) =
    drawBehind {
        drawIntoCanvas {
            val paint = Paint()
                .apply {
                    strokeWidth = width.toPx()
                    this.color = color
                    style = PaintingStyle.Stroke
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                }
            it.drawRoundRect(
                width.toPx(),
                width.toPx(),
                size.width - width.toPx(),
                size.height - width.toPx(),
                radius.toPx(),
                radius.toPx(),
                paint
            )
        }
    }

@Stable
fun Modifier.withCornerRadius(top: Dp, bottom: Dp) = clip(
    RoundedCornerShape(
        topStart = top,
        topEnd = top,
        bottomStart = bottom,
        bottomEnd = bottom
    )
)

@Stable
fun Modifier.zIndexTop() = zIndex(1000f)

@Stable
fun Modifier.withBackground(color: @Composable () -> Color): Modifier {
    return composed {
        background(color())
    }
}

@Stable
fun Modifier.withTopCornerRadius(cornerRadius: @Composable () -> Dp) = composed {
    val radius = cornerRadius()
    clip(remember(radius) { RoundedCornerShape(topStart = radius, topEnd = radius) })
}

@Stable
fun Modifier.withTopCornerRadius(cornerRadius: Dp) =
    clip(RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius))

@Stable
fun Modifier.withBottomCornerRadius(cornerRadius: Dp) =
    clip(RoundedCornerShape(bottomStart = cornerRadius, bottomEnd = cornerRadius))

@Stable
fun Modifier.withPaddingHorizontal16() = padding(horizontal = 16.dp)

@Stable
fun Modifier.shakeWrong(enabled: MutableState<Boolean>): Modifier {
    return composed {
        if (enabled.value) {
            LocalContext.current.vibrateWrong()
        }
        shake(enabled)
    }
}

@Stable
fun Modifier.shake(enabled: MutableState<Boolean>): Modifier {
    return composed(
        factory = {
            val distance by animateFloatAsState(
                targetValue = if (enabled.value) -25f else 0f,
                animationSpec = repeatable(
                    iterations = 5,
                    animation = tween(durationMillis = 50, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                finishedListener = {
                    enabled.value = false
                },
                label = ""
            )
            Modifier
                .graphicsLayer {
                    translationX = if (enabled.value) distance else 0f
                }
        }
    )
}