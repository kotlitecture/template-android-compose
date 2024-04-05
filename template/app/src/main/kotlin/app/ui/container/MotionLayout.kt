package app.ui.container

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionLayoutScope
import androidx.constraintlayout.compose.MotionScene

@Composable
fun MotionLayout(
    modifier: Modifier = Modifier,
    @RawRes sceneRes: Int,
    progress: State<Float> = mutableStateOf(0f),
    content: @Composable MotionLayoutScope.() -> Unit
) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources
            .openRawResource(sceneRes)
            .readBytes()
            .decodeToString()
    }
    MotionLayout(
        modifier = modifier,
        motionScene = MotionScene(motionScene),
        progress = progress.value
    ) {
        content()
    }
}