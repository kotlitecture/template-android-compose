## Overview

- Component package: `app.ui.navigation.left.modal`
- DI integration: `app.di.state.ProvidesNavigationBarState`
- State management: `app.ui.navigation.NavigationBarState`
- Pre-configured destinations package: `app.userflow.navigation`


## Configuration

Configure your destinations using `ProvidesNavigationBarState`, and if necessary, specify any restricted or allowed destinations which will force navigation to show/hide the navigation bar in some cases.

```kotlin
@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesNavigationBarState {

    @Provides
    @Singleton
    fun state(navigationState: NavigationState): NavigationBarState = NavigationBarState(
        pages = listOf(
            createPage(
                navigationState = navigationState,
                destination = ShowcasesDestination,
                getActiveIcon = { Icons.Filled.School },
                getInactiveIcon = { Icons.Outlined.School },
                getLabel = { "Showcases" }
            ),
            ...
        ),
        restrictedDestinations = setOf(
            UnlockPasscodeDestination
        ),
        allowedDestinations = setOf(
            ShowcasesDestination
        )
    )
}
```