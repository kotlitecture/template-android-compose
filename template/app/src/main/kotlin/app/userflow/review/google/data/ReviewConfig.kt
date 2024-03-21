package app.userflow.review.google.data

import com.google.android.play.core.review.ReviewManager

data class ReviewConfig(
    val reviewManager: ReviewManager,
    private val uid: Long = System.nanoTime()
)