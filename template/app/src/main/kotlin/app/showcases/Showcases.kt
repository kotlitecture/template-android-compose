package app.showcases

import app.showcases.datasource.http.basic.BasicHttpShowcase
import app.showcases.datasource.paging.basic.BasicPagingShowcase
import app.showcases.userflow.passcode.navigate.NavigateWithPasscodeShowcase
import app.showcases.userflow.passcode.reset.ResetPasscodeShowcase
import app.showcases.userflow.passcode.set.SetPasscodeShowcase
import app.showcases.userflow.passcode.unlock.UnlockPasscodeShowcase
import app.showcases.userflow.theme.change.ChangeThemeDialogShowcase
import app.showcases.userflow.theme.change.ChangeThemeScreenShowcase
import app.showcases.userflow.theme.toggle.ToggleThemeShowcase

/**
 * Object containing all showcase items.
 */
object Showcases {

    /**
     * A list containing all showcase items.
     */
    val all = listOf(
        ShowcaseItemGroup("Datasource :: Http"),
        BasicHttpShowcase,
        ShowcaseItemGroup("Datasource :: Paging"),
        BasicPagingShowcase,
        ShowcaseItemGroup("Userflow :: Theme"),
        ChangeThemeScreenShowcase,
        ChangeThemeDialogShowcase,
        ToggleThemeShowcase,
        ShowcaseItemGroup("Userflow :: Passcode"),
        SetPasscodeShowcase,
        ResetPasscodeShowcase,
        UnlockPasscodeShowcase,
        NavigateWithPasscodeShowcase
    )

}