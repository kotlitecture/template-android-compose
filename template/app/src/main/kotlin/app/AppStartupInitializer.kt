package app

import android.content.Context
import androidx.startup.Initializer
import core.dataflow.startup.DependencyInitializer
import core.datasource.firebase.analytics.FirebaseAnalyticsInitializer // {firebase-analytics}
import core.datasource.firebase.config.FirebaseRemoteConfigInitializer // {firebase-config}
import core.datasource.firebase.crashlytics.FirebaseCrashlyticsInitializer // {firebase-crashlytics}

class AppStartupInitializer : DependencyInitializer<AppStartupInitializer>() {

    override fun initialize(context: Context): AppStartupInitializer = this

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf(
        FirebaseAnalyticsInitializer::class.java, // {firebase-analytics}
        FirebaseCrashlyticsInitializer::class.java, // {firebase-crashlytics}
        FirebaseRemoteConfigInitializer::class.java // {firebase-config}
    )

}