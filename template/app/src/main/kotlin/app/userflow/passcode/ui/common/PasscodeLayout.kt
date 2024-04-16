package app.userflow.passcode.ui.common

import android.view.HapticFeedbackConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ui.component.basic.AnyIcon
import core.ui.theme.ThemeData

private val numberSize = 48.sp
private val actionSize = 72.dp
private val actionSpace = 24.dp

@Composable
fun PasscodeLayout(
    modifier: Modifier = Modifier,
    title: String? = null,
    errorState: State<String?>? = null,
    codeState: State<String?>,
    codeLength: Int,
    onCodeChange: (code: String) -> Unit,
    bottomLeftBlock: @Composable (size: Dp) -> Unit = {
        Box(modifier = Modifier.size(it))
    }
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(actionSpace, Alignment.CenterVertically)
    ) {
        PasscodeBlock(
            title = title,
            codeState = codeState,
            errorState = errorState,
            codeLength = codeLength
        )
        Row(horizontalArrangement = Arrangement.spacedBy(actionSpace)) {
            PadNumberButton(1, codeLength, codeState, onCodeChange)
            PadNumberButton(2, codeLength, codeState, onCodeChange)
            PadNumberButton(3, codeLength, codeState, onCodeChange)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(actionSpace)) {
            PadNumberButton(4, codeLength, codeState, onCodeChange)
            PadNumberButton(5, codeLength, codeState, onCodeChange)
            PadNumberButton(6, codeLength, codeState, onCodeChange)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(actionSpace)) {
            PadNumberButton(7, codeLength, codeState, onCodeChange)
            PadNumberButton(8, codeLength, codeState, onCodeChange)
            PadNumberButton(9, codeLength, codeState, onCodeChange)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(actionSpace)) {
            bottomLeftBlock(actionSize)
            PadNumberButton(0, codeLength, codeState, onCodeChange)
            EraseBlock(codeState, onCodeChange)
        }
    }
}

@Composable
private fun EraseBlock(codeState: State<String?>, onCodeChange: (code: String) -> Unit) {
    Box(modifier = Modifier.size(actionSize)) {
        AnimatedVisibility(
            modifier = Modifier.size(actionSize),
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
            visible = !codeState.value.isNullOrEmpty()
        ) {
            PadIconButton(
                iconRes = Icons.AutoMirrored.Outlined.Backspace,
                rippleColor = ThemeData.current.onPrimary,
                onClick = {
                    val code = codeState.value
                    if (!code.isNullOrEmpty()) {
                        val newCode = code.take(code.length - 1)
                        onCodeChange(newCode)
                    }
                }
            )
        }
    }
}

@Composable
fun PadTextButton(
    text: String,
    onClick: () -> Unit
) {
    PadButton(
        rippleColor = ThemeData.current.onPrimary,
        onClick = onClick
    ) {
        Text(
            text = text,
            maxLines = 1,
            fontSize = 16.sp,
            fontWeight = FontWeight.W500
        )
    }
}

@Composable
fun PadNumberButton(
    number: Int,
    codeLength: Int,
    codeState: State<String?>,
    onCodeChange: (code: String) -> Unit
) {
    PadButton(
        rippleColor = ThemeData.current.onPrimary,
        backgroundColor = ThemeData.current.primary,
        onClick = {
            val code = codeState.value.orEmpty()
            val enteredCode = code + number
            if (enteredCode.length <= codeLength) {
                onCodeChange(enteredCode)
            }
        }
    ) {
        Text(
            text = number.toString(),
            fontSize = numberSize
        )
    }
}

@Composable
fun PadIconButton(
    modifier: Modifier = Modifier,
    iconRes: Any,
    rippleColor: Color,
    onClick: () -> Unit
) {
    PadButton(
        rippleColor = rippleColor,
        onClick = onClick
    ) {
        AnyIcon(
            modifier = modifier.size(32.dp),
            model = iconRes
        )
    }
}

@Composable
fun PadButton(
    rippleColor: Color,
    backgroundColor: Color = Color.Unspecified,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .size(actionSize)
            .clip(RoundedCornerShape(percent = 50))
            .background(color = backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        val view = LocalView.current
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        onClick.invoke()
                    },
                    role = Role.Button,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false,
                        color = rippleColor
                    )
                )
        )
        content()
    }
}