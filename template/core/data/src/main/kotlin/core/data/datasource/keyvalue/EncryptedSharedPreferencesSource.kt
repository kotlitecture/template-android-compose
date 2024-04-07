package core.data.datasource.keyvalue

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Key-value implementation of encrypted data using EncryptedSharedPreferences.
 *
 * @param app The application context.
 */
open class EncryptedSharedPreferencesSource(
    protected val app: Application,
    private val name: String = "encrypted_key_value_source"
) : BaseSharedPreferencesSource(), EncryptedKeyValueSource {

    override fun createSharedPreferences(): SharedPreferences {
        return try {
            create()
        } catch (e: Exception) {
            app.deleteSharedPreferences(name)
            create()
        }
    }

    private fun create(): SharedPreferences {
        return EncryptedSharedPreferences.create(
            app,
            name,
            MasterKey.Builder(app).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}