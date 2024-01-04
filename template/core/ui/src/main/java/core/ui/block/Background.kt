package core.ui.block

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
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
fun BackgroundBlock(
    modifier: Modifier = Modifier,
    value: Any,
    width: Dp,
    height: Dp = width,
) {
    when (value) {
        is ImageVector -> {
            Image(
                modifier = modifier.size(width, height),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                imageVector = value
            )
        }

        is Int -> {
            Image(
                modifier = modifier.size(width, height),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(value),
                contentDescription = null,
            )
        }

        is Color -> {
            Box(
                modifier = modifier
                    .size(width, height)
                    .background(value)
            )
        }

        else -> Unit
    }
}