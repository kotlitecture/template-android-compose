package core.ui.state

import androidx.compose.runtime.Composable

@Composable
fun StoreStateProvider() {
    StoreObject.bind()
}