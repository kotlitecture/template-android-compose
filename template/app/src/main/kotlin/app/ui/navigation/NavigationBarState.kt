package app.ui.navigation

import core.ui.state.StoreObject
import core.ui.state.StoreState

/**
 * Represents the state of the navigation bar.
 *
 * @param availablePages The list of available pages in the navigation bar.
 */
data class NavigationBarState(
    val availablePages: List<NavigationBarPage>
) : StoreState() {

    /** Store object for the available pages. */
    val availablePagesStore: StoreObject<List<NavigationBarPage>> = StoreObject(availablePages)

    /** Store object for the active page. */
    val activePageStore: StoreObject<NavigationBarPage> = StoreObject()

}