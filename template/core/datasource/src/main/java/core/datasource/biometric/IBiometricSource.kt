package core.datasource.biometric

import core.datasource.IDataSource

interface IBiometricSource : IDataSource {

    suspend fun isAvailable(): Boolean

}