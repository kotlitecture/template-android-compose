package app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import app.feature.template.TemplateDestination
import app.feature.webtonative.WebToNativeDestination // {workflow-webtonative}
import core.datasource.intent.IntentSource
import core.datasource.update.IUpdateSource // {market-update}
import core.ui.app.AppScreen
import core.ui.app.dialog.error.ErrorDialogDestination
import core.ui.app.dialog.hint.HintDialogDestination
import core.ui.mvvm.provideViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppActivity : FragmentActivity() {

    @Inject
    lateinit var intentSource: IntentSource

    @Inject // {market-update}
    lateinit var updateSource: IUpdateSource // {market-update}

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            intentSource.attachToActivityScope()
            ContentBlock()
            LaunchedEffect(Unit) { updateSource.init() } // {market-update}
        }
    }

}

@Composable
private fun ContentBlock(viewModel: AppViewModel = provideViewModel()) {
    viewModel.destinationStore.asStateValue()
        ?.route
        ?.let { route ->
            AppScreen(
                route = route,
                navGraphBuilder = {
                    TemplateDestination().register(this)
                    WebToNativeDestination().register(this) // {workflow-webtonative}
                    HintDialogDestination().register(this)
                    ErrorDialogDestination().register(this)
                },
                bottomBar = {

                },
                footer = {
                }
            )
        }
}
