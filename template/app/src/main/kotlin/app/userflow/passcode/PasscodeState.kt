package app.userflow.passcode

import core.ui.state.StoreObject
import core.ui.state.StoreState
import kotlin.time.Duration.Companion.seconds

/**
 * Represents the state related to passcode configuration and management.
 *
 * @param persistentKey The key used for persistent storage.
 * @param passcodeLength The length of the passcode.
 * @param unlockAttemptsCount The maximum number of unlock attempts allowed.
 * @param canForgetPasscode Indicates whether users can forget their passcode.
 * @param resumeTimeout The timeout for resuming the passcode lock in milliseconds.
 */
data class PasscodeState(
    val persistentKey: String = "passcode_config",
    val passcodeLength: Int = 4,
    val unlockAttemptsCount: Int = 5,
    val canForgetPasscode: Boolean = true,
    val resumeTimeout: Long = 1.seconds.inWholeMilliseconds
) : StoreState() {

    /** Represents the store object indicating whether the passcode is locked or not. */
    val lockedStore = StoreObject(true)

}