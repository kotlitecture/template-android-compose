package core.data.datasource.encryption

/**
 * Interface representing a source for handling encryption and decryption of strings.
 */
interface EncryptionSource {

    /**
     * Converts a decrypted string into an encrypted string.
     *
     * @param string The decrypted string to encrypt.
     * @return An EncryptedString object representing the encrypted string.
     */
    fun fromDecrypted(string: String?): EncryptedString

    /**
     * Converts an encrypted string into a decrypted string.
     *
     * @param string The encrypted string to decrypt.
     * @return An EncryptedString object representing the decrypted string.
     */
    fun fromEncrypted(string: String?): EncryptedString

}