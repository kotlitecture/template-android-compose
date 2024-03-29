package app.di.state

import core.ui.theme.ThemeConfig
import core.ui.theme.ThemeState
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
    fun theme(): ThemeState = ThemeState(getConfig = { ThemeConfig.Default })

}