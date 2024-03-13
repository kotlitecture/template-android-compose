package di

import app.datasource.analytics.AppAnalyticsSource
import core.data.datasource.analytics.AnalyticsSource
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
    fun source(): AnalyticsSource {
        return AppAnalyticsSource()
    }

}