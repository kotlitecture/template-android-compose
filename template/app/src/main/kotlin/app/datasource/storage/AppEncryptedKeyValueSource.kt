package app.datasource.storage

import android.app.Application
import core.data.datasource.storage.keyvalue.EncryptedSharedPreferencesSource

/**
 * Decorator class for working with encrypted key-value storage on the app level.
 *
 * Can provide extra methods without impacting facade implementations.
 */
class AppEncryptedKeyValueSource(app: Application) : EncryptedSharedPreferencesSource(app)