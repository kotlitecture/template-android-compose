package app.di

import android.app.Application
import core.data.datasource.clipboard.BasicClipboardSource
import core.data.datasource.clipboard.ClipboardSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesClipboardSource {

    @Provides
    @Singleton
    fun source(app: Application): ClipboardSource {
        return BasicClipboardSource(app)
    }

}