package core.ui.command

import core.ui.state.StoreObject
import core.ui.state.StoreState

class CommandState : StoreState() {

    val commandStore = StoreObject<Command>(valueReply = 0, valueBufferCapacity = Int.MAX_VALUE)

    fun onCommand(cmd: Command) = commandStore.set(cmd)

    companion object {
        val Default by lazy { CommandState() }
    }

}