package core.datasource.firebase.analytics

import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import core.datasource.analytics.IAnalyticsSource
import org.tinylog.kotlin.Logger

class FirebaseAnalyticsSource : IAnalyticsSource {

    private val analytics by lazy { Firebase.analytics }
    private val crashlytics by lazy { FirebaseCrashlytics.getInstance() }

    override fun setUserId(id: String?) {
        analytics.setUserId(id)
    }

    override fun setUserProperty(key: String, value: String?) {
        analytics.setUserProperty(key, value)
    }

    override fun onEvent(event: String, params: Map<String, String>) {
        analytics.logEvent(event, bundleOf(*params.toList().toTypedArray()))
    }

    override fun onScreenView(screenName: String, params: Map<String, String>) {
        val newParams = params
            .plus(FirebaseAnalytics.Param.SCREEN_NAME to screenName)
            .plus(FirebaseAnalytics.Param.SCREEN_CLASS to screenName)
        Logger.debug("onScreenView :: name={}, params={}", screenName, params)
        analytics.logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            bundleOf(*newParams.toList().toTypedArray())
        )
    }

    override fun onError(event: String, error: Throwable) {
        crashlytics.recordException(error)
    }

}