package core.datasource.encryption

interface IEncryptedObjectSource {

    fun fromDecryptedString(string: String?): IEncryptedString

    fun fromEncryptedString(string: String?): IEncryptedString

}