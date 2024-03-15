package app.di

import core.data.datasource.encryption.BasicEncryptionSource
import core.data.datasource.encryption.EncryptionSource
import core.data.datasource.storage.keyvalue.EncryptedKeyValueSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesEncryptionSource {

    @Provides
    @Singleton
    fun source(keyValueSource: EncryptedKeyValueSource): EncryptionSource {
        return BasicEncryptionSource(keyValueSource)
    }

}