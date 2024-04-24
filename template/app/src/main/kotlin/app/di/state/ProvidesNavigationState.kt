package app.di.state

import app.showcases.ShowcasesDestination
import app.ui.screen.template.TemplateDestination
import app.ui.screen.template_no_args.TemplateNoArgsDestination
import app.userflow.navigation.a.NavigationADestination
import app.userflow.navigation.b.NavigationBDestination
import app.userflow.navigation.c.NavigationCDestination
import app.userflow.passcode.ui.enable.biometric.SetBiometricDestination
import app.userflow.passcode.ui.enable.confirm.ConfirmPasscodeDestination
import app.userflow.passcode.ui.enable.set.SetPasscodeDestination
import app.userflow.passcode.ui.reset.ResetPasscodeDestination
import app.userflow.passcode.ui.unlock.UnlockPasscodeDestination
import app.userflow.theme.change.ChangeThemeDestination
import app.userflow.theme.change.ChangeThemeDialogDestination
import app.userflow.webtonative.WebToNativeDestination
import core.ui.navigation.NavigationState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesNavigationState {

    @Provides
    @Singleton
    fun state(): NavigationState = NavigationState(
        destinations = listOf(
            ShowcasesDestination,
            TemplateDestination,
            TemplateNoArgsDestination,
            WebToNativeDestination,
            NavigationADestination,
            NavigationBDestination,
            NavigationCDestination,
            ChangeThemeDestination,
            ChangeThemeDialogDestination,
            UnlockPasscodeDestination,
            SetPasscodeDestination,
            ConfirmPasscodeDestination,
            ResetPasscodeDestination,
            SetBiometricDestination
        )
    )

}