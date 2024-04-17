package app.userflow.passcode.data

import kotlinx.serialization.Serializable

/**
 * Serializable data class representing passcode unlock data.
 *
 * @property attempts The number of attempts made to unlock the passcode.
 */
@Serializable
data class PasscodeUnlockData(
    val attempts: Int
)