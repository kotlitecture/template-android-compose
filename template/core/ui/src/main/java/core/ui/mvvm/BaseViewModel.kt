package core.ui.mvvm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import core.data.state.StoreObject
import core.ui.app.AppState
import core.ui.app.loading.LoadingState
import core.ui.misc.extension.requireActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue
import javax.inject.Inject
import kotlin.collections.set
import kotlin.coroutines.CoroutineContext

@Stable
@Composable
inline fun <reified VM : BaseViewModel> provideViewModel(
    key: String? = null,
    singleInstance: Boolean = false,
    noinline focusHandler: ((field: String) -> Unit)? = null
): VM {
    val storeOwner: ViewModelStoreOwner
    val lifecycleOwner: LifecycleOwner
    when {
        singleInstance -> {
            val activity = LocalContext.current.requireActivity()
            lifecycleOwner = activity
            storeOwner = activity
        }

        else -> {
            lifecycleOwner = LocalLifecycleOwner.current
            storeOwner = LocalViewModelStoreOwner.current!!
        }
    }
    val viewModel: VM = hiltViewModel(storeOwner, key)
    viewModel.BindLifecycleOwner(lifecycleOwner, singleInstance)
    focusHandler?.let { viewModel.BindFocusHandler(it) }
    return viewModel
}

@Immutable
abstract class BaseViewModel : ViewModel() {

    protected open val refreshingDelay = 50L
    protected open val isRefreshingInitial = false

    private val focusRequester by lazy { StoreObject<FocusRequest>() }
    private val isRefreshing by lazy { StoreObject(isRefreshingInitial) }

    @Immutable
    private data class FocusRequest(
        val field: String,
        val uid: Long = System.currentTimeMillis()
    )

    enum class LaunchMode {
        Refreshing,
        Blocking,
        Default
    }

    @Inject
    lateinit var appState: AppState

    private val subscribers = ConcurrentLinkedQueue<Int>()
    private val jobs = ConcurrentHashMap<String, Job>()

    open fun onBack() = appState.onBack()
    fun onClearFocus() = focusRequester.clear()
    fun isRefreshing() = isRefreshing.asStateValueNotNull()
    fun onFocus(field: String) = focusRequester.set(FocusRequest(field))

    protected open fun doBind() {}
    protected open fun doResume() {}
    protected open fun doUnbind() {}

    fun launchMain(
        id: String,
        launchMode: LaunchMode = LaunchMode.Default,
        block: suspend CoroutineScope.() -> Unit
    ) {
        launch(
            id = id,
            block = block,
            launchMode = launchMode,
            context = viewModelScope.coroutineContext,
        )
    }

    fun launchAsync(
        id: String,
        launchMode: LaunchMode = LaunchMode.Default,
        block: suspend CoroutineScope.() -> Unit
    ) {
        launch(
            id = id,
            block = block,
            context = Dispatchers.IO,
            launchMode = launchMode
        )
    }

    suspend fun <T> withAsync(
        id: String? = null,
        launchMode: LaunchMode = LaunchMode.Default,
        block: suspend CoroutineScope.() -> T
    ): T {
        try {
            runCatching { jobs.remove(id)?.cancel() }
            withState(launchMode) { LoadingState.Loading(id) }
            val job = viewModelScope.async(Dispatchers.IO) { block.invoke(this) }
            withState(launchMode) { LoadingState.Loaded(id) }
            id?.let { jobs[id] = job }
            return job.await()
        } catch (th: Throwable) {
            withState(launchMode) { LoadingState.Error(id, th = th) }
            throw th
        }
    }

    protected suspend fun withMinDelay(block: suspend () -> Long) {
        val startTime = System.currentTimeMillis()
        val delay = block.invoke()
        val remainingDelay = delay - (System.currentTimeMillis() - startTime)
        if (remainingDelay > 0) delay(remainingDelay)
    }

    private fun launch(
        id: String,
        launchMode: LaunchMode,
        context: CoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) {
        jobs.remove(id)?.cancel()
        withState(launchMode) { LoadingState.Loading(id) }
        val job = viewModelScope.launch(context) {
            runCatching { block.invoke(this) }
                .onFailure(appState::onError)
        }
        job.invokeOnCompletion { withState(launchMode) { LoadingState.Loaded(id) } }
        jobs[id] = job
    }

    @Composable
    fun BindLifecycleOwner(owner: LifecycleOwner, singleInstance: Boolean) {
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
                    if (singleInstance) {
                        val currentJobs = jobs.values.toList()
                        jobs.clear()
                        currentJobs.forEach { it.cancel() }
                    }
                    doUnbind()
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalComposeUiApi::class)
    fun BindFocusHandler(focusHandler: (field: String) -> Unit) {
        val scope = rememberCoroutineScope()
        val keyboardController = LocalSoftwareKeyboardController.current
        LaunchedEffect(keyboardController) {
            focusRequester.asFlow().collectLatest {
                scope.launch {
                    if (it != null) {
                        focusHandler.invoke(it.field)
                        keyboardController?.show()
                    } else {
                        keyboardController?.hide()
                    }
                }
            }
        }
    }

    private fun withState(mode: LaunchMode, stateProvider: () -> LoadingState) {
        when (mode) {
            LaunchMode.Blocking -> {
                val nextState = stateProvider.invoke()
                appState.onLoading(nextState)
            }

            LaunchMode.Refreshing -> {
                val nextState = stateProvider.invoke()
                isRefreshing.set(nextState is LoadingState.Loading)
            }

            else -> Unit
        }
    }

}