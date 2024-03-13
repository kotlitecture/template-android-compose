package app.di

import android.app.Application
import app.datasource.config.AppConfigSource
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import core.data.datasource.http.HttpSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesHttpSource {

    @Provides
    @Singleton
    fun source(
        app: Application,
        config: AppConfigSource
    ): HttpSource {
        return HttpSource(
            timeout = config.getApiTimeout(),
            retries = config.getApiRetryCount(),
            interceptors = listOf(
                ChuckerInterceptor.Builder(app)
                    .maxContentLength(1000)
                    .collector(
                        ChuckerCollector(
                            app,
                            showNotification = false,
                            retentionPeriod = RetentionManager.Period.ONE_HOUR
                        )
                    )
                    .build()
            )
        )
    }

}