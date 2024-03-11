package core.dataflow.datasource.encryption

interface EncryptionSource {

    fun fromDecrypted(string: String?): EncryptedString

    fun fromEncrypted(string: String?): EncryptedString

}