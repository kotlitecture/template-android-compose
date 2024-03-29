## Overview

The implementation is available as the `app.datasource.config.firebase.FirebaseConfigSource` class. This class implements the `core.data.datasource.config.ConfigSource` interface. Direct access to this class is not recommended.

The API can be accessed through:
- `core.data.datasource.config.ConfigSource` - facade interface at the core module level.
- `app.datasource.config.AppConfigSource` - decorator class at the app level.

## Example

Both the **facade** and **decorator** are pre-configured via dependency injection (DI) as singletons in `app.di.datasource.ProvidesConfigSource`.

However, it is recommended to use decorator methods instead of directly accessing facade methods, as the latter requires providing an extra parameter `defaultValue`, which might be hidden in the decorator.

To start using, just inject any of them to your DI managed class.

```kotlin
class AppConfigSource : DelegateConfigSource() {
    fun getCounterInitialValue(): Int = getInt("counter_initial_value") { 100 }
    ...
}

@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val configSource: AppConfigSource // ConfigSource
) : AppViewModel() {

    private val counter by lazy { AtomicInteger(configSource.getCounterInitialValue()) }
    
    ...
}
```