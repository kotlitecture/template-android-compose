package core.ui.navigation

import core.ui.state.StoreObject
import core.ui.state.StoreState

/**
 * Represents the navigation state of the application, managing the destination and navigation data.
 *
 * This class extends [StoreState], providing mechanisms for managing the current navigation state.
 */
class NavigationState(val destinations: List<NavigationDestination<*>>) : StoreState() {

    /** StoreObject to hold the current navigation destination. */
    val destinationStore = StoreObject<NavigationDestination<*>>()

    /** StoreObject to hold the current navigation data. */
    val navigationStore = StoreObject<NavigationData<*>>(valueReply = 0, valueBufferCapacity = Int.MAX_VALUE)

    /**
     * Navigate back to the previous screen.
     */
    fun onBack() {
        navigationStore.set(NavigationData(
            strategy = NavigationStrategy.Back,
            destination = null,
            data = null,
        ))
    }

    /**
     * Navigate to a new destination with optional data and strategy.
     *
     * @param destination The destination to navigate to.
     * @param data The optional data to pass during navigation.
     * @param strategy The navigation strategy to use. Defaults to the destination's default strategy.
     */
    fun <D> onNext(
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

}