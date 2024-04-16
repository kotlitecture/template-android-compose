package app.userflow.passcode.data

import kotlinx.serialization.Serializable

@Serializable
data class PasscodeLockData(
    val pauseTime: Long = System.currentTimeMillis()
)