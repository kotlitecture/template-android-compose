package app.userflow.update.googleplay

import android.app.Activity
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import core.ui.state.StoreObject
import core.ui.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class GooglePlayUpdateViewModel @Inject constructor(app: Application) : AppViewModel(), InstallStateUpdatedListener, LifecycleEventObserver {

    private val manager = AppUpdateManagerFactory.create(app)
    private val typeStore = StoreObject(AppUpdateType.FLEXIBLE)
    private val availableStore = StoreObject(false)
    private val infoStore = StoreObject<AppUpdateInfo>()

    @Composable
    override fun doBind(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(this)
        LaunchedEffect(owner) {
            launchAsync("availableStore") {
                availableStore.asFlow()
                    .filterNotNull()
                    .filter { it }
                    .distinctUntilChanged()
                    .collectLatest { onUpdate(owner) }
            }
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> onInit()
            Lifecycle.Event.ON_RESUME -> onResume()
            Lifecycle.Event.ON_DESTROY -> onDestroy()
            else -> Unit
        }
    }

    override fun onStateUpdate(state: InstallState) {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            onComplete()
        }
    }

    private fun requestUpdate(info: AppUpdateInfo, type: Int) {
        infoStore.set(info)
        typeStore.set(type)
        availableStore.set(true)
    }

    private fun onInit() {
        manager.appUpdateInfo.addOnSuccessListener { info ->
            // Check if update is available
            if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) { // UPDATE IS AVAILABLE
                if (info.updatePriority() == 5) { // Priority: 5 (Immediate update flow)
                    if (info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        requestUpdate(info, AppUpdateType.IMMEDIATE)
                    }
                } else if (info.updatePriority() == 4) { // Priority: 4
                    val clientVersionStalenessDays = info.clientVersionStalenessDays()
                    if (clientVersionStalenessDays != null && clientVersionStalenessDays >= 5 && info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        // Trigger IMMEDIATE flow
                        requestUpdate(info, AppUpdateType.IMMEDIATE)
                    } else if (clientVersionStalenessDays != null && clientVersionStalenessDays >= 3 && info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        // Trigger FLEXIBLE flow
                        requestUpdate(info, AppUpdateType.FLEXIBLE)
                    }
                } else if (info.updatePriority() == 3) { // Priority: 3
                    val clientVersionStalenessDays = info.clientVersionStalenessDays()
                    if (clientVersionStalenessDays != null && clientVersionStalenessDays >= 30 && info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        // Trigger IMMEDIATE flow
                        requestUpdate(info, AppUpdateType.IMMEDIATE)
                    } else if (clientVersionStalenessDays != null && clientVersionStalenessDays >= 15 && info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        // Trigger FLEXIBLE flow
                        requestUpdate(info, AppUpdateType.FLEXIBLE)
                    }
                } else if (info.updatePriority() == 2) { // Priority: 2
                    val clientVersionStalenessDays = info.clientVersionStalenessDays()
                    if (clientVersionStalenessDays != null && clientVersionStalenessDays >= 90 && info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        // Trigger IMMEDIATE flow
                        requestUpdate(info, AppUpdateType.IMMEDIATE)
                    } else if (clientVersionStalenessDays != null && clientVersionStalenessDays >= 30 && info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        // Trigger FLEXIBLE flow
                        requestUpdate(info, AppUpdateType.FLEXIBLE)
                    }
                } else if (info.updatePriority() == 1) { // Priority: 1
                    // Trigger FLEXIBLE flow
                    requestUpdate(info, AppUpdateType.FLEXIBLE)
                } else { // Priority: 0
                    // Do not show in-app update
                }
            } else {
                // UPDATE IS NOT AVAILABLE
                availableStore.set(false)
            }
        }
        manager.registerListener(this)
    }

    private fun onResume() {
        val currentType = typeStore.getNotNull()
        manager.appUpdateInfo.addOnSuccessListener { info ->
            if (currentType == AppUpdateType.FLEXIBLE) {
                // If the update is downloaded but not installed, notify the user to complete the update.
                if (info.installStatus() == InstallStatus.DOWNLOADED)
                    onComplete()
            } else if (currentType == AppUpdateType.IMMEDIATE) {
                // for AppUpdateType.IMMEDIATE only, already executing updater
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    requestUpdate(info, AppUpdateType.IMMEDIATE)
                }
            }
        }
    }

    private fun onDestroy() {
        manager.unregisterListener(this)
    }

    private fun onComplete() {
        manager.completeUpdate().addOnSuccessListener {
            availableStore.clear()
            infoStore.clear()
            typeStore.clear()
        }
    }

    private fun onUpdate(owner: LifecycleOwner) {
        val info = infoStore.get() ?: return
        val activity = owner as? Activity ?: return
        val type = typeStore.getNotNull()
        val options = AppUpdateOptions.newBuilder(type).build()
        manager.startUpdateFlow(info, activity, options)
    }

}