package core.ui.block

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import coil.compose.rememberAsyncImagePainter
import core.ui.app.theme.AppTheme

@Composable
@NonRestartableComposable
fun ImageBlock(
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
    model: Any?
) {
    val painter = rememberAsyncImagePainter(
        placeholder = ColorPainter(AppTheme.color.placeholderPrimary),
        error = ColorPainter(AppTheme.color.placeholderPrimary),
        model = model
    )
    Image(
        modifier = modifier,
        contentScale = contentScale,
        contentDescription = null,
        painter = painter,
    )
}

@Composable
@NonRestartableComposable
fun RoundedImageBlock(
    modifier: Modifier = Modifier,
    model: Any?,
    size: Dp,
) {
    ImageBlock(
        modifier = modifier
            .clip(CircleShape)
            .size(size),
        model = model
    )
}