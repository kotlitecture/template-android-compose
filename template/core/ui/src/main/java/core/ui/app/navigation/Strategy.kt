package core.ui.app.navigation

import android.net.Uri
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import org.tinylog.Logger

enum class Strategy {
    Back {
        override fun doProceed(route: String?, uri: Uri, controller: NavHostController) {
            if (controller.previousBackStackEntry != null) {
                controller.popBackStack()
            }
        }
    },

    NewInstance {
        override fun doProceed(route: String?, uri: Uri, controller: NavHostController) {
            controller.navigate(uri)
        }
    },

    SingleInstance {
        override fun doProceed(route: String?, uri: Uri, controller: NavHostController) {
            controller.navigate(uri, navOptions {
                if (route != null) {
                    popUpTo(route) {
                        inclusive = true
                    }
                }
                launchSingleTop = true
                restoreState = false
            })
        }
    },

    ReplacePrevious {
        override fun doProceed(route: String?, uri: Uri, controller: NavHostController) {
            val prev = controller.currentDestination?.route ?: route
            Logger.debug("prevRoute :: prev={}, next={}", prev, uri)
            controller.navigate(uri, navOptions {
                if (prev != null) {
                    popUpTo(prev) {
                        inclusive = true
                    }
                }
                launchSingleTop = true
                restoreState = false
            })
        }
    },

    ClearHistory {
        override fun doProceed(route: String?, uri: Uri, controller: NavHostController) {
            controller.navigate(uri, navOptions {
                popUpTo(controller.graph.id) {
                    inclusive = true
                }
            })
        }
    }

    ;

    open fun proceed(route: String?, uri: Uri, controller: NavHostController) {
        val currentRoute = controller.currentDestination?.route
        Logger.debug("proceed :: {} -> {}", currentRoute, route)
        if (!uri.query.isNullOrEmpty() || currentRoute != route) {
            Logger.debug("proceed :: navigate :: {}", uri)
            doProceed(route, uri, controller)
        }
    }

    protected open fun doProceed(route: String?, uri: Uri, controller: NavHostController) = Unit
}