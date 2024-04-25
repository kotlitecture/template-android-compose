package app.userflow.review.google

import androidx.compose.runtime.Composable
import app.appViewModel

@Composable
fun GoogleReviewProvider() {
    appViewModel<GoogleReviewViewModel>()
}