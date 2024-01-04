package core.essentials.cache

interface ICacheSource {

    suspend fun <T> getState(key: ICacheKey<T>, valueProvider: suspend () -> T?): ICacheState<T>

    suspend fun <T> get(key: ICacheKey<T>, valueProvider: suspend () -> T?): T?

    suspend fun <T> put(key: ICacheKey<T>, value: T)

    fun <K : ICacheKey<*>> invalidate(type: Class<K>)

    fun <K : ICacheKey<*>> invalidate(key: K)

    fun <K : ICacheKey<*>> remove(type: Class<K>)

    fun <K : ICacheKey<*>> remove(key: K)

    fun clear()

}