## Overview

The API can be accessed through:
- `core.data.datasource.config.ConfigSource` - facade interface at the core module level.
- `app.datasource.config.AppConfigSource` - decorator class at the app level.

The difference is that the class serves as a **decorator** and can provide extra methods without impacting facade implementations. 

Facade *ConfigSource* provides the following methods:

- `<T> get(key: String, type: Class<T>, defaultValue: () -> T): T` - Retrieves the value associated with the specified key from the configuration source.
- `getString(key: String, defaultValue: () -> String): String` - Retrieves string value associated with the specified key from the configuration source.
- `getBoolean(key: String, defaultValue: () -> Boolean): Boolean` - Retrieves boolean value associated with the specified key from the configuration source.
- `getLong(key: String, defaultValue: () -> Long): Long` - Retrieves long value associated with the specified key from the configuration source.
- `getInt(key: String, defaultValue: () -> Int): Int` - Retrieves int value associated with the specified key from the configuration source.
- `getDouble(key: String, defaultValue: () -> Int): Int` - Retrieves double value associated with the specified key from the configuration source.
- `getFloat(key: String, defaultValue: () -> Float): Float` - Retrieves float value associated with the specified key from the configuration source.

## Get started

Both the facade and decorator are pre-configured via dependency injection (DI) as singletons in `app.di.ProvidesConfigSource`.

*However*, it is recommended to use decorator methods instead of directly accessing facade methods,
as the latter requires providing an extra parameter `defaultValue`, which might be hidden in the decorator.

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

## Changing implementation

Follow these two steps:

1. Implement the `core.data.datasource.config.ConfigSource` interface.
2. Register your implementation in `app.datasource.config.AppConfigSource`.