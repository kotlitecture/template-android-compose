package app.showcases.userflow.passcode.unlock

import app.showcases.ShowcaseItem
import app.showcases.ShowcasesViewModel
import app.userflow.passcode.ui.unlock.UnlockPasscodeDestination

object UnlockPasscodeShowcase : ShowcaseItem {

    override val label: String = "Unlock Passcode"

    override fun onClick(viewModel: ShowcasesViewModel) {
        val data = UnlockPasscodeDestination.Data(canNavigateBack = true)
        viewModel.navigationState.onNext(UnlockPasscodeDestination, data)
    }

}