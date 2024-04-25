package app.userflow.update.google

import androidx.compose.runtime.Composable
import app.appViewModel

@Composable
fun GoogleUpdateProvider() {
    appViewModel<GoogleUpdateViewModel>()
}