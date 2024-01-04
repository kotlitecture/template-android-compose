package core.ui.block

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
@NonRestartableComposable
fun IconBlock(
    modifier: Modifier = Modifier,
    model: Any?,
    tint: Color,
    size: Dp
) {
    when (model) {
        is ImageVector -> {
            Icon(
                modifier = modifier.size(size),
                contentDescription = null,
                imageVector = model,
                tint = tint
            )
        }

        is Int -> {
            Icon(
                modifier = modifier.size(size),
                painter = painterResource(model),
                contentDescription = null,
                tint = tint
            )
        }

        is Color -> {
            Box(
                modifier = modifier
                    .size(size)
                    .background(model)
            )
        }

        else -> {
            Box(
                modifier = modifier
                    .size(size)
            )
        }
    }
}

@Composable
@NonRestartableComposable
fun SquaredIconBlock(
    modifier: Modifier = Modifier,
    model: Any?,
    tint: Color,
    tone: Color = tint,
    toneAlpha: Float = 0.1f,
    size: Dp,
    ignoreColor: Boolean = false,
    iconSizeMultiplier: Float = 0.55f
) {
    ChipBlock(
        modifier = modifier,
        toneAlpha = toneAlpha,
        tone = tone,
        size = size
    ) {
        when (model) {
            is String -> {
                ImageBlock(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    model = model
                )
            }

            else -> {
                IconBlock(
                    tint = tint.takeIf { !ignoreColor } ?: Color.Unspecified,
                    size = size.times(iconSizeMultiplier),
                    model = model
                )
            }
        }
    }
}

@Composable
@NonRestartableComposable
fun RoundedIconBlock(
    modifier: Modifier = Modifier,
    model: Any,
    tint: Color,
    size: Dp
) {
    ChipBlock(
        modifier = modifier,
        cornerRadius = size.times(0.5f),
        tone = tint,
        size = size
    ) {
        IconBlock(
            size = size.times(0.55f),
            model = model,
            tint = tint
        )
    }
}