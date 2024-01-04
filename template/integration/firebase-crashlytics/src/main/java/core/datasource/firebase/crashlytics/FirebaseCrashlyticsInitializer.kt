package core.datasource.firebase.crashlytics

import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import core.data.startup.DependencyInitializer

class FirebaseCrashlyticsInitializer : DependencyInitializer<FirebaseCrashlytics>() {

    override fun initialize(context: Context): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }

}