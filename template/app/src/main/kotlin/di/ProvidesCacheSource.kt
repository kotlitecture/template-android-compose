package di

import core.data.datasource.cache.CacheSource
import core.data.datasource.cache.MemoryCacheSource
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
    fun source(): CacheSource {
        return MemoryCacheSource()
    }

}