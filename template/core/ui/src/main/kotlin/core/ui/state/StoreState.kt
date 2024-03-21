package core.ui.state

import androidx.compose.runtime.Immutable

/**
 * Represents the state of data specific to a particular domain or user flow.
 * This state can be shared between the Repository, ViewModel, and View layers.
 *
 * The state includes a [StoreObject] that holds the [DataState].
 */
@Immutable
abstract class StoreState {

    val dataStateStore by lazy { StoreObject<DataState>() }

    fun loaded(id: String) = dataStateStore.set(DataState.Loaded(id))
    fun loading(id: String) = dataStateStore.set(DataState.Loading(id))
    fun error(id: String, e: Exception) = dataStateStore.set(DataState.Error(id, e))

}