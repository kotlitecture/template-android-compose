package core.ui.app.command

import core.data.state.StoreObject
import core.ui.app.AppContext
import org.tinylog.Logger

abstract class Command(val uid: Long = System.currentTimeMillis()) {

    fun execute(appContext: AppContext) {
        StoreObject.onMain {
            try {
                doExecute(appContext)
            } catch (e: Exception) {
                Logger.error(e, "Command error")
            }
        }
    }

    protected abstract fun doExecute(appContext: AppContext)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Command

        if (uid != other.uid) return false

        return true
    }

    override fun hashCode(): Int {
        return uid.hashCode()
    }

}