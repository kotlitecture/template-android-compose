package app.di.datasource

import android.app.Application
import app.datasource.storage.keyvalue.AppEncryptedKeyValueSource
import core.data.datasource.storage.keyvalue.EncryptedKeyValueSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesEncryptedKeyValueSource {

    @Provides
    @Singleton
    fun sourceWrapped(app: Application): AppEncryptedKeyValueSource {
        return AppEncryptedKeyValueSource(app)
    }

    @Provides
    @Singleton
    fun source(wrapped: AppEncryptedKeyValueSource): EncryptedKeyValueSource {
        return wrapped
    }

}