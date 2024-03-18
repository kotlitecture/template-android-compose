package core.data.datasource.biometric

import android.annotation.SuppressLint
import android.app.Application
import androidx.biometric.BiometricManager

class BasicBiometricSource(private val app: Application) : BiometricSource {

    private val biometricManager by lazy { BiometricManager.from(app) }

    @SuppressLint("ObsoleteSdkInt")
    override suspend fun isAvailable(): Boolean {
        val types = BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.BIOMETRIC_WEAK
        return biometricManager.canAuthenticate(types) == BiometricManager.BIOMETRIC_SUCCESS
    }

}