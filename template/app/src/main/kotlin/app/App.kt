package app

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.hilt.navigation.compose.hiltViewModel
import core.ui.AppViewModel
import core.ui.createViewModel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application()

@Stable
@Composable
inline fun <reified VM : AppViewModel> provideHiltViewModel(
    key: String? = null,
    activityScope: Boolean = false
): VM {
    return createViewModel(activityScope) { hiltViewModel(it, key) }
}