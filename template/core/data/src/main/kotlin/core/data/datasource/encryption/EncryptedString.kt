package core.data.datasource.encryption

interface EncryptedString {

    fun decrypted(): String?

    fun encrypted(): String?

    fun password(): ByteArray?

}