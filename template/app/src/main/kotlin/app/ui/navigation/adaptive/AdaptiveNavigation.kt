package app.ui.navigation.adaptive

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.ui.navigation.bottom.BottomNavigation
import app.ui.navigation.left.PermanentLeftNavigation
import app.ui.navigation.left.RailNavigation
import core.ui.misc.utils.WindowSize
import core.ui.misc.utils.WindowSizeProvider

/**
 * Composable function to display an adaptive navigation.
 *
 * @param content The content to display.
 */
@Composable
fun AdaptiveNavigation(content: @Composable () -> Unit) {
    WindowSizeProvider { size ->
        when {
            size <= WindowSize.Compact -> Bottom(content)
            size <= WindowSize.Large -> LeftCompact(content)
            else -> LeftLarge(content)
        }
    }
}

@Composable
private fun Bottom(content: @Composable () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(Modifier.weight(1f)) { content() }
        BottomNavigation(Modifier.wrapContentHeight())
    }
}

@Composable
private fun LeftCompact(content: @Composable () -> Unit) {
    RailNavigation(content)
}

@Composable
private fun LeftLarge(content: @Composable () -> Unit) {
    PermanentLeftNavigation(content)
}