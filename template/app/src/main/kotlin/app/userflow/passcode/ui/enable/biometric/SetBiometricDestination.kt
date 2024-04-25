package app.userflow.passcode.ui.enable.biometric

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.NavigationStrategy
import core.ui.navigation.NavigationDestinationNoArgs

/**
 * Represents the destination for the set biometric screen.
 */
object SetBiometricDestination : NavigationDestinationNoArgs() {

    override val id: String = "set_biometric_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { SetBiometricScreen() }

}