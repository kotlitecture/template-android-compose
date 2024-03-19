package core.data.datasource.biometric

import android.annotation.SuppressLint
import android.app.Application
import androidx.biometric.BiometricManager

/**
 * Provides basic implementation for accessing biometric data on the device.
 */
open class BasicBiometricSource(protected val app: Application) : BiometricSource {

    protected val biometricManager by lazy { BiometricManager.from(app) }

    @SuppressLint("ObsoleteSdkInt")
    override suspend fun isAvailable(): Boolean {
        val types = BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.BIOMETRIC_WEAK
        return biometricManager.canAuthenticate(types) == BiometricManager.BIOMETRIC_SUCCESS
    }

}