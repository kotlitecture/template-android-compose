package di

import app.datasource.config.AppConfigSource
import core.datasource.analytics.IAnalyticsSource
import core.datasource.config.IConfigSource
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
    fun source(
        analyticsSource: IAnalyticsSource
    ): IConfigSource {
        return AppConfigSource(analyticsSource)
    }

}