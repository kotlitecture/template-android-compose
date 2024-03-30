## Overview

The implementation is available as the `app.datasource.analytics.firebase.FirebaseAnalyticsSource` class. This class implements the `core.data.datasource.analytics.AnalyticsSource` interface. Direct access to this class is not recommended.

The API can be accessed through:
- `core.data.datasource.analytics.AnalyticsSource` - facade interface at the core module level.
- `app.datasource.analytics.AppAnalyticsSource` - decorator class at the app level.

The difference is that the class serves as a **decorator** and can provide extra methods without impacting facade implementations.

## Example

Both the **facade** and **decorator** are pre-configured via dependency injection (DI) as singletons in `app.di.datasource.ProvidesAnalyticsSource`.

To start using, just inject any of them to your DI managed class. Recommended to use from `ViewModel` or `Repository` level.

```kotlin
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val analyticsSource: AnalyticsSource // AppAnalyticsSource,
) : AppViewModel() {

    fun onSomeAction() {
        ...
        analyticsSource.onEvent("my_event")
        ...
    }
}
```