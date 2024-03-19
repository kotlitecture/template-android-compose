package app.di

import android.app.Application
import app.datasource.biometric.AppBiometricSource
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
    fun sourceWrapped(app: Application): AppBiometricSource {
        return AppBiometricSource(app)
    }

    @Provides
    @Singleton
    fun source(wrapped: AppBiometricSource): BiometricSource {
        return wrapped
    }

}