package app.di.state

import app.userflow.passcode.PasscodeState
import app.userflow.passcode.ui.unlock.UnlockPasscodeHandler
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
        return PasscodeState(
            unlockHandler = object : UnlockPasscodeHandler {
                override suspend fun onUnlock() {

                }

                override suspend fun onForgot() {

                }

                override suspend fun onReset() {

                }
            }
        )
    }

}