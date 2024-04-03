package app.ui.theme

import app.datasource.storage.AppKeyValueSource
import core.data.serialization.JsonStrategy
import core.ui.BaseViewModel
import core.ui.theme.ThemeConfig
import core.ui.theme.ThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    val themeState: ThemeState,
    private val keyValueSource: AppKeyValueSource
) : BaseViewModel() {

    override fun doBind() {
        launchAsync("config") {
            val key = themeState.persistentKey
            if (key == null) {
                themeState.configStore.set(themeState.initialConfig)
            } else {
                val strategy = JsonStrategy.create(ThemeConfigData.serializer())
                val config = keyValueSource.read(key, strategy)?.let { mapToModel(it) }
                    ?: themeState.initialConfig
                themeState.configStore.set(config)
                themeState.configStore.asFlow()
                    .filterNotNull()
                    .filter { it !== config }
                    .map { mapToData(it) }
                    .collectLatest { data -> keyValueSource.save(key, data, strategy) }
            }
        }
    }

    private fun mapToModel(from: ThemeConfigData): ThemeConfig {
        val initial = themeState.initialConfig
        return initial.copy(
            defaultTheme = themeState.findProviderById(from.defaultThemeId) ?: initial.defaultTheme,
            lightTheme = themeState.findProviderById(from.lightThemeId) ?: initial.lightTheme,
            darkTheme = themeState.findProviderById(from.darkThemeId) ?: initial.darkTheme,
            autoDark = from.autoDark ?: initial.autoDark
        )
    }

    private fun mapToData(from: ThemeConfig): ThemeConfigData {
        return ThemeConfigData(
            defaultThemeId = from.defaultTheme.id,
            lightThemeId = from.lightTheme.id,
            darkThemeId = from.darkTheme.id,
            autoDark = from.autoDark
        )
    }

}