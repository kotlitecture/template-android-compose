package app.showcases.userflow.passcode.navigate

import app.showcases.ShowcaseItem
import app.showcases.ShowcasesViewModel
import app.showcases.userflow.passcode.navigate.from.NavigateWithPasscodeFromDestination
import app.showcases.userflow.passcode.navigate.to.NavigateWithPasscodeToDestination
import core.ui.navigation.NavigationDestination

object NavigateWithPasscodeShowcase : ShowcaseItem {

    override val label: String = "Navigate with Passcode"

    override fun onClick(viewModel: ShowcasesViewModel) {
        viewModel.navigationState.onNext(NavigateWithPasscodeFromDestination)
    }

    override fun dependsOn(): List<NavigationDestination<*>> = listOf(
        NavigateWithPasscodeFromDestination,
        NavigateWithPasscodeToDestination
    )


}