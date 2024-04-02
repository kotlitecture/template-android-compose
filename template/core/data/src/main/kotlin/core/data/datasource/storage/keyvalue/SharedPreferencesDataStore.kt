package core.data.datasource.storage.keyvalue

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import core.data.serialization.SerializationStrategy
import kotlinx.coroutines.flow.first

/**
 * Key-value data source implementation using [DataStore].
 *
 * @param app The application context.
 */
open class SharedPreferencesDataStore(
    protected val app: Application,
    name: String = "data_store_source"
) : KeyValueSource {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = name)

    override suspend fun <T> save(key: String, value: T, serializationStrategy: SerializationStrategy<T>) {
        val prefsKey = defineKey(key, serializationStrategy.getType())
        app.dataStore.edit { prefs ->
            if (prefsKey != null) {
                prefs[prefsKey] = value
            } else {
                val jsonKey = stringPreferencesKey(key)
                prefs[jsonKey] = serializationStrategy.toString(value).orEmpty()
            }
        }
    }

    override suspend fun <T> read(key: String, serializationStrategy: SerializationStrategy<T>): T? {
        val prefsKey = defineKey(key, serializationStrategy.getType())
        if (prefsKey != null) {
            return app.dataStore.data.first()[prefsKey]
        } else {
            val jsonKey = stringPreferencesKey(key)
            val jsonString = app.dataStore.data.first()[jsonKey] ?: return null
            return serializationStrategy.toObject(jsonString)
        }
    }

    override suspend fun <T> remove(key: String, serializationStrategy: SerializationStrategy<T>): T? {
        val prev = read(key, serializationStrategy)
        app.dataStore.edit { prefs ->
            prefs.remove(defineKey(key, serializationStrategy.getType())
                ?: stringPreferencesKey(key))
        }
        return prev
    }

    override suspend fun clear() {
        app.dataStore.edit { prefs -> prefs.clear() }
    }

    private fun <T> defineKey(key: String, type: Class<T>): Preferences.Key<T>? {
        val prefsKey = when (type) {
            String::class.java -> stringPreferencesKey(key)

            java.lang.Boolean::class.java,
            Boolean::class.java -> booleanPreferencesKey(key)

            java.lang.Integer::class.java,
            Int::class.java -> intPreferencesKey(key)

            java.lang.Long::class.java,
            Long::class.java -> longPreferencesKey(key)

            java.lang.Float::class.java,
            Float::class.java -> floatPreferencesKey(key)

            java.lang.Double::class.java,
            Double::class.java -> doublePreferencesKey(key)

            else -> null
        }
        return prefsKey as? Preferences.Key<T>
    }

}