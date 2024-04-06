package app.showcases.theme.change

import app.showcases.ShowcaseItem
import app.showcases.ShowcasesViewModel
import app.userflow.theme.change.ChangeThemeDialogDestination

object ChangeThemeDialogShowcase : ShowcaseItem {

    override val label: String = "Change Theme Dialog"

    override fun onClick(viewModel: ShowcasesViewModel) {
        viewModel.navigationState.onNext(ChangeThemeDialogDestination)
    }

}