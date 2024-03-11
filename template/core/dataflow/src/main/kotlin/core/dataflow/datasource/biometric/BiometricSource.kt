package core.dataflow.datasource.biometric

import core.dataflow.datasource.DataSource

interface BiometricSource : DataSource {

    suspend fun isAvailable(): Boolean

}