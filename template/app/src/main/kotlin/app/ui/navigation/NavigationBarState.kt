package app.ui.navigation

import core.ui.state.StoreObject
import core.ui.state.StoreState

/**
 * Represents the state of a navigation bar.
 *
 * @param availablePagesStore The store object containing the list of available navigation bar pages.
 * @param activePageStore The store object containing the active navigation bar page.
 */
data class NavigationBarState(
    val availablePagesStore: StoreObject<List<NavigationBarPage<*>>> = StoreObject(),
    val activePageStore: StoreObject<NavigationBarPage<*>> = StoreObject()
) : StoreState()