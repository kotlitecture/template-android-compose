package app.showcases

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.navigation.NavGraphBuilder
import app.provideHiltViewModel
import app.ui.container.FixedTopBarLayout
import core.ui.BaseViewModel
import core.ui.navigation.ArgsStrategy
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationState
import core.ui.navigation.NavigationStrategy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@Immutable
abstract class Showcase : NavigationDestination<Unit>() {

    override val navStrategy: NavigationStrategy = NavigationStrategy.SingleInstance
    override val argsStrategy: ArgsStrategy<Unit> = ArgsStrategy.memory()

    override fun doBind(builder: NavGraphBuilder) {
        composable(builder) {
            ShowcaseScreen(this) {
                content()
            }
        }
    }

    abstract val label: String

    @Composable
    abstract fun ColumnScope.content()

}

@Composable
fun ShowcaseScreen(showcase: Showcase, content: @Composable ColumnScope.() -> Unit) {
    val viewModel: ShowcaseViewModel = provideHiltViewModel()
    FixedTopBarLayout(
        onBack = viewModel::onBack,
        title = showcase.label,
        content = content
    )
}

@HiltViewModel
class ShowcaseViewModel @Inject constructor(
    private val navigationState: NavigationState,
) : BaseViewModel() {

    fun onBack() {
        navigationState.onBack()
    }

}