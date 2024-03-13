package core.data.datasource.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface CacheState<T> {

    val key: CacheKey<T>

    var value: T?

    suspend fun get(): T?

    suspend fun getLast(): T?

    suspend fun getFresh(): T?

    suspend fun getChanges(): Flow<T>

    companion object {
        fun <T> single(key: CacheKey<T>, item: T): CacheState<T> = object : CacheState<T> {
            override val key: CacheKey<T> = key
            override var value: T? = item
            override suspend fun get(): T? = item
            override suspend fun getLast(): T? = item
            override suspend fun getFresh(): T? = item
            override suspend fun getChanges(): Flow<T> = flowOf(item)
        }
    }

}