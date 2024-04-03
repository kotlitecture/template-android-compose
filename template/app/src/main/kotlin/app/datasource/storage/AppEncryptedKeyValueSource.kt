package app.datasource.storage

import android.app.Application
import core.data.datasource.storage.keyvalue.EncryptedSharedPreferencesSource
import core.data.serialization.NoSerializationStrategy

/**
 * Decorator class for working with encrypted key-value storage on the app level.
 *
 * Can provide extra methods without impacting facade implementations.
 */
class AppEncryptedKeyValueSource(app: Application) : EncryptedSharedPreferencesSource(app) {

    suspend inline fun <reified T> read(key: String): T? {
        return read(key, NoSerializationStrategy.create())
    }

    suspend inline fun <reified T> save(key: String, value: T) {
        save(key, value, NoSerializationStrategy.create())
    }

}