package app.userflow.review.googleplay

import android.app.Activity
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LifecycleOwner
import app.userflow.review.googleplay.data.ReviewData
import com.google.android.play.core.review.ReviewManagerFactory
import core.ui.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class GooglePlayReviewViewModel @Inject constructor(
    private val app: Application,
    private val state: GooglePlayReviewState
) : AppViewModel() {

    private val manager by lazy { ReviewManagerFactory.create(app) }

    @Composable
    override fun doBind(owner: LifecycleOwner) {
        val activity = owner as Activity
        LaunchedEffect(activity) {
            launchAsync("reviewInfoStore") {
                val request = manager.requestReviewFlow()
                request.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val info = task.result
                        state.dataStore.set(ReviewData(info))
                    } else {
                        state.dataStore.clear()
                    }
                }
            }
            launchAsync("reviewFlow") {
                state.configStore.asFlow()
                    .filterNotNull()
                    .flatMapLatest { state.dataStore.asFlow().filterNotNull() }
                    .collectLatest { data -> requestReview(activity, data) }
            }
        }
    }

    private fun requestReview(activity: Activity, data: ReviewData) {
        manager.launchReviewFlow(activity, data.info)
            .addOnCompleteListener {
                state.configStore.clear()
            }
    }

}