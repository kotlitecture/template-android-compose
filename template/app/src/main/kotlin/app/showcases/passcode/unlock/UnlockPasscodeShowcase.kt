package app.showcases.passcode.unlock

import app.showcases.ShowcaseItem
import app.showcases.ShowcasesDestination
import app.showcases.ShowcasesViewModel
import app.userflow.passcode.ui.unlock.UnlockPasscodeDestination

object UnlockPasscodeShowcase : ShowcaseItem {

    override val label: String = "Unlock Passcode"

    override fun onClick(viewModel: ShowcasesViewModel) {
        val data = UnlockPasscodeDestination.Data(nextRoute = ShowcasesDestination.route)
        viewModel.navigationState.onNext(UnlockPasscodeDestination, data)
    }

}