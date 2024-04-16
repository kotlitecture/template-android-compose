package app.userflow.passcode

import app.userflow.passcode.ui.unlock.UnlockPasscodeHandler
import core.ui.state.StoreState
import kotlin.time.Duration.Companion.seconds

data class PasscodeState(
    val persistentKey: String = "passcode_config",
    val passcodeLength: Int = 4,
    val canForgetPasscode: Boolean = true,
    val unlockAttemptsCount: Int = 5,
    val resumeTimeout: Long = 5.seconds.inWholeMilliseconds,
    val unlockHandler: UnlockPasscodeHandler
) : StoreState()