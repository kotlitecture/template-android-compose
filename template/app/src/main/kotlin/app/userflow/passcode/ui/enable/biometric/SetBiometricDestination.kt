package app.userflow.passcode.ui.enable.biometric

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.builtins.serializer

/**
 * Represents the destination for the set biometric screen.
 */
object SetBiometricDestination : NavigationDestination<Unit>() {

    override val id: String = "set_biometric_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.NewInstance
    override val argsStrategy: ArgsStrategy<Unit> = ArgsStrategy.json(Unit.serializer())
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { SetBiometricScreen() }

}