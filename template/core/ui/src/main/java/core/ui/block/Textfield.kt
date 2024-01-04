package core.ui.block

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import core.data.state.StoreObject
import core.essentials.misc.extensions.asBigDecimal
import core.ui.app.theme.AppTheme
import core.ui.misc.extension.hapticTap
import core.ui.misc.extension.withRepeatableClick
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import java.math.BigDecimal

@Immutable
data class TextFieldBlockAppearance(
    val textColorFocused: Color,
    val textColorUnfocused: Color,
    val textColorDisabled: Color,
    val cornerRadius: Dp,
    val borderWidthFocused: Dp,
    val borderWidthUnfocused: Dp,
    val borderColorFocused: Color,
    val borderColorUnfocused: Color,
    val borderColorDisabled: Color
) {
    companion object {
        @Stable
        @Composable
        fun default(
            textColorFocused: Color = AppTheme.color.textPrimary,
            textColorUnfocused: Color = AppTheme.color.textSecondary,
            textColorDisabled: Color = AppTheme.color.textPrimary.copy(alpha = ContentAlpha.disabled),
            cornerRadius: Dp = AppTheme.size.cornerSm,
            borderWidthFocused: Dp = 2.dp,
            borderWidthUnfocused: Dp = 1.dp,
            borderColorFocused: Color = AppTheme.color.buttonPrimary,
            borderColorUnfocused: Color = AppTheme.color.buttonSecondary,
            borderColorDisabled: Color = AppTheme.color.buttonPrimary.copy(alpha = ContentAlpha.disabled),
        ) = TextFieldBlockAppearance(
            textColorFocused = textColorFocused,
            textColorUnfocused = textColorUnfocused,
            cornerRadius = cornerRadius,
            borderWidthFocused = borderWidthFocused,
            borderWidthUnfocused = borderWidthUnfocused,
            borderColorFocused = borderColorFocused,
            borderColorUnfocused = borderColorUnfocused,
            textColorDisabled = textColorDisabled,
            borderColorDisabled = borderColorDisabled
        )
    }
}

@Composable
@NonRestartableComposable
fun TextFieldBlock(
    modifier: Modifier = Modifier,
    appearance: TextFieldBlockAppearance = TextFieldBlockAppearance.default(),
    model: StoreObject<String>,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = {
        if (!model.asStateValue().isNullOrEmpty()) {
            ActionButtonBlock(
                icon = Icons.Filled.Cancel,
                color = appearance.textColorUnfocused,
                onClick = { model.set(null) }
            )
        }
    },
    textStyle: TextStyle = LocalTextStyle.current,
    maxLength: Int = Int.MAX_VALUE,
    placeholder: String? = null,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    label: String?,
) {
    val borderWidth = remember { mutableStateOf(appearance.borderWidthUnfocused) }
    val borderColor = remember { mutableStateOf(appearance.borderColorUnfocused) }
    val interactionSource = remember { MutableInteractionSource() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions
            .filterIsInstance<FocusInteraction>()
            .distinctUntilChanged()
            .collectLatest {
                if (it is FocusInteraction.Focus) {
                    borderWidth.value = appearance.borderWidthFocused
                    borderColor.value = appearance.borderColorFocused
                } else {
                    borderWidth.value = appearance.borderWidthUnfocused
                    borderColor.value = appearance.borderColorUnfocused
                }
                if (!enabled) {
                    borderColor.value = appearance.borderColorDisabled
                }
            }
    }
    Row(
        modifier = modifier
            .border(
                borderWidth.value,
                borderColor.value,
                RoundedCornerShape(appearance.cornerRadius)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (leadingIcon != null) {
            Spacer4()
            leadingIcon.invoke()
            Spacer4()
        }
        TextField(
            modifier = Modifier.weight(1f),
            singleLine = true,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            interactionSource = interactionSource,
            onValueChange = {
                if (it.length <= maxLength) {
                    model.set(it)
                }
            },
            value = model.asStateValue().orEmpty(),
            label = label?.let {
                {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = label,
                        textAlign = textStyle.textAlign
                    )
                }
            },
            placeholder = {
                if (placeholder != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = placeholder,
                        textAlign = textStyle.textAlign
                    )
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Unspecified,
                textColor = appearance.textColorFocused,
                disabledTextColor = appearance.textColorDisabled,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = appearance.textColorFocused,
                focusedLabelColor = appearance.borderColorFocused,
                unfocusedLabelColor = appearance.textColorUnfocused,
                placeholderColor = appearance.textColorUnfocused,
            ),
            keyboardOptions = keyboardOptions
        )
        if (trailingIcon != null) {
            Spacer4()
            trailingIcon.invoke()
            Spacer4()
        }
    }
}

