package app.ui.theme

import core.ui.BaseViewModel
import core.ui.theme.ThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    val themeState: ThemeState
) : BaseViewModel() {

    override fun doBind() {
        launchAsync("config") {
            // restore
            val initialConfig = themeState.config
            themeState.configStore.set(initialConfig)
            themeState.configStore.asFlow()
                .filterNotNull()
                .filter { it !== initialConfig }
                .collectLatest { config ->
                    // store
                }
        }
    }

}