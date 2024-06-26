## Overview

Component package: `app.userflow.loader.data`

The component is pre-configured at the `app.AppActivity` level to monitor state changes of the `app.AppState` instance.

If you need to modify the behavior, including colors, texts, logic, etc., simply update the `app.userflow.loader.data.DataLoaderProvider` composable.

## Example

To control the loader state, you need to change the value of `app.AppState#dataStateStore`.
By default, this can be achieved through the provided **MVVM** functionality.

### Usage via provided MVVM Framework

Simply inject the `app.AppState` instance into your **ViewModel** and call the `launchAsync` or `launchMain` method by passing the obtained state instance.

```kotlin
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val appState: AppState
) : BaseViewModel() {

    fun onActionWithLoader() {
        launchAsync("onBottom", appState) {
            delay(1000) // just delay to simulate an action
        }
    }

}
```

### Usage from anywhere

Inject the `app.AppState` instance into your DI-managed class and modify its loading state to control the visibility of the loader.
However, it is recommended to use it only at the **ViewModel** level.

```kotlin
@Singleton
class DataRepository @Inject constructor(private val appState: AppState) {

    fun performSomeAction() {
        val id = "performSomeAction"
        try {
            appState.loading(id)
            // some action
            appState.loaded(id)
        } catch (e: Exception) {
            appState.error(id, e)
        }
    }

}
```

### Usage without `app.AppState`

`app.userflow.loader.data.DataLoaderProvider` accepts any `StoreState` instance. As this is your code, feel free to use it as needed.