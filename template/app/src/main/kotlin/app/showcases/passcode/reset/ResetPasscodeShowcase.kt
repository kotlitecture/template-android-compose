package app.showcases.passcode.reset

import app.showcases.ShowcaseItem
import app.showcases.ShowcasesViewModel
import app.userflow.passcode.ui.reset.ResetPasscodeDestination

object ResetPasscodeShowcase : ShowcaseItem {

    override val label: String = "Reset Passcode"

    override fun onClick(viewModel: ShowcasesViewModel) {
        viewModel.navigationState.onNext(ResetPasscodeDestination)
    }

}