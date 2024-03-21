package core.data.datasource.encryption

import core.data.datasource.storage.keyvalue.EncryptedKeyValueSource
import core.data.misc.utils.EncryptUtils

/**
 * A basic implementation of [EncryptionSource] that utilizes an [EncryptedKeyValueSource] for handling encryption and decryption of strings.
 *
 * @param keyValueSource The source for key-value storage of encrypted data.
 */
class BasicEncryptionSource(private val keyValueSource: EncryptedKeyValueSource) : EncryptionSource {

    private val password by lazy {
        keyValueSource.read(KEY_PASSWORD, ByteArray::class.java) ?: run {
            val password = EncryptUtils.generatePassword()
            keyValueSource.save(KEY_PASSWORD, password, ByteArray::class.java)
        }
    }

    override fun fromDecrypted(string: String?): EncryptedString {
        return BasicEncryptedString(
            encrypted = null,
            decrypted = string,
            password = password
        )
    }

    override fun fromEncrypted(string: String?): EncryptedString {
        return BasicEncryptedString(
            encrypted = string,
            decrypted = null,
            password = password
        )
    }

    companion object {
        private const val KEY_PASSWORD = "KEY_PASSWORD"
    }

}