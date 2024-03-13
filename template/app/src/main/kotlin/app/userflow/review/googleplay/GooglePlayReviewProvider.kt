package app.userflow.review.googleplay

import androidx.compose.runtime.Composable
import app.provideHiltViewModel

@Composable
fun GooglePlayReviewProvider() {
    provideHiltViewModel<GooglePlayReviewViewModel>(activityScope = true)
}