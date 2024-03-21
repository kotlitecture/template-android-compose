package app.userflow.review.google.data

import com.google.android.play.core.review.ReviewInfo
import java.util.Date

data class ReviewData(
    val startTime: Date = Date(),
    val completeTime: Date? = null,
    val reviewInfo: ReviewInfo? = null,
    val reviewError: Exception? = null
) {

    fun duration(): Long {
        if (completeTime == null) return -1
        return completeTime.time - startTime.time
    }

}