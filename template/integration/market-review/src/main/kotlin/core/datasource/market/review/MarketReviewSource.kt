package core.datasource.market.review

import androidx.fragment.app.FragmentActivity
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import core.data.state.StoreObject
import core.datasource.review.IReviewSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class MarketReviewSource(private val activity: FragmentActivity) : IReviewSource {

    private val manager by lazy { ReviewManagerFactory.create(activity) }

    override suspend fun isAvailable(): Flow<Boolean> {
        return getReviewInfoFlow().map { it != null }
    }

    override suspend fun startReview() {
        getReviewInfoFlow().filterNotNull()
            .collectLatest { info ->
                manager.launchReviewFlow(activity, info)
            }
    }

    private fun getReviewInfoFlow(): Flow<ReviewInfo?> {
        val infoStore = StoreObject<ReviewInfo>()
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                infoStore.set(reviewInfo)
            } else {
                infoStore.set(null)
            }
        }
        return infoStore.asFlow().filterNotNull()
    }
}