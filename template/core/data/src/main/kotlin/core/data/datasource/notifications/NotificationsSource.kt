package core.data.datasource.notifications

import core.data.datasource.DataSource

/**
 * Interface for managing notifications on the device.
 */
interface NotificationsSource : DataSource {

    /**
     * Enable all notifications.
     */
    fun enable()

    /**
     * Check if notifications are enabled.
     *
     * @return `true` if notifications are enabled, `false` otherwise.
     */
    fun areEnabled(): Boolean

    /**
     * Enable notifications for the specified channel.
     *
     * @param channelId The ID of the channel to enable notifications for.
     */
    fun enable(channelId: String)

    /**
     * Disable notifications for the specified channel.
     *
     * @param channelId The ID of the channel to disable notifications for.
     */
    fun disable(channelId: String)

    /**
     * Check if notifications are enabled for the specified channel.
     *
     * @param channelId The ID of the channel to check.
     * @return `true` if notifications are enabled for the specified channel, `false` otherwise.
     */
    fun isEnabled(channelId: String): Boolean

}