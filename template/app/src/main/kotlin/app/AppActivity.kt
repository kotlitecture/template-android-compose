package app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import app.userflow.internet.no.NoInternetProvider
import app.userflow.loader.DataLoaderProvider
import app.userflow.review.google.GoogleReviewProvider
import app.userflow.template.TemplateDestination
import app.userflow.update.google.GoogleUpdateProvider
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
            ScaffoldBlock(viewModel)
            GoogleUpdateProvider()
            GoogleReviewProvider()
            DataLoaderProvider(viewModel.appState)
            NoInternetProvider()
            SplashBlock(splashScreen, viewModel)
        }
    }

}

@Composable
private fun ScaffoldBlock(viewModel: AppActivityViewModel) {
    val destination = viewModel.destinationStore.asStateValue() ?: return
    AppScaffold(
        navigationState = viewModel.navigationState,
        commandState = viewModel.commandState,
        themeState = viewModel.themeState,
        startDestination = destination,
        destinations = remember {
            listOf(
                TemplateDestination,
                WebToNativeDestination
            )
        }
    )
}

@Composable
private fun SplashBlock(splashScreen: SplashScreen, viewModel: AppActivityViewModel) {
    splashScreen.setKeepOnScreenCondition {
        viewModel.themeState.dataStore.get() == null
    }
}
