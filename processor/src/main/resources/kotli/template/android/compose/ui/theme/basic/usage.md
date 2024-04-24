## Overview

- Component package: `app.ui.theme`
- State management: `core.ui.theme.ThemeState`
- DI integration: `app.di.state.ProvidesThemeState`
- Behavior management: `app.ui.theme.AppThemeViewModel`

This state instance is utilized by `app.ui.theme.AppThemeProvider`, which is pre-defined at the activity level to furnish themes for the entire application.

```kotlin
@AndroidEntryPoint
class AppActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppThemeProvider {
                // ...
                // app scaffold
                // ...
            }
        }
    }

}
```

The behavior is managed by the `app.ui.theme.AppThemeViewModel` class, which applies the default **ThemeConfig** from the provided state instance.

## Manually change current theme

By default, the theme follows the system preferences. To manually change the current theme, you just need to instruct the `ThemeState` instance to use a new **ThemeConfig**. All underlying logic is handled automatically by the `AppThemePersistenceViewModel`.

```kotlin
@HiltViewModel
class ChangeThemeViewModel @Inject constructor(
    private val themeState: ThemeState
) : BaseViewModel() {

    fun onChangeThemeToLight() {
        themeState.configStore.set(
            ThemeConfig(
                ...
            )
        )
    }
}
```