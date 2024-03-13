package di

import core.ui.command.CommandState
import core.ui.navigation.NavigationState
import core.ui.theme.ThemeState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesState {

    @Provides
    @Singleton
    fun navigation(): NavigationState = NavigationState.Default

    @Provides
    @Singleton
    fun command(): CommandState = CommandState.Default

    @Provides
    @Singleton
    fun theme(): ThemeState = ThemeState.Default

}