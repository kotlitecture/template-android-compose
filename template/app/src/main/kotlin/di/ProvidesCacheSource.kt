package di

import core.essentials.cache.ICacheSource
import core.essentials.cache.impl.MemoryCacheSource
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
    fun source(): ICacheSource {
        return MemoryCacheSource()
    }

}