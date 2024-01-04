package di

import app.datasource.analytics.AppAnalyticsSource
import core.datasource.analytics.IAnalyticsSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesAnalyticsSource {

    @Provides
    @Singleton
    fun source(): IAnalyticsSource {
        return AppAnalyticsSource()
    }

}