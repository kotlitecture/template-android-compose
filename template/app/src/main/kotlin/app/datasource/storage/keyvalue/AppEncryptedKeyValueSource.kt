package app.datasource.storage.keyvalue

import android.app.Application
import core.data.datasource.storage.keyvalue.BasicEncryptedKeyValueSource

/**
 * Decorator class for working with encrypted key-value storage on the app level.
 *
 * Can provide extra methods without impacting facade implementations.
 */
class AppEncryptedKeyValueSource(app: Application) : BasicEncryptedKeyValueSource(app)