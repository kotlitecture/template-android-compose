## Register Destinations

All app destinations should be registered within an instance of the class `core.ui.navigation.NavigationState`.
This instance is already pre-configured in dependency injection (DI) through the `app.di.state.ProvidesNavigationState` class.
Simply place all your destinations within this instance.

```kotlin
@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesNavigationState {

    @Provides
    @Singleton
    fun state(): NavigationState = NavigationState(
        listOf(
            TemplateDestination,
            WebToNativeDestination,
            ...
        )
    )

}
```

## Navigate to Destination

Once the navigation is aware of the destinations, you can initiate navigation to them using the `onBack` and `onNext` methods available in the `core.ui.navigation.NavigationState` class.
Simply inject `NavigationState` into your `ViewModel` or other dependency injection managed class, and navigate whenever necessary.

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val navigationState: NavigationState
) : AppViewModel() {

    fun onBack() {
        navigationState.onBack()
    }
    
    fun onShowSettings() {
        navigationState.onNext(SettingsDestination)
    }

}
```

## Set Initial App Destination

When the app is first opened, you need to provide **NavigationState** with an initial destination. It can be done in pre-configured **AppActivityViewModel** class.

```kotlin
@HiltViewModel
class AppActivityViewModel @Inject constructor(
    val navigationState: NavigationState,
    val themeState: ThemeState,
    val appState: AppState,
) : AppViewModel() {

    override fun doBind() {
        launchAsync("doBind") {
            // You can perform some logic before setting the initial destination.
            navigationState.startDestinationStore.set(TemplateDestination)
        }
    }

}
```


