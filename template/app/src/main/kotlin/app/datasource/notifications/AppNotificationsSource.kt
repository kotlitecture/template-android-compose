package app.datasource.notifications

import android.app.Application
import core.data.datasource.notifications.BasicNotificationsSource

/**
 * Decorator class for working with notifications on the device.
 *
 * Can provide extra methods without impacting facade implementations.
 */
class AppNotificationsSource(app: Application) : BasicNotificationsSource(app)