package core.ui.state

import androidx.compose.runtime.Immutable

@Immutable
abstract class StoreState {

    val dataStateStore by lazy { StoreObject<DataState>() }

}