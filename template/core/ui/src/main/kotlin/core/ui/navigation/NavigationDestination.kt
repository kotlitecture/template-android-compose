package core.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navigation
import kotlin.collections.set

abstract class NavigationDestination<D> {

    abstract val id: String
    val route by lazy { "$id?$ATTR_DATA={$ATTR_DATA}" }
    abstract val navStrategy: NavigationStrategy
    abstract val argsStrategy: ArgsStrategy<D>

    open fun getName(): String = id

    fun bind(builder: NavGraphBuilder) {
        REGISTRY[id] = this
        doBind(builder)
    }

    @Suppress("UNCHECKED_CAST", "RestrictedApi")
    internal fun navigate(data: Any?, strategy: NavigationStrategy, controller: NavHostController) {
        val uri = Uri.Builder()
            .encodedPath(NavDestination.createRoute(id))
            .apply {
                data?.let {
                    it as D
                    appendQueryParameter(ATTR_DATA, argsStrategy.toString(it))
                }
            }
            .build()
        strategy.proceed(route, uri, controller)
    }

    protected abstract fun doBind(builder: NavGraphBuilder)

    protected fun composable(
        builder: NavGraphBuilder,
        content: @Composable (data: D?) -> Unit
    ) {
        builder.composable(
            route = route,
            arguments = createArgs(),
            content = { entry -> route(entry, content) }
        )
    }

    protected fun dialog(
        builder: NavGraphBuilder,
        dismissOnBackPress: Boolean = true,
        dismissOnClickOutside: Boolean = true,
        content: @Composable (data: D?) -> Unit
    ) {
        builder.dialog(
            route = route,
            arguments = createArgs(),
            dialogProperties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside
            ),
            content = { entry -> route(entry, content) }
        )
    }

    protected fun navigation(
        builder: NavGraphBuilder,
        primaryDestination: NavigationDestination<*>,
        vararg destinations: NavigationDestination<*>
    ) {
        builder.navigation(
            startDestination = primaryDestination.route,
            route = route
        ) {
            primaryDestination.bind(this)
            destinations.forEach { it.bind(this) }
        }
    }

    @Composable
    private fun route(
        entry: NavBackStackEntry,
        content: @Composable (data: D?) -> Unit
    ) {
        val value = entry.arguments?.getString(ATTR_DATA)
        val data = value?.let { argsStrategy.toObject(it) }
        content(data)
    }

    private fun createArgs() = listOf(
        navArgument(ATTR_DATA) {
            type = NavType.StringType
            nullable = true
        }
    )

    companion object {
        private const val ATTR_DATA = "data"
        private val REGISTRY = mutableMapOf<String, NavigationDestination<*>>()

        fun find(route: String) = REGISTRY[getId(route)]

        private fun getId(route: String): String {
            val indexOfData = route.indexOf('?')
            return if (indexOfData != -1) {
                route.substring(0, indexOfData)
            } else {
                route
            }
        }
    }

}