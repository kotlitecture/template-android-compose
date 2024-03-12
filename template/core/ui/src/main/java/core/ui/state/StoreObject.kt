@file:Suppress("UNCHECKED_CAST")

package core.ui.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

@Immutable
class StoreObject<T>(
    private val value: T? = null,
    private val valueReply: Int = 1,
    private val valueBufferCapacity: Int = 1,
    private val onChanged: ((prevValue: T?, newValue: T?) -> Unit)? = null,
) {

    private var prevValue: T? = null
    private var currentValue: T? = value

    private val valueChanges = lazy {
        val processor = MutableSharedFlow<T?>(replay = valueReply, extraBufferCapacity = valueBufferCapacity)
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
                valueState.value.value = value
            }
        }
        return changed
    }

}