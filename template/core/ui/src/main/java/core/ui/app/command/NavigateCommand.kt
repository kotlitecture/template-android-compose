package core.ui.app.command

import android.net.Uri
import core.ui.app.AppContext
import core.ui.app.navigation.Destination
import core.ui.app.navigation.Strategy

class NavigateCommand<D>(
    private val destination: Destination<D>? = null,
    private val data: D? = null
) : Command() {

    override fun doExecute(appContext: AppContext) {
        try {
            val controller = appContext.navController
            val strategy = destination?.strategy ?: Strategy.Back
            val uri = destination?.toUri(data) ?: Uri.EMPTY
            val route = destination?.route
            strategy.proceed(route, uri, controller)
        } catch (e: Exception) {
            appContext.appState.onError(e)
        }
    }

}