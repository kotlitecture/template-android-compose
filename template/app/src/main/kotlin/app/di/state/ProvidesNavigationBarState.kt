package app.di.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.NoDrinks
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.LocalDrink
import androidx.compose.material.icons.outlined.NoDrinks
import androidx.compose.material.icons.outlined.WineBar
import app.ui.navigation.NavigationBarPage
import app.ui.navigation.NavigationBarState
import app.userflow.navigation.a.NavigationADestination
import app.userflow.navigation.b.NavigationBDestination
import app.userflow.navigation.c.NavigationCDestination
import app.userflow.navigation.d.NavigationDDestination
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
        pages = listOf(
            createPage(
                navigationState = navigationState,
                destination = NavigationADestination,
                getActiveIcon = { Icons.Filled.WineBar },
                getInactiveIcon = { Icons.Outlined.WineBar },
                getLabel = { "A" }
            ),
            createPage(
                navigationState = navigationState,
                destination = NavigationBDestination,
                getActiveIcon = { Icons.Filled.LocalDrink },
                getInactiveIcon = { Icons.Outlined.LocalDrink },
                getLabel = { "B" }
            ),
            createPage(
                navigationState = navigationState,
                destination = NavigationCDestination,
                getActiveIcon = { Icons.Filled.Coffee },
                getInactiveIcon = { Icons.Outlined.Coffee },
                getLabel = { "C" }
            ),
            createPage(
                navigationState = navigationState,
                destination = NavigationDDestination,
                getActiveIcon = { Icons.Filled.NoDrinks },
                getInactiveIcon = { Icons.Outlined.NoDrinks },
                getLabel = { "D" }
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