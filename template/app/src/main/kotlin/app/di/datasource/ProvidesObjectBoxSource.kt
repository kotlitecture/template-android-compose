package app.di.datasource

import android.app.Application
import app.datasource.database.objectbox.AppObjectBoxSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesObjectBoxSource {

    @Provides
    @Singleton
    fun source(app: Application): AppObjectBoxSource {
        return AppObjectBoxSource(app)
    }

}