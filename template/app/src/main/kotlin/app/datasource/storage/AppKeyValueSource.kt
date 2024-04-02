package app.datasource.storage

import android.app.Application
import core.data.datasource.storage.keyvalue.SharedPreferencesSource

/**
 * Decorator class for working with key-value storage on the app level.
 *
 * Can provide extra methods without impacting facade implementations.
 */
class AppKeyValueSource(app: Application) : SharedPreferencesSource(app)