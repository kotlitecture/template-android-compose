package app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import app.userflow.internet.NoInternetProvider
import app.userflow.loader.DataLoaderProvider
import app.userflow.review.googleplay.GooglePlayReviewProvider
import app.userflow.template.TemplateDestination
import app.userflow.update.googleplay.GooglePlayUpdateProvider
import app.userflow.webtonative.WebToNativeDestination
import core.ui.AppScaffold
import core.ui.provideViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent { ContentBlock() }
    }

}

@Composable
private fun ContentBlock(viewModel: AppActivityViewModel = provideViewModel()) {
    viewModel.destinationStore.asStateValue()
        ?.route
        ?.let { route ->
            AppScaffold(
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
