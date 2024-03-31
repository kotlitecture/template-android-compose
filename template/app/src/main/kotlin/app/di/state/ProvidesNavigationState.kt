package app.di.state

import app.userflow.navigation.a.NavigationADestination
import app.userflow.navigation.b.NavigationBDestination
import app.userflow.navigation.c.NavigationCDestination
import app.userflow.navigation.d.NavigationDDestination
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
        destinations = listOf(
            TemplateDestination,
            WebToNativeDestination,
            NavigationADestination,
            NavigationBDestination,
            NavigationCDestination,
            NavigationDDestination
        )
    )

}