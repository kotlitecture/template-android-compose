package app.showcases.theme.change

import app.showcases.ShowcaseItem
import app.showcases.ShowcasesViewModel
import app.userflow.theme.change.ChangeThemeDestination

object ChangeThemeScreenShowcase : ShowcaseItem {

    override val label: String = "Change Theme Screen"

    override fun onClick(viewModel: ShowcasesViewModel) {
        viewModel.navigationState.onNext(ChangeThemeDestination)
    }

}