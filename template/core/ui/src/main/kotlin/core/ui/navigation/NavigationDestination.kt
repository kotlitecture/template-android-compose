@file:Suppress("UNCHECKED_CAST")

package core.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import androidx.navigation.navigation
import java.util.UUID
import kotlin.collections.set

abstract class NavigationDestination<D> {

    abstract val id: String
    abstract val strategy: NavigationStrategy

    open val screenName by lazy { id }
    open val ready: Boolean = true
    open val savable = true

    val route by lazy { "$id?$ATTR_DATA={$ATTR_DATA}" }

    private val arguments by lazy {
        listOf(
            navArgument(ATTR_DATA) {
                type = NavType.StringType
                nullable = true
            }
        )
    }

    fun register(builder: NavGraphBuilder) {
        register(this)
        doRegister(builder)
    }

    protected open fun createDataId(): String = UUID.randomUUID().toString()
    protected abstract fun doRegister(builder: NavGraphBuilder)
    protected open fun toObject(string: String): D? = null
    protected open fun toString(data: D): String? = null

    fun toUri(data: Any?): Uri {
        return Uri.Builder()
            .encodedPath(NavDestination.createRoute(id))
            .apply { data?.let { it as? D }?.let { appendQueryParameter(ATTR_DATA, serialize(it)) } }
            .build()
    }

    protected fun screen(
        builder: NavGraphBuilder,
        content: @Composable (data: D?) -> Unit
    ) {
        builder.composable(
            route = route,
            arguments = arguments,
            content = { entry -> Route(entry, content) }
        )
    }

    protected fun dialog(
        builder: NavGraphBuilder,
        arguments: List<NamedNavArgument> = emptyList(),
        deepLinks: List<NavDeepLink> = emptyList(),
        dismissOnBackPress: Boolean = true,
        dismissOnClickOutside: Boolean = true,
        content: @Composable (data: D?) -> Unit
    ) {
        builder.dialog(
            route = route,
            arguments = arguments,
            deepLinks = deepLinks,
            dialogProperties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside
            ),
            content = { entry -> Route(entry, content) }
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
            primaryDestination.register(this)
            destinations.forEach { it.register(this) }
        }
    }

    @Stable
    @Composable
    private fun Route(
        entry: NavBackStackEntry,
        content: @Composable (data: D?) -> Unit
    ) {
        val value = entry.arguments?.getString(ATTR_DATA)
        val data = value?.let(this::deserialize)
        content(data)
        if (!savable && value != createDataId()) {
            DisposableEffect(value) {
                onDispose {
                    destinationDataCache.remove(value)
                }
            }
        }
    }

    private fun serialize(data: D): String? {
        if (!savable) {
            val id = createDataId()
            destinationDataCache[id] = data
            return id
        }
        return toString(data)
    }

    private fun deserialize(data: String): D? {
        if (!savable) {
            return destinationDataCache[data] as? D
        }
        return toObject(data)
    }

    companion object {
        private const val ATTR_DATA = "data"
        private val destinationDataCache = mutableMapOf<String, Any?>()
        private val destinationRegistry = mutableMapOf<String, NavigationDestination<*>>()

        fun get(route: String): NavigationDestination<*>? {
            return destinationRegistry[getId(route)]
        }

        private fun getId(route: String): String {
            val indexOfData = route.indexOf('?')
            return if (indexOfData != -1) {
                route.substring(0, indexOfData)
            } else {
                route
            }
        }

        private fun register(destination: NavigationDestination<*>) {
            destinationRegistry[destination.id] = destination
        }

    }

}