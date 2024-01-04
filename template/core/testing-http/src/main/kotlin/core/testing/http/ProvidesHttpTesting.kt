package core.testing.http

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import core.essentials.http.HttpSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesHttpTesting {

    @Named(HttpSource.INTERCEPTORS)
    @Singleton
    @Provides
    @IntoSet
    fun chuckerInterceptor(
        app: Application
    ): Interceptor {
        val collector = ChuckerCollector(
            app,
            showNotification = false,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        return ChuckerInterceptor.Builder(app)
            .maxContentLength(1000)
            .collector(collector)
            .build()
    }

}