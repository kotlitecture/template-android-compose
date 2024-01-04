package core.datasource.work

interface IWorkConfig {

    fun getChannelName(): String

    fun getNotificationIcon(): Int

    fun getNotificationTitle(activeWorks: Int): String

    fun getNotificationText(activeWorks: Int): String?

    fun getQueueCleanerDestroySeconds(): Int = 10

    fun getQueueCleanerDelay(): Long = 15_000L

}