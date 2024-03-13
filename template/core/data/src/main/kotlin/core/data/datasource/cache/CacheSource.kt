package core.data.datasource.cache

import core.data.datasource.DataSource

interface CacheSource : DataSource {

    suspend fun <T> getState(key: CacheKey<T>, valueProvider: suspend () -> T?): CacheState<T>

    suspend fun <T> get(key: CacheKey<T>, valueProvider: suspend () -> T?): T?

    suspend fun <T> put(key: CacheKey<T>, value: T)

    fun <K : CacheKey<*>> invalidate(type: Class<K>)

    fun <K : CacheKey<*>> invalidate(key: K)

    fun <K : CacheKey<*>> remove(type: Class<K>)

    fun <K : CacheKey<*>> remove(key: K)

    fun clear()

}