package core.data.datasource.encryption

interface EncryptionSource {

    fun fromDecrypted(string: String?): EncryptedString

    fun fromEncrypted(string: String?): EncryptedString

}