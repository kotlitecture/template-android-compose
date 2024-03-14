package core.ui.navigation

import core.ui.state.StoreObject
import core.ui.state.StoreState

class NavigationState : StoreState() {

    val destinationStore = StoreObject<NavigationDestination<*>>()
    val navigationStore = StoreObject<NavigationData<*>>(valueReply = 1, valueBufferCapacity = Int.MAX_VALUE)

    fun onBack() {
        navigationStore.set(NavigationData(
            strategy = NavigationStrategy.Back,
            destination = null,
            data = null,
        ))
    }

    fun <D> onNavigate(
        destination: NavigationDestination<D>,
        data: D? = null,
        strategy: NavigationStrategy = destination.navStrategy
    ) {
        navigationStore.set(NavigationData(
            destination = destination,
            strategy = strategy,
            data = data
        ))
    }

    companion object {
        val Default by lazy { NavigationState() }
    }

}