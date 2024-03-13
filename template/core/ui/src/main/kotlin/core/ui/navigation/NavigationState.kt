package core.ui.navigation

import core.ui.state.StoreObject
import core.ui.state.StoreState

class NavigationState : StoreState() {

    val destinationStore = StoreObject<NavigationDestination<*>>()
    val navigationStore = StoreObject<NavigationData<*>>(valueReply = 1, valueBufferCapacity = Int.MAX_VALUE)

    fun onBack() {
        navigationStore.set(NavigationData<Nothing>())
    }

    fun <D> onNavigate(destination: NavigationDestination<D>, data: D? = null) {
        navigationStore.set(NavigationData(destination, data))
    }

    companion object {
        val Default by lazy { NavigationState() }
    }

}