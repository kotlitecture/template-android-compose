package core.ui.app.dialog.date

import androidx.compose.runtime.Composable
import dagger.hilt.android.lifecycle.HiltViewModel
import core.ui.mvvm.BaseViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DateDialogViewModel @Inject constructor(
    private val state: DateDialogState
) : BaseViewModel() {

    @Composable
    fun getSelectedType(): DateType = state.selectedTypeStore.asStateValueNotNull()

    @Composable
    fun getDateFromLabel(): String {
        val dateFrom = state.fromStore.asStateValue()
        return state.formatDate(dateFrom)
    }

    @Composable
    fun getDateToLabel(): String {
        val dateTo = state.toStore.asStateValue()
        return state.formatDate(dateTo)
    }

    fun getSelectedDate(): Date? = state.getSelectedDateStore().get()

    fun onSelectType(type: DateType) {
        state.selectedTypeStore.set(type)
    }

    fun onSelectDate(date: Date?) {
        state.getSelectedDateStore().set(date)
    }

    fun onConfirm() {
        state.onConfirm()
    }

    fun onCancel() {
        state.onClose()
    }

}