package app.di.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cookie
import app.ui.navigation.NavigationBarPage
import app.ui.navigation.NavigationBarState
import app.userflow.template.TemplateDestination
import core.ui.navigation.NavigationDestination
import core.ui.navigation.NavigationState
import core.ui.navigation.NavigationStrategy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesNavigationBarState {

    @Provides
    @Singleton
    fun state(navigationState: NavigationState): NavigationBarState = NavigationBarState(
        availablePages = listOf(
            createPage(
                navigationState = navigationState,
                destination = TemplateDestination,
                getIcon = { Icons.Default.Cookie },
                getLabel = { "1" }
            )
        )
    )

    private fun <D> createPage(
        navigationState: NavigationState,
        destination: NavigationDestination<D>,
        data: D? = null,
        getLabel: () -> String?,
        getIcon: () -> Any,
        enabled: Boolean = true,
        alwaysShowLabel: Boolean = true
    ): NavigationBarPage {
        return NavigationBarPage(
            getLabel = getLabel,
            getIcon = getIcon,
            enabled = enabled,
            id = destination.id,
            alwaysShowLabel = alwaysShowLabel,
            onClick = { navigate(navigationState, destination, data) }
        )
    }

    private fun <D> navigate(
        navigationState: NavigationState,
        destination: NavigationDestination<D>,
        data: D?,
    ) {
        navigationState.onNext(
            destination,
            data,
            NavigationStrategy.SingleInstance
        )
    }

}