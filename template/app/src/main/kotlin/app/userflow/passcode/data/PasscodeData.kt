package app.userflow.passcode.data

import kotlinx.serialization.Serializable

@Serializable
data class PasscodeData(
    val code: String,
    val biometric: Boolean
)