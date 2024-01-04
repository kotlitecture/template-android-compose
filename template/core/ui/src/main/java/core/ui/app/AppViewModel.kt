package core.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import core.datasource.analytics.IAnalyticsSource
import core.ui.app.command.Command
import core.ui.app.navigation.Destination
import core.ui.mvvm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val analytics: IAnalyticsSource
) : BaseViewModel() {

    @Stable
    fun isReady() = appState.readyStore.asStateNotNull()

    @Composable
    fun bindContext(): AppContext {
        val appContext = rememberAppContext(this)
        appState.commandHandler = object : AppState.ICommandHandler {
            override fun handle(command: Command) {
                command.execute(appContext)
            }
        }
        LaunchedEffect(appContext) {
            launchAsync("currentBackStackEntryFlow") {
                appContext.navController.currentBackStackEntryFlow
                    .mapNotNull { it.destination.route }
                    .mapNotNull(Destination.Companion::get)
                    .distinctUntilChanged()
                    .collectLatest {
                        appState.destinationStore.set(it)
                        appState.readyStore.set(it.ready)
                        analytics.onScreenView(it.screenName)
                    }
            }
        }
        return appContext
    }

}