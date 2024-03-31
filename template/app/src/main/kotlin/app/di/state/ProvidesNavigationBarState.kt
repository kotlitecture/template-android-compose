package app.di.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.outlined.Cookie
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
import java.util.UUID
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesNavigationBarState {

    @Provides
    @Singleton
    fun state(navigationState: NavigationState): NavigationBarState = NavigationBarState(
        pages = listOf(
            createPage(
                destination = TemplateDestination,
                navigationState = navigationState,
                getActiveIcon = { Icons.Filled.Cookie },
                getInactiveIcon = { Icons.Outlined.Cookie },
                getLabel = { UUID.randomUUID().toString() }
            )
        )
    )

    private fun <D> createPage(
        navigationState: NavigationState,
        destination: NavigationDestination<D>,
        data: D? = null,
        getLabel: () -> String?,
        getActiveIcon: () -> Any,
        getInactiveIcon: () -> Any,
        enabled: Boolean = true,
        alwaysShowLabel: Boolean = true
    ): NavigationBarPage {
        return NavigationBarPage(
            enabled = enabled,
            id = destination.id,
            getLabel = getLabel,
            getActiveIcon = getActiveIcon,
            getInactiveIcon = getInactiveIcon,
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