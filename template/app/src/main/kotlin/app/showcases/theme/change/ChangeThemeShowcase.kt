package app.showcases.theme.change

import app.showcases.Showcase
import app.showcases.ShowcasesViewModel
import app.userflow.theme.change.ChangeThemeDestination

object ChangeThemeShowcase : Showcase {

    override val label: String = "ChangeThemeDestination"

    override fun onClick(viewModel: ShowcasesViewModel) {
        viewModel.navigationState.onNext(ChangeThemeDestination)
    }

}