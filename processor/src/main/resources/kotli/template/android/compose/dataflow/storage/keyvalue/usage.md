## Overview

The API can be accessed through:
- `core.data.datasource.storage.keyvalue.KeyValueSource` - facade interface at the core module level.
- `app.datasource.storage.keyvalue.AppKeyValueSource` - decorator class at the app level.

The difference is that the class serves as a **decorator** and can provide extra methods without impacting facade implementations.

Facade *KeyValueSource* provides the following methods:

- `save(key: String, value: T, valueType: Class<T>): T` - Saves the specified key-value pair.
- `read(key: String, returnType: Class<T>): T?` - Reads the value associated with the specified key.
- `remove(key: String)` - Removes the value associated with the specified key.
- `clear()` - Clears all key-value pairs.

Decorator *AppKeyValueSource* also provides the following methods:

- `read(key: String): T?` - Reads data of type [T] associated with the given [key].
- `save(key: String, value: T)` - Saves the provided [value] of type [T] with the given [key].

These methods are **reified**.

## Get started

Both the facade and decorator are pre-configured via dependency injection (DI) as singletons in `app.di.ProvidesKeyValueSource`.

To start using, just inject it to your DI managed class.

```kotlin
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val keyValueSource: KeyValueSource // AppKeyValueSource
) : AppViewModel() {
    ...
}
```

## Changing implementation

Just edit `app.datasource.storage.keyvalue.AppKeyValueSource` however you like.