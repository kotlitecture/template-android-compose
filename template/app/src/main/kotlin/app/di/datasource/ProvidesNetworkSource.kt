package app.di.datasource

import android.app.Application
import app.datasource.network.AppNetworkSource
import core.data.datasource.network.NetworkSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesNetworkSource {

    @Provides
    @Singleton
    fun sourceWrapped(app: Application): AppNetworkSource {
        return AppNetworkSource(app)
    }

    @Provides
    @Singleton
    fun source(wrapped: AppNetworkSource): NetworkSource {
        return wrapped
    }

}