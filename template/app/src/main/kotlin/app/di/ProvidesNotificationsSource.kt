package app.di

import android.app.Application
import app.datasource.notifications.AppNotificationsSource
import core.data.datasource.notifications.NotificationsSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesNotificationsSource {

    @Provides
    @Singleton
    fun sourceWrapped(app: Application): AppNotificationsSource {
        return AppNotificationsSource(app)
    }

    @Provides
    @Singleton
    fun source(wrapped: AppNotificationsSource): NotificationsSource {
        return wrapped
    }

}