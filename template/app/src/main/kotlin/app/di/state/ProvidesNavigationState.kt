package app.di.state

import app.userflow.template.TemplateDestination
import app.userflow.webtonative.WebToNativeDestination
import core.ui.navigation.NavigationState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesNavigationState {

    @Provides
    @Singleton
    fun state(): NavigationState = NavigationState(
        listOf(
            TemplateDestination,
            WebToNativeDestination
        )
    )

}