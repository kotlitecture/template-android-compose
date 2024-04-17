package app.userflow.passcode.repository

import app.userflow.passcode.PasscodeState
import app.userflow.passcode.data.PasscodeData
import app.userflow.passcode.data.PasscodeLockData
import app.userflow.passcode.data.PasscodeUnlockData
import core.data.datasource.biometric.BiometricSource
import core.data.datasource.keyvalue.EncryptedKeyValueSource
import core.data.datasource.keyvalue.KeyValueSource
import core.data.serialization.JsonStrategy
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max

/**
 * Repository class for managing passcode-related operations.
 */
@Singleton
class PasscodeRepository @Inject constructor(
    private val encryptedKeyValueSource: EncryptedKeyValueSource,
    private val biometricSource: BiometricSource,
    private val keyValueSource: KeyValueSource,
    private val passcodeState: PasscodeState,
) {

    private val dataKey = "${passcodeState.persistentKey}_data"
    private val lockDataKey = "${passcodeState.persistentKey}_lock_data"
    private val unlockDataKey = "${passcodeState.persistentKey}_unlock_data"
    private val dataStrategy = JsonStrategy.create(PasscodeData.serializer())
    private val lockDataStrategy = JsonStrategy.create(PasscodeLockData.serializer())
    private val unlockDataStrategy = JsonStrategy.create(PasscodeUnlockData.serializer())

    /**
     * Unlocks the passcode.
     */
    suspend fun unlock() {
        passcodeState.lockedStore.set(false)
    }

    /**
     * Checks if the passcode is locked.
     */
    suspend fun isLocked(): Boolean {
        return passcodeState.lockedStore.getNotNull() && isEnabled()
    }

    /**
     * Checks if the passcode is enabled.
     */
    suspend fun isEnabled(): Boolean {
        return !getData()?.code.isNullOrBlank()
    }

    /**
     * Checks if biometric authentication is available.
     */
    suspend fun isBiometricAvailable(): Boolean {
        return biometricSource.isAvailable()
    }

    /**
     * Checks if biometric authentication is enabled.
     */
    suspend fun isBiometricEnabled(): Boolean {
        return getData()?.biometric == true
    }

    /**
     * Enables the passcode with the specified code.
     */
    suspend fun enablePasscode(code: String) {
        val data = getData()?.copy(code = code) ?: PasscodeData(code = code, biometric = false)
        encryptedKeyValueSource.save(dataKey, data, dataStrategy)
    }

    /**
     * Disables the passcode.
     */
    suspend fun disablePasscode() {
        encryptedKeyValueSource.remove(dataKey, dataStrategy)
    }

    /**
     * Enables or disables biometric authentication.
     */
    suspend fun enableBiometric(enable: Boolean) {
        val data = getData()?.copy(biometric = enable) ?: return
        encryptedKeyValueSource.save(dataKey, data, dataStrategy)
    }

    /**
     * Resets the passcode and related data.
     */
    suspend fun reset() {
        encryptedKeyValueSource.remove(dataKey, dataStrategy)
        keyValueSource.remove(lockDataKey, lockDataStrategy)
        keyValueSource.remove(unlockDataKey, unlockDataStrategy)
        passcodeState.lockedStore.set(true)
    }

    /**
     * Gets the number of unlock attempts.
     */
    suspend fun getUnlockAttempts(): Int {
        return getUnlockData()?.attempts ?: 0
    }

    /**
     * Gets the remaining unlock attempts.
     */
    suspend fun getRemainingAttempts(): Int {
        return max(0, passcodeState.unlockAttemptsCount - getUnlockAttempts())
    }

    /**
     * Checks if the passcode can be locked.
     */
    suspend fun canLock(foreground: Boolean): Boolean {
        if (!isEnabled()) {
            return false
        }
        if (!foreground) {
            val lockData = PasscodeLockData(System.currentTimeMillis())
            keyValueSource.save(lockDataKey, lockData, lockDataStrategy)
        } else {
            val pauseTime = keyValueSource.remove(lockDataKey, lockDataStrategy)?.pauseTime
            val resumeTimeout = passcodeState.resumeTimeout
            val currentTime = System.currentTimeMillis()
            val needLock = pauseTime == null || currentTime - pauseTime >= resumeTimeout
            return needLock
        }
        return false
    }

    /**
     * Checks if the passcode can be unlocked with the specified code.
     */
    suspend fun canUnlock(code: String): Boolean {
        val expectedCode = getData()?.code ?: return false
        if (code != expectedCode) {
            val remainingAttempts = getRemainingAttempts()
            val attempts = getUnlockAttempts() + 1
            if (remainingAttempts <= 1) {
                reset()
            } else {
                keyValueSource.save(unlockDataKey, PasscodeUnlockData(attempts), unlockDataStrategy)
            }
            return false
        } else {
            keyValueSource.remove(unlockDataKey, unlockDataStrategy)
            return true
        }
    }

    private suspend fun getData(): PasscodeData? =
        encryptedKeyValueSource.read(dataKey, dataStrategy)

    private suspend fun getUnlockData(): PasscodeUnlockData? =
        keyValueSource.read(unlockDataKey, unlockDataStrategy)

}