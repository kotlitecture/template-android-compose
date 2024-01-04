package core.datasource.encryption

interface IEncryptedObject<T> {

    fun decryptedValue(): T?

    fun encryptedValue(): String?

    fun getPassword(): ByteArray?

}