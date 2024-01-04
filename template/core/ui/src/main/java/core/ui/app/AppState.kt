package core.ui.app

import android.app.Application
import core.data.state.StoreObject
import core.data.state.StoreState
import core.essentials.exception.DataException
import core.essentials.misc.extensions.isIgnoredException
import core.ui.R
import core.ui.app.command.Command
import core.ui.app.command.CopyTextCommand
import core.ui.app.command.NavigateCommand
import core.ui.app.command.OpenUrlCommand
import core.ui.app.command.ShowSnackbarCommand
import core.ui.app.command.ShowToastCommand
import core.ui.app.dialog.confirm.ConfirmState
import core.ui.app.dialog.error.ErrorDialogDestination
import core.ui.app.dialog.hint.HintDialogDestination
import core.ui.app.loading.LoadingState
import core.ui.app.navigation.Destination
import org.tinylog.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppState @Inject constructor(
    private val app: Application
) : StoreState() {

    interface IErrorHandler {
        fun handle(th: Throwable): Boolean = false
    }

    interface ICommandHandler {
        fun handle(command: Command) = Unit
    }

    enum class NavigationState {
        HIDDEN,
        SHOWN
    }

    val readyStore = StoreObject(false)
    val intentStore = StoreObject<AppIntent>()
    val loadingStore = StoreObject(false)
    val confirmStore = StoreObject<ConfirmState>()
    val destinationStore = StoreObject<Destination<*>>()
    val navigationStore = StoreObject(NavigationState.SHOWN)

    var errorInterceptor: IErrorHandler = object : IErrorHandler {}
    var commandHandler: ICommandHandler = object : ICommandHandler {}

    fun onShowHideNavigation(show: Boolean) {
        if (show) {
            navigationStore.set(NavigationState.SHOWN)
        } else {
            navigationStore.set(NavigationState.HIDDEN)
        }
    }

    fun onConfirm(state: ConfirmState?) {
        confirmStore.set(state)
    }

    fun onLoading(state: LoadingState) {
        when (state) {
            is LoadingState.Loading -> {
                loadingStore.set(true)
            }

            is LoadingState.Error -> {
                onError(state.th)
                loadingStore.set(false)
            }

            else -> {
                loadingStore.set(false)
            }
        }
    }

    fun onBack() {
        onCommand(NavigateCommand<Nothing>())
    }

    fun <D> onNavigate(destination: Destination<D>, data: D? = null) {
        onCommand(NavigateCommand(destination, data))
    }

    fun onOpenUrl(url: String) {
        onCommand(OpenUrlCommand(url))
    }

    fun onShowToast(text: String) {
        onCommand(ShowToastCommand(text))
    }

    fun onShowSnackbar(text: String) {
        onCommand(ShowSnackbarCommand(text))
    }

    fun onCopyText(text: String, toast: String) {
        onCommand(CopyTextCommand(text, toast))
    }

    fun onShowHint(title: String, message: String, icon: Any? = null) {
        val data = HintDialogDestination.Data(
            icon = icon,
            title = title,
            message = message,
            actionLabel = app.getString(R.string.button_got_it)
        )
        onNavigate(HintDialogDestination(), data)
    }

    fun onCommand(cmd: Command) = commandHandler.handle(cmd)

    fun onError(th: Throwable) {
        if (errorInterceptor.handle(th)) return
        if (!th.isIgnoredException()) {
            Logger.error(th, "onShowError")
            val message = when (th) {
                is DataException -> th.msg()
                else -> null
            } ?: th.message ?: th.stackTraceToString()
            onError(message = message)
        }
    }

    private fun onError(title: String? = null, message: String, actionLabel: String? = null) {
        Logger.debug("onShowError :: message={}", message)
        val data = ErrorDialogDestination.Data(
            title = title,
            message = message,
            actionLabel = actionLabel
        )
        onNavigate(ErrorDialogDestination(), data)
    }

}