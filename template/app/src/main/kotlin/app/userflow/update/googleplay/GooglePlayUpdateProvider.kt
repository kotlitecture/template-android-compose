package app.userflow.update.googleplay

import androidx.compose.runtime.Composable
import core.ui.provideViewModel

@Composable
fun GooglePlayUpdateProvider() {
    provideViewModel<GooglePlayUpdateViewModel>(activityScope = true)
}