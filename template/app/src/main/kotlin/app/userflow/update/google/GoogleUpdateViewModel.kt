package app.userflow.update.google

import android.app.Activity
import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import app.userflow.update.google.data.UpdateData
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import core.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class GoogleUpdateViewModel @Inject constructor(
    app: Application,
    private val state: GoogleUpdateState
) : BaseViewModel(), InstallStateUpdatedListener, LifecycleEventObserver {

    private val manager by lazy { AppUpdateManagerFactory.create(app) }

    @Composable
    override fun doBind(owner: LifecycleOwner) {
        val activity = owner as Activity
        LaunchedEffect(activity) {
            owner.lifecycle.addObserver(this@GoogleUpdateViewModel)
            launchAsync("updateFlow") {
                state.configStore.asFlow()
                    .filterNotNull()
                    .flatMapLatest { state.dataStore.asFlow().filterNotNull() }
                    .collectLatest { data -> onUpdate(activity, data) }
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
        state.dataStore.set(UpdateData(type, info))
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
                state.dataStore.clear()
            }
        }
        manager.registerListener(this)
    }

    private fun onResume() {
        manager.appUpdateInfo.addOnSuccessListener { info ->
            val type = state.dataStore.get()?.type ?: AppUpdateType.FLEXIBLE
            if (type == AppUpdateType.FLEXIBLE) {
                // If the update is downloaded but not installed, notify the user to complete the update.
                if (info.installStatus() == InstallStatus.DOWNLOADED) {
                    onComplete()
                }
            } else if (type == AppUpdateType.IMMEDIATE) {
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
            state.configStore.clear()
            state.dataStore.clear()
        }
    }

    private fun onUpdate(activity: Activity, data: UpdateData) {
        val type = data.type
        val info = data.info
        val options = AppUpdateOptions.newBuilder(type).build()
        manager.startUpdateFlow(info, activity, options)
    }

}