package core.datasource.encryption

import core.datasource.storage.IEncryptedKeyValueSource
import core.essentials.misc.utils.EncryptUtils

class EncryptedObjectSource(private val keyValueSource: IEncryptedKeyValueSource) : IEncryptedObjectSource {

    private val password by lazy {
        keyValueSource.read(KEY_PASSWORD, ByteArray::class.java) ?: run {
            val password = EncryptUtils.generatePassword()
            keyValueSource.save(KEY_PASSWORD, password, ByteArray::class.java)
        }
    }

    override fun fromDecryptedString(string: String?): IEncryptedString {
        return EncryptedString(
            encrypted = null,
            decrypted = string,
            password = password
        )
    }

    override fun fromEncryptedString(string: String?): IEncryptedString {
        return EncryptedString(
            encrypted = string,
            decrypted = null,
            password = password
        )
    }

    companion object {
        private const val KEY_PASSWORD = "KEY_PASSWORD"
    }

}