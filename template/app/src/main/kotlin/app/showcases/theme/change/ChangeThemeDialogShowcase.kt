package app.showcases.theme.change

import app.showcases.Showcase
import app.showcases.ShowcasesViewModel
import app.userflow.theme.change.ChangeThemeDialogDestination

object ChangeThemeDialogShowcase : Showcase {

    override val label: String = "ChangeThemeDialogDestination"

    override fun onClick(viewModel: ShowcasesViewModel) {
        viewModel.navigationState.onNext(ChangeThemeDialogDestination)
    }

}