package core.datasource.work.flow

import core.datasource.work.IWork
import core.datasource.work.model.WorkState
import core.essentials.cache.ICacheKey

interface IWorkFlow<I, O> : IWork<O> {

    suspend fun resume()

    suspend fun execute(): O?

    suspend fun getInput(): I

    suspend fun getOutput(): O?

    suspend fun awaitOutput(): O?

    suspend fun cancel(state: WorkState)

    suspend fun getChildren(): List<IWorkFlow<*, *>>

    data class CacheKey<I : IWorkFlow<*, *>>(
        val id: String,
        override val ttl: Long = ICacheKey.TTL_UNLIMITED
    ) : ICacheKey<I>

}