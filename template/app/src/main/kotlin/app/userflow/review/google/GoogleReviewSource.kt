package app.userflow.review.google

import android.app.Application
import app.userflow.review.google.data.ReviewConfig
import app.userflow.review.google.data.ReviewData
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.testing.FakeReviewManager
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton class responsible for managing Google review-related functionality.
 *
 * @param state The state object for managing Google review state.
 * @param app The application instance.
 */
@Singleton
class GoogleReviewSource @Inject constructor(
    private val state: GoogleReviewState,
    private val app: Application
) {

    private val realManager by lazy { ReviewManagerFactory.create(app) }
    private val fakeManager by lazy { FakeReviewManager(app) }

    /**
     * Initiates the review process based on the specified debug mode.
     *
     * @param debug Indicates whether to use the fake review manager in debug mode.
     * @return The review data obtained from the review process.
     *
     * @throws Exception in case of any error.
     */
    suspend fun review(debug: Boolean): ReviewData {
        val manager = if (debug) fakeManager else realManager
        return review(ReviewConfig(manager))
    }

    private suspend fun review(config: ReviewConfig): ReviewData {
        state.dataStore.clear()
        state.configStore.set(config)
        val data = state.dataStore.asFlow()
            .filterNotNull()
            .filter { it.completeTime != null }
            .first()
        if (data.reviewError != null) throw data.reviewError
        return data
    }

}