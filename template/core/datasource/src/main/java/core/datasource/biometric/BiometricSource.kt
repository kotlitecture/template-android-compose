package core.datasource.biometric

import android.annotation.SuppressLint
import android.app.Application
import androidx.biometric.BiometricManager

class BiometricSource(private val app: Application) : IBiometricSource {

    @SuppressLint("ObsoleteSdkInt")
    override suspend fun isAvailable(): Boolean {
        val biometricManager = BiometricManager.from(app)
        val types = BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.BIOMETRIC_WEAK
        return biometricManager.canAuthenticate(types) == BiometricManager.BIOMETRIC_SUCCESS
    }

}