package app.userflow.review.google

import androidx.compose.runtime.Composable
import app.provideHiltViewModel

@Composable
fun GoogleReviewProvider() {
    provideHiltViewModel<GoogleReviewViewModel>()
}