@Composable
@NonRestartableComposable
fun NumberStepperBlock(
    modifier: Modifier = Modifier,
    model: StoreObject<String>,
    enabled: Boolean = true,
    min: BigDecimal? = BigDecimal.ZERO,
    step: BigDecimal = BigDecimal("0.001"),
    keyboardType: KeyboardType = KeyboardType.Decimal,
    values: List<StepperValue> = emptyList(),
    placeholder: String? = null,
    label: String? = null
) {
    val view = LocalView.current
    val appearance: TextFieldBlockAppearance = TextFieldBlockAppearance.default()
    val onApplyStep: (step: BigDecimal) -> Boolean = { s ->
        if (!enabled) {
            false
        } else {
            val prevNumber = model.get()?.toBigDecimalOrNull() ?: BigDecimal.ZERO
            val newNumber = (prevNumber + s).asStepperValue()
            if (min == null || newNumber >= min) {
                model.set(newNumber.toPlainString())
                true
            } else {
                model.set(min.toPlainString())
                false
            }
        }
    }
    Column(modifier = modifier) {
        TextFieldBlock(
            modifier = Modifier.fillMaxWidth(),
            model = model,
            label = label,
            enabled = enabled,
            appearance = appearance,
            placeholder = placeholder,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            leadingIcon = {
                ActionButtonBlock(
                    haptic = true,
                    icon = Icons.Filled.Remove,
                    color = appearance.textColorUnfocused,
                    modifier = Modifier
                        .wrapContentWidth()
                        .withRepeatableClick {
                            onApplyStep(step.unaryMinus())
                        }
                )
            },
            trailingIcon = {
                ActionButtonBlock(
                    haptic = true,
                    icon = Icons.Filled.Add,
                    color = appearance.textColorUnfocused,
                    modifier = Modifier
                        .wrapContentWidth()
                        .withRepeatableClick {
                            onApplyStep(step)
                        }
                )
            },
        )
        if (values.isNotEmpty()) {
            Spacer8()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val currentValue = model.asStateValue()
                values.forEach { value ->
                    val buttonAppearance =
                        if (currentValue == value.value) {
                            ButtonAppearance.primaryXs()
                        } else {
                            ButtonAppearance.secondaryXs()
                        }
                    ButtonBlock(
                        modifier = Modifier.weight(1f),
                        appearance = buttonAppearance,
                        text = value.label,
                        enabled = enabled,
                        onClick = {
                            model.set(value.value)
                            view.hapticTap()
                        }
                    )
                }
            }
        }
    }
}

@Immutable
data class StepperValue(
    val value: String,
    val label: String = value
)

@Preview
@Composable
private fun PreviewTextFieldBlock() {
    AppTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            NumberStepperBlock(
                modifier = Modifier.fillMaxWidth(),
                model = StoreObject(),
                label = "Price in USD",
                values = listOf(
                    StepperValue(
                        value = "0.02"
                    ),
                    StepperValue(
                        value = "0.06"
                    ),
                    StepperValue(
                        value = "0.1"
                    ),
                    StepperValue(
                        value = "0.15"
                    )
                )
            )

            Spacer24()

            NumberStepperBlock(
                modifier = Modifier.fillMaxWidth(),
                model = StoreObject(),
                label = "Duration in minutes",
                keyboardType = KeyboardType.Number,
                step = BigDecimal("5"),
                min = BigDecimal("15"),
                values = listOf(
                    StepperValue(
                        label = "15m",
                        value = "15"
                    ),
                    StepperValue(
                        label = "30m",
                        value = "30"
                    ),
                    StepperValue(
                        label = "1h",
                        value = "60"
                    ),
                    StepperValue(
                        label = "6h",
                        value = "360"
                    ),
                    StepperValue(
                        label = "1d",
                        value = "1440"
                    ),
                    StepperValue(
                        label = "3d",
                        value = "4320"
                    )
                )
            )
        }
    }
}

fun BigDecimal.asStepperValue(): BigDecimal = this.asBigDecimal(7, true)