package app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import app.feature.template.TemplateDestination
import app.feature.webtonative.WebToNativeDestination
import app.userflow.internet.NoInternetProvider
import app.userflow.loading.LoadingStateProvider
import app.userflow.update.googleplay.GooglePlayUpdateProvider
import core.ui.AppScaffold
import core.ui.provideViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ContentBlock()
            GooglePlayUpdateProvider() // {userflow.google-play-update}
            LoadingStateProvider() // {userflow.loading}
            NoInternetProvider() // {userflow.internet}
        }
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
                    WebToNativeDestination().register(this) // {workflow-webtonative}
                },
                bottomBar = {
                    // bottom bar
                },
                overlay = {
                    // overlay outside of the navigation host
                }
            )
        }
}
