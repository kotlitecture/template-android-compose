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
                navigationState = navigationState,
                destination = TemplateDestination,
                getActiveIcon = { Icons.Filled.Cookie },
                getInactiveIcon = { Icons.Outlined.Cookie },
                getLabel = { UUID.randomUUID().toString() }
            )
        )
    )

    private fun <D> createPage(
        navigationState: NavigationState,
        destination: NavigationDestination<D>,
        getInactiveIcon: () -> Any,
        getActiveIcon: () -> Any,
        getLabel: () -> String?,
    ): NavigationBarPage {
        return NavigationBarPage(
            enabled = true,
            id = destination.id,
            getLabel = getLabel,
            alwaysShowLabel = false,
            getActiveIcon = getActiveIcon,
            getInactiveIcon = getInactiveIcon,
            onClick = { navigate(navigationState, destination) }
        )
    }

    private fun <D> navigate(
        navigationState: NavigationState,
        destination: NavigationDestination<D>
    ) {
        navigationState.onNext(
            destination = destination,
            strategy = NavigationStrategy.SingleInstance
        )
    }

}