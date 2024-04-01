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

## Provide Theme

To utilize the theme, simply enclose your composable function within `core.ui.theme.ThemeProvider`.
By default, the entire app scaffold is already enclosed at the `app.AppActivity` level and does not require any changes.

```kotlin
@AndroidEntryPoint
class AppActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: AppViewModel = provideHiltViewModel()
            ThemeProvider(viewModel.themeState) {
                // app scaffold
            }
        }
    }

}
```

