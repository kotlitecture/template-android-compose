package core.data.datasource.network

import core.data.datasource.DataSource
import kotlinx.coroutines.flow.Flow

/**
 * Interface for accessing network-related information.
 */
interface NetworkSource : DataSource {

    /**
     * Retrieves a flow representing the online status of the device.
     *
     * @return A flow emitting Boolean values indicating the online status.
     */
    fun isOnline(): Flow<Boolean>

    /**
     * Retrieves the IP address of the device.
     *
     * @return The IP address of the device, or null if unavailable.
     */
    suspend fun getIp(): String?

}