@file:Suppress("UNCHECKED_CAST")

package core.ui.state

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * An immutable store object that holds a value of type [T].
 *
 * @param T the type of the value stored in the object.
 * @property value The initial value of the store object.
 * @property valueReply The number of initial values to be replayed to collectors of the [asFlow] flow.
 * @property valueBufferCapacity The additional buffer capacity for the [asFlow] flow.
 * @property onChanged A lambda function invoked when the value of the store object changes.
 */
@Immutable
data class StoreObject<T>(
    private val value: T? = null,
    private val valueReply: Int = 1,
    private val valueBufferCapacity: Int = 1,
    private val onChanged: ((prevValue: T?, newValue: T?) -> Unit)? = null,
) {

    private var prevValue: T? = null
    private var currentValue: T? = value

    private val valueChanges = lazy {
        val processor =
            MutableSharedFlow<T?>(replay = valueReply, extraBufferCapacity = valueBufferCapacity)
        processor.tryEmit(currentValue)
        processor
    }

    private val valueState = lazy { mutableStateOf(currentValue) }

    /**
     * Returns the store object as a [MutableState] object.
     */
    fun asState(): MutableState<T?> = valueState.value

    /**
     * Returns the value of the store object as a [MutableState] object.
     */
    fun asStateValue(): T? = asState().value

    /**
     * Returns the store object as a non-nullable [MutableState] object.
     */
    fun asStateNotNull(): MutableState<T> = asState() as MutableState<T>

    /**
     * Returns the value of the store object as a non-nullable type [T].
     */
    fun asStateValueNotNull(): T =
        runCatching { asStateNotNull().value }.getOrElse { currentValue!! }

    /**
     * Returns the store object as a flow of type [T].
     */
    fun asFlow(): Flow<T?> = valueChanges.value

    /**
     * Gets the non-nullable value of the store object.
     */
    fun getNotNull(): T = currentValue!!

    /**
     * Clears the value of the store object to the initial state.
     */
    fun clear() = set(value)

    /**
     * Gets the value of the store object.
     */
    fun get(): T? = currentValue

    /**
     * Gets the previous value of the store object.
     */
    fun getPrev(): T? = prevValue

    /**
     * Sets the value of the store object and emits it to collectors if it has changed.
     *
     * @param value The new value to set.
     * @return `true` if the value has changed, `false` otherwise.
     */
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
                onMain { valueState.value.value = value }
            }
        }
        return changed
    }

    companion object {
        private val mainLooper by lazy { Handler(Looper.getMainLooper()) }

        private var mainScope: CoroutineScope? = null

        @Composable
        internal fun bind() {
            mainScope = rememberCoroutineScope()
            DisposableEffect(LocalContext.current) { onDispose { mainScope = null } }
        }

        internal fun onMain(block: () -> Unit) {
            mainScope
                ?.launch { block.invoke() }
                ?: run { mainLooper.post(block) }
        }
    }

}