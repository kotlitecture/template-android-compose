package app.userflow.review.googleplay

import androidx.compose.runtime.Composable
import core.ui.provideViewModel

@Composable
fun GooglePlayReviewProvider() {
    provideViewModel<GooglePlayReviewViewModel>(activityScope = true)
}