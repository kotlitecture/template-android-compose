package app.userflow.passcode.ui.unlock

interface UnlockPasscodeHandler {

    suspend fun onUnlock() = Unit

    suspend fun onForgot() = Unit

    suspend fun onReset() = Unit

    suspend fun onWrongAttempt(attemptNumber: Int, attemptCode: String) = Unit

}