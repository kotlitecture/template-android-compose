package core.data.datasource.encryption

import core.data.misc.utils.EncryptUtils

/**
 * Basic implementation of the [EncryptedString] interface.
 * This class handles encryption and decryption of strings using AES algorithm with the provided password.
 *
 * @property encrypted The encrypted string.
 * @property decrypted The decrypted string.
 * @property password The password used for encryption and decryption.
 */
internal class BasicEncryptedString(
    private val encrypted: String?,
    private val decrypted: String?,
    private val password: ByteArray?
) : EncryptedString {

    override fun password(): ByteArray? {
        return password
    }

    override fun encrypted(): String? {
        if (encrypted != null) return encrypted
        if (decrypted == null) return null
        if (password == null) return decrypted
        return try {
            EncryptUtils.encryptAes(password, decrypted)
        } catch (e: Exception) {
            decrypted
        }
    }

    override fun decrypted(): String? {
        if (decrypted != null) return decrypted
        if (encrypted == null) return null
        if (password == null) return encrypted
        return try {
            EncryptUtils.decryptAes(password, encrypted)
        } catch (e: Exception) {
            encrypted
        }
    }

}