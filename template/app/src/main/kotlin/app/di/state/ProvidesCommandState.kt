package app.di.state

import core.ui.command.CommandState
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
    fun command(): CommandState = CommandState.Default

}