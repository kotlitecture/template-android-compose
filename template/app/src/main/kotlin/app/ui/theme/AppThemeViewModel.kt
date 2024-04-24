package app.ui.theme

import app.datasource.keyvalue.AppKeyValueSource
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

/**
 * ViewModel class responsible for managing the theme state.
 */
@HiltViewModel
class AppThemeViewModel @Inject constructor(
    val themeState: ThemeState,
    private val keyValueSource: AppKeyValueSource
) : BaseViewModel() {

    /**
     * Performs the binding operation.
     */
    override fun doBind() {
        launchAsync("config") {
            val key = themeState.persistentKey
            if (key == null) {
                themeState.configStore.set(themeState.defaultConfig)
            } else {
                val strategy = JsonStrategy.create(AppThemeConfigData.serializer())
                val config = keyValueSource.read(key, strategy)?.let { mapToModel(it) }
                    ?: themeState.defaultConfig
                themeState.configStore.set(config)
                themeState.configStore.asFlow()
                    .filterNotNull()
                    .filter { it !== config }
                    .map { mapToData(it) }
                    .collectLatest { data -> keyValueSource.save(key, data, strategy) }
            }
        }
    }

    /**
     * Maps data from [AppThemeConfigData] to [ThemeConfig].
     *
     * @param from The source data.
     * @return The mapped [ThemeConfig].
     */
    private fun mapToModel(from: AppThemeConfigData): ThemeConfig {
        val initial = themeState.defaultConfig
        return initial.copy(
            defaultTheme = themeState.findProviderById(from.defaultThemeId) ?: initial.defaultTheme,
            lightTheme = themeState.findProviderById(from.lightThemeId) ?: initial.lightTheme,
            darkTheme = themeState.findProviderById(from.darkThemeId) ?: initial.darkTheme,
            autoDark = from.autoDark ?: initial.autoDark
        )
    }

    /**
     * Maps data from [ThemeConfig] to [AppThemeConfigData].
     *
     * @param from The source data.
     * @return The mapped [AppThemeConfigData].
     */
    private fun mapToData(from: ThemeConfig): AppThemeConfigData {
        return AppThemeConfigData(
            defaultThemeId = from.defaultTheme.id,
            lightThemeId = from.lightTheme.id,
            darkThemeId = from.darkTheme.id,
            autoDark = from.autoDark
        )
    }

}