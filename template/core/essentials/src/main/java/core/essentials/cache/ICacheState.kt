package core.essentials.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface ICacheState<T> {

    val key: ICacheKey<T>

    var value: T?

    suspend fun get(): T?

    suspend fun getLast(): T?

    suspend fun getFresh(): T?

    suspend fun getChanges(): Flow<T>

    companion object {
        fun <T> single(key: ICacheKey<T>, item: T): ICacheState<T> = object : ICacheState<T> {
            override val key: ICacheKey<T> = key
            override var value: T? = item
            override suspend fun get(): T? = item
            override suspend fun getLast(): T? = item
            override suspend fun getFresh(): T? = item
            override suspend fun getChanges(): Flow<T> = flowOf(item)
        }
    }

}