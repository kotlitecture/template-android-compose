package core.data.datasource.encryption

import core.data.datasource.storage.EncryptedKeyValueSource
import core.data.misc.utils.EncryptUtils

class DefaultEncryptionSource(private val keyValueSource: EncryptedKeyValueSource) : EncryptionSource {

    private val password by lazy {
        keyValueSource.read(KEY_PASSWORD, ByteArray::class.java) ?: run {
            val password = EncryptUtils.generatePassword()
            keyValueSource.save(KEY_PASSWORD, password, ByteArray::class.java)
        }
    }

    override fun fromDecrypted(string: String?): EncryptedString {
        return DefaultEncryptedString(
            encrypted = null,
            decrypted = string,
            password = password
        )
    }

    override fun fromEncrypted(string: String?): EncryptedString {
        return DefaultEncryptedString(
            encrypted = string,
            decrypted = null,
            password = password
        )
    }

    companion object {
        private const val KEY_PASSWORD = "KEY_PASSWORD"
    }

}