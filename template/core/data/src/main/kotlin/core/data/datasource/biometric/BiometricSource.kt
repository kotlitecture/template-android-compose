package core.data.datasource.biometric

import core.data.datasource.DataSource

interface BiometricSource : DataSource {

    suspend fun isAvailable(): Boolean

}