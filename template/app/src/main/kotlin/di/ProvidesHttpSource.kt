package di

import app.datasource.config.AppConfigSource
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
internal class ProvidesHttpSource {

    @Provides
    @Singleton
    fun source(
        @Named(HttpSource.INTERCEPTORS) interceptors: Set<@JvmSuppressWildcards Interceptor>,
        config: AppConfigSource
    ): HttpSource {
        return HttpSource(
            timeout = config.getApiTimeout(),
            retries = config.getApiRetryCount(),
            interceptors = interceptors.toList()
        )
    }

    @Named(HttpSource.INTERCEPTORS)
    @Singleton
    @Provides
    @IntoSet
    fun emptyInterceptor(): Interceptor = Interceptor { it.proceed(it.request()) }

}