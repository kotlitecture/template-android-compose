package app.di.datasource

import app.datasource.cache.AppCacheSource
import core.data.datasource.cache.CacheSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesCacheSource {

    @Provides
    @Singleton
    fun sourceWrapped(): AppCacheSource {
        return AppCacheSource()
    }

    @Provides
    @Singleton
    fun source(wrapped: AppCacheSource): CacheSource {
        return wrapped
    }

}