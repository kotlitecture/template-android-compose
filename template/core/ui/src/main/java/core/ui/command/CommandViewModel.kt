package core.ui.command

import core.ui.AppContext
import core.ui.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

@HiltViewModel
class CommandViewModel @Inject constructor() : AppViewModel() {

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