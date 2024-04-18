package app.userflow.passcode.ui.unlock

import androidx.navigation.NavGraphBuilder
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationStrategy
import kotlinx.serialization.Serializable

/**
 * Represents the destination for the unlock passcode screen.
 */
object UnlockPasscodeDestination : NavigationDestination<UnlockPasscodeDestination.Data>() {

    override val id: String = "unlock_passcode_screen"
    override val navStrategy: NavigationStrategy = NavigationStrategy.SingleInstance
    override val argsStrategy: ArgsStrategy<Data> = ArgsStrategy.json(Data.serializer())
    override fun doBind(builder: NavGraphBuilder) = composable(builder) { UnlockPasscodeScreen(it ?: Data()) }

    /**
     * Data class for the unlock passcode screen destination.
     *
     * @property nextDestinationIsPrevious Indicates if the next destination is the previous one.
     * @property nextDestinationUri The Uri of the next destination obtained with [toUriString] method of the required destination.
     * @property canNavigateBack Indicates if navigation back is enabled.
     * @property title The title of the screen.
     */
    @Serializable
    data class Data(
        val nextDestinationIsPrevious: Boolean = false,
        val nextDestinationUri: String? = null,
        val canNavigateBack: Boolean = false,
        val title: String? = null,
    )

}