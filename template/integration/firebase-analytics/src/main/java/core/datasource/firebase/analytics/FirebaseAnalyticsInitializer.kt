package core.datasource.firebase.analytics

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import core.data.startup.DependencyInitializer

class FirebaseAnalyticsInitializer : DependencyInitializer<FirebaseAnalytics>() {

    override fun initialize(context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }

}