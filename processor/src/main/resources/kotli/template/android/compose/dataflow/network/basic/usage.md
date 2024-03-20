## Overview

The API can be accessed through:
- `core.data.datasource.network.NetworkSource` - facade interface at the core module level.
- `app.datasource.network.AppNetworkSource` - decorator class at the app level.

The difference is that the class serves as a **decorator** and can provide extra methods without impacting facade implementations.

Facade *NetworkSource* provides the following methods:

- `isOnline(): Flow<Boolean>` - Retrieves a flow representing the online status of the device.
- `getIp(): String?` - Retrieves the IP address of the device.

## Get started

Both the facade and decorator are pre-configured via dependency injection (DI) as singletons in `app.di.ProvidesNetworkSource`.

To start using, just inject it to your DI managed class.

```kotlin
@HiltViewModel
class NoInternetViewModel @Inject constructor(
    private val networkSource: NetworkSource // AppNetworkSource
) : AppViewModel() {

    val isOnlineStore = StoreObject(true)

    override fun doBind() {
        launchAsync("doBind") {
            networkSource.isOnline()
                .filterNotNull()
                .collect(isOnlineStore::set)
        }
    }

}
```

## Changing implementation

Just edit `app.datasource.network.AppNetworkSource` however you like.