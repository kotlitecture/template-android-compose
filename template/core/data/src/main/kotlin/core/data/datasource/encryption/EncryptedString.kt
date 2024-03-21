package core.data.datasource.encryption

/**
 * Interface representing an encrypted string, providing methods to retrieve the decrypted value, the encrypted value,
 * and the password used for encryption.
 */
interface EncryptedString {

    /**
     * Retrieves the decrypted value of the encrypted string.
     *
     * @return The decrypted string, or null if decryption fails.
     */
    fun decrypted(): String?

    /**
     * Retrieves the encrypted value of the string.
     *
     * @return The encrypted string.
     */
    fun encrypted(): String?

    /**
     * Retrieves the password used for encryption.
     *
     * @return The password as a byte array.
     */
    fun password(): ByteArray?

}