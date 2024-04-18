package app.userflow.passcode.data

import kotlinx.serialization.Serializable

/**
 * Serializable data class representing passcode data.
 *
 * @property code The passcode.
 * @property biometric Indicates whether biometric authentication is enabled.
 */
@Serializable
data class PasscodeData(
    val code: String,
    val biometric: Boolean
)