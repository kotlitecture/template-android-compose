package core.data.datasource.encryption

import core.data.datasource.keyvalue.EncryptedKeyValueSource
import core.data.misc.utils.EncryptUtils
import core.data.serialization.ByteArrayStrategy
import kotlinx.coroutines.runBlocking

/**
 * A basic implementation of [EncryptionSource] that utilizes an [EncryptedKeyValueSource] for handling encryption and decryption of strings.
 *
 * @param keyValueSource The source for key-value storage of encrypted data.
 */
class BasicEncryptionSource(private val keyValueSource: EncryptedKeyValueSource) : EncryptionSource {

    private val password: ByteArray by lazy {
        runBlocking { keyValueSource.read(KEY_PASSWORD, ByteArrayStrategy) } ?: runBlocking {
            val password = EncryptUtils.generatePassword()
            keyValueSource.save(KEY_PASSWORD, password, ByteArrayStrategy)
            password
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