package app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import app.ui.navigation.NavigationBarProvider
import app.ui.navigation.bottom.BottomNavigation
import app.ui.theme.ThemeProvider
import app.userflow.internet.no.NoInternetProvider
import app.userflow.loader.data.DataLoaderProvider
import app.userflow.review.google.GoogleReviewProvider
import app.userflow.update.google.GoogleUpdateProvider
import core.ui.navigation.NavigationScaffold
import core.ui.navigation.NavigationState
import core.ui.navigation.rememberNavigationContext
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity of the app.
 */
@AndroidEntryPoint
class AppActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: AppViewModel = provideHiltViewModel()
            val navigationState = remember { viewModel.navigationState }
            val appState = remember { viewModel.appState }
            SplashBlock(splashScreen, navigationState)
            ScaffoldBlock(appState, navigationState)
        }
    }

}

@Composable
private fun ScaffoldBlock(appState: AppState, navigationState: NavigationState) {
    ThemeProvider {
        val navigationContext = rememberNavigationContext(navigationState)
        NavigationBarProvider { // {ui.navigation.common}
            NavigationScaffold(
                navigationContext = navigationContext,
                navigationState = navigationState,
                bottomBar = { BottomNavigation() }
            )
        } // {ui.navigation.common}
        DataLoaderProvider(appState)
        GoogleUpdateProvider()
        GoogleReviewProvider()
        NoInternetProvider()
    }
}

// {userflow.splash.basic}
@Composable
private fun SplashBlock(splashScreen: SplashScreen, navigationState: NavigationState) {
    splashScreen.setKeepOnScreenCondition {
        navigationState.currentDestinationStore.get() == null
    }
}
// {userflow.splash.basic}
