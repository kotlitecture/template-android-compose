package app.di

import android.app.Application
import core.data.datasource.biometric.AndroidBiometricSource
import core.data.datasource.biometric.BiometricSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesBiometricSource {

    @Provides
    @Singleton
    fun source(app: Application): BiometricSource {
        return AndroidBiometricSource(app)
    }

}