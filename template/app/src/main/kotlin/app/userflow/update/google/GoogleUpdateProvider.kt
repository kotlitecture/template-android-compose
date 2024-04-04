package app.userflow.update.google

import androidx.compose.runtime.Composable
import app.provideHiltViewModel

@Composable
fun GoogleUpdateProvider() {
    provideHiltViewModel<GoogleUpdateViewModel>()
}