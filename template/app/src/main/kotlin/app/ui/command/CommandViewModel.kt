package app.ui.command

import core.ui.AppContext
import core.ui.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

/**
 * ViewModel responsible for handling commands.
 */
@HiltViewModel
class CommandViewModel @Inject constructor(
    private val commandState: CommandState
) : AppViewModel() {

    fun onBind(context: AppContext) {
        launchAsync("commandState") {
            commandState.commandStore.asFlow()
                .filterNotNull()
                .collectLatest { command ->
                    command.execute(commandState, context)
                }
        }
    }

}