package app.userflow.review.googleplay

import app.userflow.review.googleplay.data.ReviewConfig
import app.userflow.review.googleplay.data.ReviewData
import core.ui.state.StoreObject
import core.ui.state.StoreState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GooglePlayReviewState @Inject constructor() : StoreState() {

    val configStore = StoreObject(ReviewConfig())
    val dataStore = StoreObject<ReviewData>()

}