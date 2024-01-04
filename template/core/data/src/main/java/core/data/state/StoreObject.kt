@file:Suppress("UNCHECKED_CAST")

package core.data.state

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Immutable
class StoreObject<T>(
    private val value: T? = null,
    private val onChanged: ((prevValue: T?, newValue: T?) -> Unit)? = null
) {

    private var currentValue: T? = value
    private var prevValue: T? = null

    private val valueChanges = lazy {
        val processor = MutableSharedFlow<T?>(replay = 1, extraBufferCapacity = 1)
        processor.tryEmit(currentValue)
        processor
    }

    private val valueState = lazy { mutableStateOf(currentValue) }

    fun asState(): MutableState<T?> = valueState.value
    fun asStateValue(): T? = asState().value
    fun asStateNotNull(): MutableState<T> = asState() as MutableState<T>
    fun asStateValueNotNull(): T = runCatching { asStateNotNull().value }.getOrElse { currentValue!! }
    fun asFlow(): Flow<T?> = valueChanges.value
    fun getNotNull(): T = currentValue!!
    fun clear() = set(value)
    fun get(): T? = currentValue
    fun getPrev(): T? = prevValue

    fun set(value: T?): Boolean {
        val changed = currentValue != value
        prevValue = currentValue
        currentValue = value
        if (changed) {
            onChanged?.invoke(prevValue, value)
            if (valueChanges.isInitialized()) {
                val changes = valueChanges.value
                changes.tryEmit(value)
            }
            if (valueState.isInitialized()) {
                onMain {
                    valueState.value.value = value
                }
            }
        }
        return changed
    }

    companion object {
        private val mainLooper by lazy { Handler(Looper.getMainLooper()) }

        private var mainScope: CoroutineScope? = null

        @Composable
        @NonRestartableComposable
        fun attachToActivityScope() {
            mainScope = rememberCoroutineScope()
            DisposableEffect(LocalContext.current) { onDispose { mainScope = null } }
        }

        fun onMain(block: () -> Unit) {
            mainScope
                ?.launch { block.invoke() }
                ?: run { mainLooper.post(block) }
        }
    }

}