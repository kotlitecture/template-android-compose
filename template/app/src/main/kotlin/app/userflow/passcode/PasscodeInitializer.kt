package app.userflow.passcode

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.os.Bundle
import app.AppDependencyInitializer
import app.AppInitializerEntryPoint
import app.userflow.passcode.repository.PasscodeRepository
import app.userflow.passcode.ui.unlock.UnlockPasscodeDestination
import core.data.misc.extensions.globalAsync
import core.ui.navigation.NavigationState
import dagger.Lazy
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.math.max

/**
 * Initializes passcode-related functionality and manages its state through activity lifecycle events.
 */
class PasscodeInitializer : AppDependencyInitializer<Unit>(), ActivityLifecycleCallbacks {

    @Inject
    lateinit var repositoryLazy: Lazy<PasscodeRepository>

    @Inject
    lateinit var navigationStateLazy: Lazy<NavigationState>

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
            val canLock = repositoryLazy.get().canLock(foreground)
            if (canLock) {
                lock()
            }
        }
    }

    private suspend fun lock() {
        val navigationState = navigationStateLazy.get()
        val destination = navigationState.currentDestinationStore.asFlow()
            .filterNotNull()
            .first()
        if (destination !is UnlockPasscodeDestination) {
            navigationState.onNext(
                UnlockPasscodeDestination,
                UnlockPasscodeDestination.Data(
                    nextDestinationIsPrevious = true
                )
            )
        }
    }

}