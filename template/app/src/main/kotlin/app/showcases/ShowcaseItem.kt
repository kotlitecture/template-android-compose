package app.showcases

import core.ui.navigation.NavigationDestination

interface ShowcaseItem:Showcase {

    fun onClick(viewModel: ShowcasesViewModel)

    fun destinations(): List<NavigationDestination<*>> = emptyList()

}