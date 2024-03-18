package app.di

import android.app.Application
import core.data.datasource.storage.keyvalue.BasicKeyValueSource
import core.data.datasource.storage.keyvalue.KeyValueSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesKeyValueSource {

    @Provides
    @Singleton
    fun source(app: Application): KeyValueSource {
        return BasicKeyValueSource(app)
    }

}