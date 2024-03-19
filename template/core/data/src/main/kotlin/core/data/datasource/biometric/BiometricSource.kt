package core.data.datasource.biometric

import core.data.datasource.DataSource

/**
 * Provides an interface for accessing biometric data on the device.
 */
interface BiometricSource : DataSource {

    /**
     * Checks if biometric authentication is available on the device.
     *
     * @return true if biometric authentication is available, false otherwise.
     */
    suspend fun isAvailable(): Boolean

}