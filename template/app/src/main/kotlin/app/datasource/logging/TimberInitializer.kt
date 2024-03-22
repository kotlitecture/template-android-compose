package app.datasource.logging

import android.content.Context
import app.BuildConfig
import app.startup.DependencyInitializer
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Initializes Timber for logging in the application.
 * Timber is only planted if the application is in debug mode.
 */
class TimberInitializer : DependencyInitializer<Unit>() {

    override fun initialize(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

}