package app.userflow.passcode.data

import kotlinx.serialization.Serializable

/**
 * Serializable data class representing passcode lock data.
 *
 * @property pauseTime The time when the passcode was paused.
 */
@Serializable
data class PasscodeLockData(
    val pauseTime: Long
)