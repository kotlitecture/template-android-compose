package app.di

import android.app.Application
import core.data.datasource.notifications.BasicNotificationsSource
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
    fun source(
        app: Application
    ): NotificationsSource {
        return BasicNotificationsSource(app)
    }

}