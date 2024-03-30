## Overview

Themes are managed through `core.ui.theme.ThemeState` instance.
This instance is pre-configured in dependency injection (DI) through the `app.di.state.ProvidesThemeState` class.

You can modify the themes provided and add your own to the configuration easily.

```kotlin
@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesThemeState {

    @Provides
    @Singleton
    fun state(): ThemeState = ThemeState(
        getConfig = {
            // Returns either the default or previously saved user-specific configuration.
            ThemeConfig(
                availableThemes = listOf(Material3Light, Material3Dark),
                defaultTheme = Material3Light,
                lightTheme = Material3Light,
                darkTheme = Material3Dark,
            )
        },
        setConfig = {
            // Handles changes in the configuration.
        }
    )

}
```

