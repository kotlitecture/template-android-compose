package core.ui.app.dialog.date

import core.data.state.StoreObject
import core.data.state.StoreState
import core.ui.app.AppState
import core.ui.misc.utils.FormatUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.tinylog.Logger
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateDialogState @Inject constructor(
    private val appState: AppState
) : StoreState() {

    private val confirmRequest = StoreObject<Date>()
    private var job: Job? = null

    val selectedTypeStore = StoreObject(DateType.From)
    val fromStore = StoreObject<Date>()
    val toStore = StoreObject<Date>()

    internal fun getSelectedDateStore() = when (selectedTypeStore.getNotNull()) {
        DateType.From -> fromStore
        DateType.To -> toStore
    }

    internal fun onConfirm() {
        confirmRequest.set(Date())
    }

    internal fun onClose() {
        reset()
        appState.onBack()
    }

    fun onSelectRange(
        scope: CoroutineScope,
        from: Date? = null,
        to: Date? = null,
        type: DateType = DateType.From,
        onSelectRange: (from: Date?, to: Date?) -> Unit
    ) {
        reset()
        job = scope.launch {
            confirmRequest.clear()
            selectedTypeStore.set(type)
            fromStore.set(from)
            toStore.set(to)
            appState.onNavigate(DateDialogDestination())
            confirmRequest.asFlow()
                .filterNotNull()
                .take(1)
                .collectLatest {
                    val fromValue = fromStore.get()
                    val toValue = toStore.get()
                    Logger.debug("onSelectRange :: {} - {}", fromValue, toValue)
                    onSelectRange(fromValue, toValue)
                    onClose()
                }
        }
    }

    fun formatDate(date: Date?): String {
        return date?.let { FormatUtils.formatDateMedium(it) } ?: FormatUtils.DASH
    }

    private fun reset() {
        job?.cancel()
    }

}