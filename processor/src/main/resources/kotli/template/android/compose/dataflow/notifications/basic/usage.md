## Overview

The API can be accessed through:
- `core.data.datasource.notifications.NotificationsSource` - facade interface at the core module level.
- `app.datasource.notifications.AppNotificationsSource` - decorator class at the app level.

The difference is that the class serves as a **decorator** and can provide extra methods without impacting facade implementations.

Facade **NotificationsSource** provides the following methods:

- `enable()` - Enable all notifications.
- `areEnabled(): Boolean` - Check if notifications are enabled.
- `enable(channelId: String)` - Enable notifications for the specified channel.
- `disable(channelId: String)` - Disable notifications for the specified channel.
- `isEnabled(channelId: String): Boolean` - Check if notifications are enabled for the specified channel.

## Example

Both the **facade** and **decorator** are pre-configured via dependency injection (DI) as singletons in `app.di.ProvidesNotificationsSource`.

To start using, just inject it to your DI managed class.

```kotlin
@HiltViewModel
class TemplateViewModel @Inject constructor(
    private val notificationsSource: NotificationsSource // AppNotificationsSource
) : AppViewModel() {

    fun onEnable() {
        notificationsSource.enable()
    }

    ...
}
```