package app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import app.userflow.internet.NoInternetProvider
import app.userflow.loader.DataLoaderProvider
import app.userflow.review.googleplay.GooglePlayReviewProvider
import app.userflow.template.TemplateDestination
import app.userflow.update.googleplay.GooglePlayUpdateProvider
import app.userflow.webtonative.WebToNativeDestination
import core.ui.AppScaffold
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: AppActivityViewModel = provideHiltViewModel()
            ScaffoldProvider(viewModel)
            GooglePlayUpdateProvider() // {userflow.google-play-update}
            GooglePlayReviewProvider() // {userflow.google-play-review}
            DataLoaderProvider(viewModel.appState) // {userflow.data-loader}
            NoInternetProvider() // {userflow.no-internet}
            SplashBlock(splashScreen, viewModel)
        }
    }

}

@Composable
private fun ScaffoldProvider(viewModel: AppActivityViewModel) {
    viewModel.destinationStore.asStateValue()
        ?.route
        ?.let { route ->
            AppScaffold(
                navigationState = viewModel.navigationState,
                commandState = viewModel.commandState,
                themeState = viewModel.themeState,
                startDestination = route,
                navGraphBuilder = {
                    TemplateDestination().register(this)
                    WebToNativeDestination().register(this) // {userflow.webtonative}
                }
            )
        }
}

@Composable
private fun SplashBlock(splashScreen: SplashScreen, viewModel: AppActivityViewModel) {
    splashScreen.setKeepOnScreenCondition {
        viewModel.themeState.dataStore.get() == null
    }
}
