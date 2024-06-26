package core.data.datasource.cache

import core.data.datasource.DataSource

/**
 * Provides an interface for a basic thread-safe caching mechanism, serving as an L1 Cache.
 *
 * The cache allows for storing and retrieving any in-memory data efficiently.
 *
 * It supports operations such as getting, putting, removing, and invalidating cache entries.
 */
interface CacheSource : DataSource {

    /**
     * Retrieves the state of a cache entry associated with the specified key.
     * If the entry is not found in the cache, the provided value provider function is invoked to obtain the value.
     *
     * @param key The cache key associated with the entry.
     * @param valueProvider A suspend function that provides the value if the cache entry is not found.
     * @return A CacheState object representing the state of the cache entry.
     */
    fun <T> getState(key: CacheKey<T>, valueProvider: suspend () -> T?): CacheState<T>

    /**
     * Retrieves the value associated with the specified key from the cache.
     * If the value is not found in the cache, the provided value provider function is invoked to obtain the value.
     *
     * @param key The cache key associated with the value.
     * @param valueProvider A suspend function that provides the value if it is not found in the cache.
     * @return The value associated with the key, or null if not found.
     */
    suspend fun <T> get(key: CacheKey<T>, valueProvider: suspend () -> T?): T?

    /**
     * Invalidates all cache entries associated with the specified key type.
     *
     * @param type The type of cache keys to invalidate.
     */
    fun <K : CacheKey<*>> invalidate(type: Class<K>)

    /**
     * Invalidates the cache entry associated with the specified key.
     *
     * @param key The cache key to invalidate.
     */
    fun <K : CacheKey<*>> invalidate(key: K)

    /**
     * Removes all cache entries associated with the specified key type.
     *
     * @param type The type of cache keys to remove.
     */
    fun <K : CacheKey<*>> remove(type: Class<K>)

    /**
     * Removes the cache entry associated with the specified key.
     *
     * @param key The cache key to remove.
     */
    fun <K : CacheKey<*>> remove(key: K)

    /**
     * Associates the specified value with the specified key in the cache.
     *
     *@param key The cache key to associate with the value.
     * @param value The value to be stored in the cache.
     */
    fun <T> put(key: CacheKey<T>, value: T)

    /**
     * Clears all entries from the cache.
     */
    fun clear()

}