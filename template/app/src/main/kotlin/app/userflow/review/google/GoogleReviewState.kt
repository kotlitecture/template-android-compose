package app.userflow.review.google

import app.userflow.review.google.data.ReviewConfig
import app.userflow.review.google.data.ReviewData
import core.ui.state.StoreObject
import core.ui.state.StoreState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleReviewState @Inject constructor() : StoreState() {

    val configStore = StoreObject(ReviewConfig())
    val dataStore = StoreObject<ReviewData>()

}