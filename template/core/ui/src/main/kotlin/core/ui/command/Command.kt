package core.ui.command

import core.ui.AppContext
import core.ui.state.DataState
import kotlinx.coroutines.launch

/**
 * Abstract class representing a command involving UI.
 *
 * Commands encapsulate logic to be executed within the application.
 * Each command has a unique identifier.
 * To execute a command, call the execute method passing the command state and application context.
 * Subclasses must implement the doExecute method to define the specific behavior of the command.
 */
abstract class Command(val uid: Long = System.currentTimeMillis()) {

    /**
     * Executes the command.
     *
     * @param commandState The command state to be modified by the command.
     * @param appContext The application context providing necessary resources for command execution.
     */
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