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

/**
 * Abstract class representing a navigation destination.
 *
 * @param D The type of data associated with this destination.
 */
abstract class NavigationDestination<D> {

    /** Unique identifier for this destination. */
    abstract val id: String

    /** Route string for navigation. */
    val route by lazy { "$id?$ATTR_DATA={$ATTR_DATA}" }

    /** Strategy for navigation behavior. */
    abstract val navStrategy: NavigationStrategy

    /** Strategy for handling arguments associated with this destination. */
    abstract val argsStrategy: ArgsStrategy<D>

    /**
     * Gets the name of this destination.
     *
     * @return The name of the destination.
     */
    open fun getName(): String = id

    /**
     * Binds this destination to the given NavGraphBuilder.
     *
     * @param builder The NavGraphBuilder to bind to.
     */
    fun bind(builder: NavGraphBuilder) {
        REGISTRY[id] = this
        doBind(builder)
    }

    /**
     * Navigates to this destination.
     *
     * @param data The data associated with the navigation.
     * @param strategy The navigation strategy to use.
     * @param controller The NavHostController for navigation.
     */
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

    /**
     * Performs binding of this destination to the NavGraphBuilder.
     *
     * @param builder The NavGraphBuilder to bind to.
     */
    protected abstract fun doBind(builder: NavGraphBuilder)

    /**
     * Defines a composable route for this destination.
     *
     * @param builder The NavGraphBuilder to bind to.
     * @param content The composable content to display.
     */
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

    /**
     * Defines a dialog route for this destination.
     *
     * @param builder The NavGraphBuilder to bind to.
     * @param dismissOnBackPress Whether the dialog dismisses on back press.
     * @param dismissOnClickOutside Whether the dialog dismisses on click outside.
     * @param content The composable content to display.
     */
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

    /**
     * Defines navigation routes for this destination.
     *
     * @param builder The NavGraphBuilder to bind to.
     * @param primaryDestination The primary destination for the navigation.
     * @param destinations Other destinations to include in the navigation.
     */
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

        internal fun find(route: String) = REGISTRY[getId(route)]

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