package app.di

import app.datasource.config.AppConfigSource
import core.data.datasource.config.ConfigSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesConfigSource {

    @Provides
    @Singleton
    fun source(): ConfigSource {
        return AppConfigSource()
    }

}