package core.ui.command

import core.ui.state.DataState
import core.ui.AppContext
import kotlinx.coroutines.launch

abstract class Command(val uid: Long = System.currentTimeMillis()) {

    fun execute(commandState: CommandState, appContext: AppContext) {
        appContext.scope.launch {
            try {
                doExecute(commandState, appContext)
            } catch (e: Exception) {
                commandState.dataStateStore.set(DataState.Error(uid.toString(), e))
            }
        }
    }

    protected abstract fun doExecute(commandState: CommandState, appContext: AppContext)

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