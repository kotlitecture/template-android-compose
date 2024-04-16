package app.userflow.passcode.repository

import app.userflow.passcode.PasscodeState
import app.userflow.passcode.data.PasscodeData
import app.userflow.passcode.data.PasscodeLockData
import app.userflow.passcode.ui.unlock.UnlockPasscodeDestination
import core.data.datasource.biometric.BiometricSource
import core.data.datasource.keyvalue.EncryptedKeyValueSource
import core.data.datasource.keyvalue.KeyValueSource
import core.data.serialization.JsonStrategy
import core.ui.navigation.NavigationState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PasscodeRepository @Inject constructor(
    private val encryptedKeyValueSource: EncryptedKeyValueSource,
    private val navigationState: NavigationState,
    private val biometricSource: BiometricSource,
    private val keyValueSource: KeyValueSource,
    private val passcodeState: PasscodeState,
) {

    private val dataKey = "${passcodeState.persistentKey}_data"
    private val lockDataKey = "${passcodeState.persistentKey}_lock_data"
    private val dataStrategy = JsonStrategy.create(PasscodeData.serializer())
    private val lockDataStrategy = JsonStrategy.create(PasscodeLockData.serializer())

    suspend fun getCode(): String? {
        return getData()?.code
    }

    suspend fun isEnabled(): Boolean {
        return !getCode().isNullOrBlank()
    }

    suspend fun isBiometricAvailable(): Boolean {
        return biometricSource.isAvailable()
    }

    suspend fun isBiometricEnabled(): Boolean {
        return getData()?.biometric == true
    }

    suspend fun enablePasscode(code: String) {
        val data = getData()?.copy(code = code) ?: PasscodeData(code = code, biometric = false)
        encryptedKeyValueSource.save(dataKey, data, dataStrategy)
    }

    suspend fun enableBiometric(enable: Boolean) {
        val data = getData()?.copy(biometric = enable) ?: return
        encryptedKeyValueSource.save(dataKey, data, dataStrategy)
    }

    suspend fun reset() {
        encryptedKeyValueSource.remove(dataKey, dataStrategy)
        keyValueSource.remove(lockDataKey, lockDataStrategy)
    }

    suspend fun tryLock(foreground: Boolean) {
        if (!isEnabled()) {
            return
        }
        if (!foreground) {
            val lockData = PasscodeLockData(System.currentTimeMillis())
            keyValueSource.save(lockDataKey, lockData, lockDataStrategy)
        } else {
            val pauseTime = keyValueSource.remove(lockDataKey, lockDataStrategy)?.pauseTime
            val resumeTimeout = passcodeState.resumeTimeout
            val currentTime = System.currentTimeMillis()
            val needLock = pauseTime == null || currentTime - pauseTime >= resumeTimeout
            if (needLock) {
                val currentDestinationStore = navigationState.currentDestinationStore
                if (currentDestinationStore.get() !is UnlockPasscodeDestination) {
                    navigationState.onNext(
                        UnlockPasscodeDestination,
                        UnlockPasscodeDestination.Data(
                            back = currentDestinationStore.isNotNull()
                        )
                    )
                }
            }
        }
    }

    private suspend fun getData(): PasscodeData? =
        encryptedKeyValueSource.read(dataKey, dataStrategy)

}