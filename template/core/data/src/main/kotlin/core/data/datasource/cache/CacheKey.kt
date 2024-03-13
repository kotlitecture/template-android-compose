package core.data.datasource.cache

interface CacheKey<T> {

    val ttl: Long

    fun isImmortal(): Boolean = false

    fun throwErrors(): Boolean = false

    companion object {
        const val TTL_UNLIMITED = -1L
        const val TTL_1_SECOND = 1_000L
        const val TTL_3_SECONDS = 3_000L
        const val TTL_5_SECONDS = 5_000L
        const val TTL_10_SECONDS = 10_000L
        const val TTL_15_SECONDS = 15_000L
        const val TTL_30_SECONDS = 30_000L
        const val TTL_60_SECONDS = 60_000L
        const val TTL_5_MINUTES = 5 * 60_000L
    }

}