package core.ui.block

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import core.ui.misc.extension.pxToDp
import core.ui.misc.extension.withCornerRadius

@Composable
@NonRestartableComposable
fun HighlightBlock(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 4.dp,
    background: Any,
    content: @Composable BoxScope.(modifier: Modifier) -> Unit = {}
) {
    Box(
        modifier = modifier
            .withCornerRadius(cornerRadius)
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        val size = remember { mutableStateOf(IntSize(0, 0)) }
        BackgroundBlock(
            modifier = Modifier.matchParentSize(),
            value = background,
            width = size.value.width.pxToDp(),
            height = size.value.height.pxToDp()
        )
        content(Modifier.onSizeChanged { size.value = it })
    }
}