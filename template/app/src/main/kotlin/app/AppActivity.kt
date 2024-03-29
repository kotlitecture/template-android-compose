package app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import app.ui.navigation.adaptive.AdaptiveNavigation
import app.ui.navigation.bottom.BottomNavigation
import app.ui.navigation.left.DismissibleLeftNavigation
import app.ui.navigation.left.ModalLeftNavigation
import app.userflow.internet.no.NoInternetProvider
import app.userflow.loader.data.DataLoaderProvider
import app.userflow.review.google.GoogleReviewProvider
import app.userflow.update.google.GoogleUpdateProvider
import core.ui.AppScaffold
import core.ui.theme.ThemeProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: AppActivityViewModel = provideHiltViewModel(activityScoped = true)
            ScaffoldBlock(viewModel)
            SplashBlock(splashScreen, viewModel)
        }
    }

}

@Composable
private fun ScaffoldBlock(viewModel: AppActivityViewModel) {
    ThemeProvider(viewModel.themeState) {
        ModalLeftNavigation {
            AppScaffold(
                navigationState = viewModel.navigationState,
                commandState = viewModel.commandState,
                bottomBar = { BottomNavigation() }
            )
        }
        DataLoaderProvider(viewModel.appState)
        GoogleUpdateProvider()
        GoogleReviewProvider()
        NoInternetProvider()
    }
}

// {userflow.splash.basic}
@Composable
private fun SplashBlock(splashScreen: SplashScreen, viewModel: AppActivityViewModel) {
    splashScreen.setKeepOnScreenCondition {
        viewModel.navigationState.currentDestinationStore.asStateValue() == null
    }
}
// {userflow.splash.basic}
