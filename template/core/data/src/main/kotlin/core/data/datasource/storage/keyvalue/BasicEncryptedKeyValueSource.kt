package core.data.datasource.storage.keyvalue

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.tinylog.Logger

class BasicEncryptedKeyValueSource(private val app: Application) : AbstractKeyValueSource(), EncryptedKeyValueSource {

    private val fileName = "encrypted_key_value_source"

    override fun createSharedPreferences(): SharedPreferences {
        return try {
            create()
        } catch (e: Exception) {
            Logger.error(e, "createSharedPreferences")
            app.deleteSharedPreferences(fileName)
            create()
        }
    }

    private fun create(): SharedPreferences {
        return EncryptedSharedPreferences.create(
            app,
            fileName,
            MasterKey.Builder(app).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}