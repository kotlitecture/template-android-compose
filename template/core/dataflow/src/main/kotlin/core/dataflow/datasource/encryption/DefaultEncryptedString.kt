package core.dataflow.datasource.encryption

import core.dataflow.misc.utils.EncryptUtils

internal class DefaultEncryptedString(
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