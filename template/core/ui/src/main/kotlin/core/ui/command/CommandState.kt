package core.ui.command

import core.ui.state.StoreObject
import core.ui.state.StoreState

/**
 * Represents the state of command execution within the application.
 * It manages the storage of commands to be executed.
 */
class CommandState : StoreState() {

    /**
     * Store for holding commands to be executed.
     * Commands are stored without replay and with maximum buffer capacity.
     */
    val commandStore = StoreObject<Command>(valueReply = 0, valueBufferCapacity = Int.MAX_VALUE)

    /**
     * Sets a command to be executed.
     * @param cmd The command to be executed.
     */
    fun onCommand(cmd: Command) = commandStore.set(cmd)

    companion object {
        val Default by lazy { CommandState() }
    }

}