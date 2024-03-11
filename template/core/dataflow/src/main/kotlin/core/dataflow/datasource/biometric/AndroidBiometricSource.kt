package core.dataflow.datasource.biometric

import android.annotation.SuppressLint
import android.app.Application
import androidx.biometric.BiometricManager

class AndroidBiometricSource(private val app: Application) : BiometricSource {

    @SuppressLint("ObsoleteSdkInt")
    override suspend fun isAvailable(): Boolean {
        val biometricManager = BiometricManager.from(app)
        val types = BiometricManager.Authenticators.BIOMETRIC_STRONG or
            BiometricManager.Authenticators.BIOMETRIC_WEAK
        return biometricManager.canAuthenticate(types) == BiometricManager.BIOMETRIC_SUCCESS
    }

}