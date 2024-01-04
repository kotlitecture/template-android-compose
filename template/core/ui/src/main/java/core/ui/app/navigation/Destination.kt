@file:Suppress("UNCHECKED_CAST")

package core.ui.app.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import core.essentials.misc.utils.GsonUtils
import core.ui.app.dialog.dialogLayout
import org.tinylog.Logger
import java.util.UUID
import kotlin.collections.set

private val memoryCache = HashMap<String, Any?>()

abstract class Destination<D> {

    abstract val id: String
    abstract val dataType: Class<D>
    abstract val strategy: Strategy

    open val screenName by lazy { id }
    open val ready: Boolean = true
    open val savable = true

    val route by lazy { "$id?${ATTR_DATA}={${ATTR_DATA}}" }

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

    fun toUri(data: D? = null): Uri {
        return Uri.Builder()
            .encodedPath(NavDestination.createRoute(id))
            .apply { data?.let { appendQueryParameter(ATTR_DATA, serialize(data)) } }
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
        content: @Composable (data: D?) -> Unit
    ) {
        builder.dialogLayout(
            route = route,
            arguments = arguments,
            content = { entry -> Route(entry, content) }
        )
    }

    protected fun navigation(
        builder: NavGraphBuilder,
        primaryDestination: Destination<*>,
        vararg destinations: Destination<*>
    ) {
        Logger.debug("register navigation :: {}", route)
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
                    memoryCache.remove(value)
                }
            }
        }
    }

    private fun serialize(data: D): String? {
        if (!savable) {
            val id = createDataId()
            memoryCache[id] = data
            return id
        }
        return GsonUtils.toString(data)
    }

    private fun deserialize(data: String?): D? {
        if (!savable) {
            return memoryCache[data] as? D
        }
        return GsonUtils.toObject(data, dataType)
    }

    companion object {
        private const val ATTR_DATA = "data"
        private val registry = mutableMapOf<String, Destination<*>>()

        fun get(route: String): Destination<*>? {
            return registry[getId(route)]
        }

        private fun getId(route: String): String {
            val indexOfData = route.indexOf('?')
            return if (indexOfData != -1) {
                route.substring(0, indexOfData)
            } else {
                route
            }
        }

        private fun register(destination: Destination<*>) {
            registry[destination.id] = destination
        }

    }

}