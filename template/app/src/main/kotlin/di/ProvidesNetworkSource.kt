package di

import android.app.Application
import core.datasource.network.INetworkSource
import core.datasource.network.NetworkSource
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
    fun source(
        app: Application
    ): INetworkSource {
        return NetworkSource(app)
    }

}