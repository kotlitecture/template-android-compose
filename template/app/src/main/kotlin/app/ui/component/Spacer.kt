package app.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.ui.misc.extensions.pxToDp

@Composable
fun Spacer2() {
    Spacer(modifier = Modifier.size(2.dp))
}

@Composable
fun Spacer4() {
    Spacer(modifier = Modifier.size(4.dp))
}

@Composable
fun Spacer8() {
    Spacer(modifier = Modifier.size(8.dp))
}

@Composable
fun Spacer12() {
    Spacer(modifier = Modifier.size(12.dp))
}

@Composable
fun Spacer16() {
    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
fun SpacerStatusBar(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
    )
}

@Composable
fun SpacerNavigationBar(modifier: Modifier = Modifier) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    )
}

@Composable
fun SpacerDynamic(modifier: Modifier = Modifier, heightState: State<Int>) {
    Spacer(
        modifier
            .height(heightState.value.pxToDp())
            .fillMaxWidth()
    )
}
