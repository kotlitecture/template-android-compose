package core.ui.layout

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import core.ui.misc.extension.traceRecompose

@Stable
fun Modifier.withLayoutId(id: String): Modifier {
    return layoutId(id)
}

@Composable
fun MotionScreenLayout(
    key: Any? = null,
    modifier: Modifier = Modifier,
    @RawRes sceneRes: Int,
    progress: State<Float> = mutableFloatStateOf(0f),
    content: @Composable () -> Unit
) {
    traceRecompose("MotionScreenLayout")
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(sceneRes)
            .readBytes()
            .decodeToString()
    }
    MotionLayout(
        motionScene = MotionScene(motionScene),
        modifier = modifier.fillMaxWidth(),
        progress = progress.value
    ) {
        content()
    }
}