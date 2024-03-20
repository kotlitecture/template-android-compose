## Overview

The API can be accessed through:
- `core.data.datasource.storage.keyvalue.EncryptedKeyValueSource` - facade interface at the core module level.
- `app.datasource.storage.keyvalue.AppEncryptedKeyValueSource` - decorator class at the app level.

The difference is that the class serves as a **decorator** and can provide extra methods without impacting facade implementations.

Facade *EncryptedKeyValueSource* provides the following methods:

- `save(key: String, value: T, valueType: Class<T>): T` - Saves the specified key-value pair.
- `read(key: String, returnType: Class<T>): T?` - Reads the value associated with the specified key.
- `remove(key: String)` - Removes the value associated with the specified key.
- `clear()` - Clears all key-value pairs.

Decorator *AppEncryptedKeyValueSource* also provides the following methods:

- `read(key: String): T?` - Reads data of type [T] associated with the given [key].
- `save(key: String, value: T)` - Saves the provided [value] of type [T] with the given [key].

These methods are **reified**.

## Get started

Both the facade and decorator are pre-configured via dependency injection (DI) as singletons in `app.di.ProvidesEncryptedKeyValueSource`.

To start using, just inject it to your DI managed class.

```kotlin
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val encryptedKeyValueSource: EncryptedKeyValueSource // AppEncryptedKeyValueSource
) : AppViewModel() {
    ...
}
```

## Changing implementation

Just edit `app.datasource.storage.keyvalue.AppEncryptedKeyValueSource` however you like.