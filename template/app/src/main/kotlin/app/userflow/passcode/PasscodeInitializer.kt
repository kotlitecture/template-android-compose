package app.userflow.passcode

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
import app.AppDependencyInitializer
import app.AppInitializerEntryPoint
import app.userflow.passcode.repository.PasscodeRepository
import core.data.misc.extensions.globalAsync
import dagger.Lazy
import javax.inject.Inject
import kotlin.math.max

/**
 * Initializes the PasscodeState for the application to handle foreground state of the app.
 */
class PasscodeInitializer : AppDependencyInitializer<Unit>(), ActivityLifecycleCallbacks {

    @Inject
    lateinit var repository: Lazy<PasscodeRepository>

    private var activityCounter = 0

    override fun initialize(context: Context) {
        AppInitializerEntryPoint.resolve(context).inject(this)
        val app = context.applicationContext as Application
        app.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityStarted(activity: Activity) {
        if (activity.isChangingConfigurations) return
        activityCounter += 1
        tryLock()
    }

    override fun onActivityStopped(activity: Activity) {
        if (activity.isChangingConfigurations) return
        activityCounter = max(0, activityCounter - 1)
        tryLock()
    }

    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityDestroyed(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    private fun tryLock() {
        globalAsync("tryLock") {
            val foreground = activityCounter > 0
            repository.get().tryLock(foreground)
        }
    }

}