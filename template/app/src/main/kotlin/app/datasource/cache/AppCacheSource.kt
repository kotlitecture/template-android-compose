package app.datasource.cache

import core.data.datasource.cache.BasicCacheSource

/**
 * Decorator class for working with L1 Cache.
 *
 * Can provide extra methods without impacting facade implementations.
 */
class AppCacheSource : BasicCacheSource()