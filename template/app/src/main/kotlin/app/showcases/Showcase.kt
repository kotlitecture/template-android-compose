package app.showcases

import core.ui.navigation.NavigationDestination

interface Showcase {

    val label: String

    fun onClick(viewModel: ShowcasesViewModel)

    fun destinations(): List<NavigationDestination<*>> = emptyList()

}