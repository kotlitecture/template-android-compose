package app.showcases

import app.showcases.theme.change.ChangeThemeDialogShowcase
import app.showcases.theme.change.ChangeThemeScreenShowcase
import app.showcases.theme.toggle.ToggleThemeShowcase

object Showcases {

    val all = listOf(
        ShowcaseItemGroup("Userflow :: Theme"),
        ChangeThemeScreenShowcase,
        ChangeThemeDialogShowcase,
        ToggleThemeShowcase
    )

}