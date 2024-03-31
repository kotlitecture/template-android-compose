package app.userflow.review.google

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LifecycleOwner
import app.userflow.review.google.data.ReviewConfig
import app.userflow.review.google.data.ReviewData
import com.google.android.gms.tasks.Tasks
import core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class GoogleReviewViewModel @Inject constructor(
    private val state: GoogleReviewState
) : BaseViewModel() {

    @Composable
    override fun doBind(owner: LifecycleOwner) {
        val activity = owner as Activity
        LaunchedEffect(activity) {
            launchAsync("reviewFlow") {
                state.configStore.asFlow()
                    .filterNotNull()
                    .collectLatest { config ->
                        val data = ReviewData()
                        try {
                            val manager = config.reviewManager
                            val reviewInfo = Tasks.await(manager.requestReviewFlow())
                            requestReview(activity, config, data.copy(reviewInfo = reviewInfo))
                        } catch (e: Exception) {
                            state.dataStore.set(data
                                .copy(
                                    completeTime = Date(),
                                    reviewError = e,
                                )
                            )
                        }
                    }
            }
        }
    }

    private fun requestReview(activity: Activity, config: ReviewConfig, data: ReviewData) {
        config.reviewManager.launchReviewFlow(activity, data.reviewInfo!!)
            .addOnCompleteListener {
                if (it.exception != null) {
                    state.dataStore.set(data
                        .copy(
                            completeTime = Date(),
                            reviewError = it.exception,
                        )
                    )
                } else {
                    state.dataStore.set(data
                        .copy(
                            completeTime = Date()
                        )
                    )
                }
                state.configStore.clear()
            }
    }

}