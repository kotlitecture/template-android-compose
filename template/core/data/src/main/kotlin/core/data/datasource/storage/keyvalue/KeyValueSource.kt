package core.data.datasource.storage.keyvalue

import core.data.datasource.DataSource

/**
 * Provides functionality to save, read, remove, and clear key-value pairs.
 */
interface KeyValueSource : DataSource {

    /**
     * Saves the specified key-value pair.
     *
     * @param key The key to save.
     * @param value The value to save.
     * @param valueType The class type of the value.
     * @return The saved value.
     */
    fun <T> save(key: String, value: T, valueType: Class<T>): T

    /**
     * Reads the value associated with the specified key.
     *
     * @param key The key to read.
     * @param returnType The class type of the value.
     * @return The value associated with the key, or `null` if the key does not exist.
     */
    fun <T> read(key: String, returnType: Class<T>): T?

    /**
     * Removes the value associated with the specified key.
     *
     * @param key The key to remove.
     */
    fun remove(key: String)

    /**
     * Clears all key-value pairs.
     */
    fun clear()

}