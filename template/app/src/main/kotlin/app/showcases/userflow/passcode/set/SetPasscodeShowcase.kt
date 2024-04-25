package app.showcases.userflow.passcode.set

import app.showcases.ShowcaseItem
import app.showcases.ShowcasesViewModel
import app.userflow.passcode.ui.enable.set.SetPasscodeDestination

object SetPasscodeShowcase : ShowcaseItem {

    override val label: String = "Set Passcode"

    override fun onClick(viewModel: ShowcasesViewModel) {
        viewModel.navigationState.onNext(SetPasscodeDestination)
    }

}