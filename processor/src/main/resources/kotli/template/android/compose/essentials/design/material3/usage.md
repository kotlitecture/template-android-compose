## Overview

- Component package: `app.ui.theme`
- State management: `core.ui.theme.ThemeState`
- DI integration: `app.di.state.ProvidesThemeState`

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

## Change Themes

By default, `ThemeState` is initialized with pre-defined dark and light themes. To edit these themes:

1. Visit the [Material 3 Theme Builder](https://m3.material.io/theme-builder#/custom).
2. Customize the desired color theme.
3. Click on the **Export** button and confirm exporting as **Jetpack Compose (Theme.kt)**.
4. Paste the exported files (**Theme.kt** and **Color.kt**) into the package `core.ui.theme.material3` and update their package declaration accordingly.
5. Direct the **Material3Themes** class to utilize the color themes from the exported classes:
   - Mark the variables **LightColors** and **DarkColors** in the exported **Theme.kt** file as internal.
   - Omit the **AppTheme** composable from this file, as it is not utilized by the template.

The themes can be declared directly in the app module. However, if you plan to use feature modules, it might be beneficial to declare the theme in the common module.
