package core.ui.app.dialog.date

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.ui.R
import core.ui.app.dialog.DialogButton
import core.ui.app.dialog.DialogLayout
import core.ui.app.theme.AppTheme
import core.ui.block.Spacer8
import core.ui.mvvm.provideViewModel
import org.tinylog.Logger
import java.util.*

@Stable
@Immutable
data class DateDialogAppearance(
    val buttonsHeight: Dp,
    val bgColor: Color,
    val captionColor: Color,
    val selectionColor: Color,
    val selectionTextColor: Color
) {

    fun getTypeColor(selected: Boolean) = if (selected) selectionColor else Color.Unspecified

    companion object {
        @Composable
        fun default(
            buttonsHeight: Dp = 64.dp,
            bgColor: Color = AppTheme.color.dialogPrimary,
            captionColor: Color = AppTheme.color.textPrimary,
            selectionColor: Color = AppTheme.materialColor.primary,
            selectionTextColor: Color = AppTheme.materialColor.onPrimary
        ): DateDialogAppearance = DateDialogAppearance(
            buttonsHeight = buttonsHeight,
            bgColor = bgColor,
            captionColor = captionColor,
            selectionColor = selectionColor,
            selectionTextColor = selectionTextColor
        )
    }
}

@Composable
fun DateRangeDialogScreen() {
    val viewModel: DateDialogViewModel = provideViewModel()
    DateRangeDialogLayout(
        onCancel = viewModel::onCancel,
        onConfirm = viewModel::onConfirm,
        selectedType = viewModel.getSelectedType(),
        getSelectedDate = viewModel::getSelectedDate,
        onSelectType = viewModel::onSelectType,
        onSelectDate = viewModel::onSelectDate,
        getDateFromLabel = { viewModel.getDateFromLabel() },
        getDateToLabel = { viewModel.getDateToLabel() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateRangeDialogLayout(
    appearance: DateDialogAppearance = DateDialogAppearance.default(),
    selectedType: DateType = DateType.From,
    getSelectedDate: () -> Date? = { null },
    getDateFromLabel: @Composable () -> String = { "" },
    getDateToLabel: @Composable () -> String = { "" },
    onCancel: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onSelectType: (type: DateType) -> Unit = {},
    onSelectDate: (date: Date?) -> Unit = {}
) {
    Logger.debug("recompose :: {}", "Date")
    DialogLayout(
        heightRatio = 1f,
        bgColor = appearance.bgColor
    ) {
        val state = rememberDatePickerState()
        LaunchedEffect(selectedType) {
            Logger.debug("selected date set :: {}", getSelectedDate())
            state.selectedDateMillis = getSelectedDate()?.time
        }
        LaunchedEffect(state.selectedDateMillis) {
            Logger.debug("selected date changed :: {}", state.selectedDateMillis)
            onSelectDate(state.selectedDateMillis?.let { Date(it) })
        }
        DatePicker(
            modifier = Modifier.padding(bottom = appearance.buttonsHeight),
            state = state,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = appearance.bgColor,
                selectedDayContainerColor = appearance.selectionColor,
                selectedYearContainerColor = appearance.selectionColor,
                selectedDayContentColor = appearance.selectionTextColor,
                selectedYearContentColor = appearance.selectionTextColor
            ),
            title = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.date_dialog_title),
                        color = appearance.captionColor
                    )
                    Spacer8()
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = appearance.getTypeColor(selectedType == DateType.From)
                            ),
                            onClick = { onSelectType(DateType.From) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = getDateFromLabel())
                        }

                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = stringResource(R.string.preposition_to).lowercase(),
                            color = appearance.captionColor
                        )

                        OutlinedButton(
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = appearance.getTypeColor(selectedType == DateType.To)
                            ),
                            onClick = { onSelectType(DateType.To) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = getDateToLabel())
                        }
                    }
                }
            },
            headline = {}
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 8.dp)
                .height(appearance.buttonsHeight),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            DialogButton(
                text = stringResource(R.string.button_cancel),
                onClick = onCancel
            )
            DialogButton(
                text = stringResource(R.string.button_ok),
                onClick = onConfirm
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDateDialogLayout() {
    AppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            DateRangeDialogLayout()
        }
    }
}