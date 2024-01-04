@file:OptIn(ExperimentalLayoutApi::class)

package core.ui.block

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@NonRestartableComposable
fun SpacerFullHeight(
    modifier: Modifier = Modifier,
    fraction: Float = 1f,
    minus: Dp = 0.dp
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imeHeight =
        if (WindowInsets.isImeVisible) {
            WindowInsets.ime.asPaddingValues().calculateBottomPadding()
        } else {
            0.dp
        }
    val height = (screenHeight - imeHeight - minus).times(fraction)
    Spacer(modifier = modifier.height(height))
}

@Composable
@NonRestartableComposable
fun Spacer2() {
    Spacer(modifier = Modifier.size(2.dp))
}

@Composable
@NonRestartableComposable
fun Spacer4() {
    Spacer(modifier = Modifier.size(4.dp))
}

@Composable
@NonRestartableComposable
fun Spacer8() {
    Spacer(modifier = Modifier.size(8.dp))
}

@Composable
@NonRestartableComposable
fun Spacer12() {
    Spacer(modifier = Modifier.size(12.dp))
}

@Composable
@NonRestartableComposable
fun Spacer16() {
    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
@NonRestartableComposable
fun SpacerStatusBar(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier
        .fillMaxWidth()
        .statusBarsPadding())
}

@Composable
@NonRestartableComposable
fun SpacerNavigationBar(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier
        .fillMaxWidth()
        .navigationBarsPadding())
}

@Composable
@NonRestartableComposable
fun Spacer24() {
    Spacer(modifier = Modifier.size(24.dp))
}

@Composable
@NonRestartableComposable
fun Spacer32() {
    Spacer(modifier = Modifier.size(32.dp))
}

@Composable
@NonRestartableComposable
fun Spacer56() {
    Spacer(modifier = Modifier.size(56.dp))
}