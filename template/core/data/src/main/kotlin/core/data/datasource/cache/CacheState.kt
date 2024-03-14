package core.data.datasource.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface CacheState<T> {

    val key: CacheKey<T>

    suspend fun get(): T?

    suspend fun last(): T?

    suspend fun fresh(): T?

    suspend fun lastOrFresh(): T?

    suspend fun changes(): Flow<T>

    companion object {
        fun <T> single(key: CacheKey<T>, item: T): CacheState<T> = object : CacheState<T> {
            override val key: CacheKey<T> = key
            override suspend fun get(): T? = item
            override suspend fun last(): T? = item
            override suspend fun fresh(): T? = item
            override suspend fun lastOrFresh(): T? = item
            override suspend fun changes(): Flow<T> = flowOf(item)
        }
    }

}