package app.userflow.update.googleplay

import androidx.compose.runtime.Composable
import app.provideHiltViewModel

@Composable
fun GooglePlayUpdateProvider() {
    provideHiltViewModel<GooglePlayUpdateViewModel>(activityScoped = true)
}