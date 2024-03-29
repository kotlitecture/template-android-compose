package app.ui.navigation

import core.ui.state.StoreObject
import core.ui.state.StoreState

data class NavigationBarState(
    val availablePagesStore: StoreObject<List<NavigationBarPage<*>>> = StoreObject(),
    val activePageStore: StoreObject<NavigationBarPage<*>> = StoreObject()
) : StoreState()