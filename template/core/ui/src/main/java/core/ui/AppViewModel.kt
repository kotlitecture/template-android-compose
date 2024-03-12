package core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import core.ui.state.DataState
import core.ui.state.StoreState
import core.ui.misc.extensions.findActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.collections.set
import kotlin.coroutines.CoroutineContext

@Stable
@Composable
inline fun <reified VM : AppViewModel> provideViewModel(
    key: String? = null,
    activityScope: Boolean = false
): VM {
    val storeOwner: ViewModelStoreOwner
    val lifecycleOwner: LifecycleOwner
    when {
        activityScope -> {
            val activity = LocalContext.current.findActivity()!!
            lifecycleOwner = activity
            storeOwner = activity
        }

        else -> {
            lifecycleOwner = LocalLifecycleOwner.current
            storeOwner = LocalViewModelStoreOwner.current!!
        }
    }
    val viewModel: VM = hiltViewModel(storeOwner, key)
    viewModel.bind(lifecycleOwner, activityScope)
    return viewModel
}

@Immutable
abstract class AppViewModel : ViewModel() {

    private val subscribers = ConcurrentLinkedQueue<Int>()
    private val jobs = ConcurrentHashMap<String, Job>()

    protected open fun doBind() {}
    protected open fun doResume() {}
    protected open fun doUnbind() {}

    protected fun launchMain(
        id: String,
        state: StoreState? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        launch(
            id = id,
            state = state,
            block = block,
            context = viewModelScope.coroutineContext,
        )
    }

    protected fun launchAsync(
        id: String,
        state: StoreState? = null,
        block: suspend CoroutineScope.() -> Unit
    ) {
        launch(
            id = id,
            state = state,
            block = block,
            context = Dispatchers.IO
        )
    }

    private fun launch(
        id: String,
        state: StoreState?,
        context: CoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) {
        jobs.remove(id)?.cancel()
        state?.dataStateStore?.set(DataState.Loading(id))
        val job = viewModelScope.launch(context = context, block = block)
        state?.let { job }?.invokeOnCompletion { th ->
            val dataState = when {
                th == null -> DataState.Loaded(id)
                else -> DataState.Error(id, th)
            }
            state.dataStateStore.set(dataState)
        }
        jobs[id] = job
    }

    protected suspend fun <T> withAsync(block: suspend CoroutineScope.() -> T): T {
        val job = viewModelScope.async(Dispatchers.IO) { block.invoke(this) }
        return job.await()
    }

    @Composable
    fun bind(owner: LifecycleOwner, activityScope: Boolean) {
        val ownerId = owner.hashCode()
        val scope = rememberCoroutineScope()
        LaunchedEffect(ownerId) {
            val isNew = !subscribers.contains(ownerId)
            subscribers.add(ownerId)
            if (isNew) {
                doBind()
                var initialRequest = true
                owner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    scope.launch {
                        if (!initialRequest) {
                            doResume()
                        }
                        initialRequest = false
                    }
                }
            }
        }
        DisposableEffect(ownerId) {
            onDispose {
                subscribers.remove(ownerId)
                if (subscribers.isEmpty()) {
                    if (activityScope) {
                        val currentJobs = jobs.values.toList()
                        jobs.clear()
                        currentJobs.forEach { it.cancel() }
                    }
                    doUnbind()
                }
            }
        }
        doBind(owner)
    }

    @Composable
    protected open fun doBind(owner: LifecycleOwner) = Unit

}