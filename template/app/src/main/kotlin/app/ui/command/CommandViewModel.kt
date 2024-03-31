package app.ui.command

import core.ui.navigation.NavigationContext
import core.ui.BaseViewModel
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
) : BaseViewModel() {

    fun onBind(context: NavigationContext) {
        launchAsync("commandState") {
            commandState.commandStore.asFlow()
                .filterNotNull()
                .collectLatest { command ->
                    command.execute(commandState, context)
                }
        }
    }

}