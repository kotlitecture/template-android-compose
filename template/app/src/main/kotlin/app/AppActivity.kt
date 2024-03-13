package app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
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
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: AppActivityViewModel = provideHiltViewModel()
            ContentBlock(viewModel)
            splashScreen.setKeepOnScreenCondition {
                viewModel.themeState.dataStore.get() == null
            }
        }
    }

}

@Composable
private fun ContentBlock(viewModel: AppActivityViewModel) {
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
    GooglePlayUpdateProvider() // {userflow.google-play-update}
    GooglePlayReviewProvider() // {userflow.google-play-review}
    DataLoaderProvider(viewModel.appState) // {userflow.data-loader}
    NoInternetProvider() // {userflow.no-internet}
}
