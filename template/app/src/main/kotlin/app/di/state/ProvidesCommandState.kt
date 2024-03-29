package app.di.state

import app.ui.command.CommandState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesCommandState {

    @Provides
    @Singleton
    fun state(): CommandState = CommandState()

}