package app.di.state

import app.userflow.passcode.PasscodeState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ProvidesPasscodeState {

    @Provides
    @Singleton
    fun state(): PasscodeState {
        return PasscodeState()
    }

}