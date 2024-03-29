package app.ui.command

import core.ui.AppContext
import core.ui.AppViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull

/**
 * ViewModel responsible for handling commands.
 */
class CommandViewModel : AppViewModel() {

    fun onBind(commandState: CommandState, context: AppContext) {
        launchAsync("commandState") {
            commandState.commandStore.asFlow()
                .filterNotNull()
                .collectLatest { command ->
                    command.execute(commandState, context)
                }
        }
    }

}