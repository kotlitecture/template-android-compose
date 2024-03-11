package core.dataflow.datasource.network

import core.dataflow.datasource.DataSource
import kotlinx.coroutines.flow.Flow

interface NetworkSource : DataSource {

    suspend fun isOnline(): Flow<Boolean>

    suspend fun getIp(): String?

}