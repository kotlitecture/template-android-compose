package core.datasource.di

import core.essentials.cache.ICacheSource
import core.essentials.cache.impl.MemoryCacheSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class Bindings {

    @Binds
    abstract fun bind(provider: MemoryCacheSource): ICacheSource

}