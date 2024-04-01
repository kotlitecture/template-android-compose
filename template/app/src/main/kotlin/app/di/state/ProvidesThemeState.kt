package app.di.state

import core.ui.theme.ThemeConfig
import core.ui.theme.ThemeState
import core.ui.theme.material3.Material3Dark
import core.ui.theme.material3.Material3Light
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesThemeState {

    @Provides
    @Singleton
    fun state(): ThemeState = ThemeState(
        config = ThemeConfig(
            availableThemes = listOf(Material3Light, Material3Dark),
            defaultTheme = Material3Light,
            lightTheme = Material3Light,
            darkTheme = Material3Dark
        )
    )

}