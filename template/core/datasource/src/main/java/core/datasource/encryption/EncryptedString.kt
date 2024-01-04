package core.datasource.encryption

import core.essentials.misc.utils.EncryptUtils

internal class EncryptedString(
    private val encrypted: String?,
    private val decrypted: String?,
    private val password: ByteArray?
) : IEncryptedString {

    override fun getPassword(): ByteArray? {
        return password
    }

    override fun encryptedValue(): String? {
        if (encrypted != null) return encrypted
        if (decrypted == null) return null
        if (password == null) return decrypted
        return try {
            EncryptUtils.encryptAes(password, decrypted)
        } catch (e: Exception) {
            decrypted
        }
    }

    override fun decryptedValue(): String? {
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