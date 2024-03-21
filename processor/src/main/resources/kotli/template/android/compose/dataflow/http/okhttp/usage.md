## Overview

The data source is available within the class `core.data.datasource.http.okhttp.OkHttpSource`. An instance of this class can be obtained through dependency injection (DI) as a singleton in `app.di.ProvidesOkHttpSource`.

The class provides the next functionality:

- `okhttp` - pre-configured HTTP client to work with HTTP through **OkHttp API**.
- `ktor` - pre-configured HTTP client to work with HTTP through **Ktor API**.

## Example

To start using, just inject it to your DI managed class.

```kotlin
@Singleton
class ApiSource @Inject constructor(private val httpSource: OkHttpSource) {

    suspend fun getIp(): String {
        val ktor = httpSource.ktor
        return ktor.get("https://api64.ipify.org").body<String>()
    }

}
```

### WebSocket API

The data source also offers an API to acquire WebSocket connections, which can remain open until there are no active subscribers within a predefined number of milliseconds.

```kotlin
@Singleton
class ApiSource @Inject constructor(private val httpSource: OkHttpSource) {

    suspend fun getStockChanges(id:String): Flow<String> {
        return httpSource.get(id) {
            ktor.wss(
                urlString = "wss://my.stocks.api",
                request = {
                    parameter("stock", id)
                },
                block = {
                    incoming.consumeAsFlow()
                        .filterNotNull()
                        .mapNotNull { it.readBytes().decodeToString() }
                        .collect { changes -> emit(changes) }
                    close()
                })
        }
    }

}
```

