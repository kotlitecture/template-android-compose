package app.userflow.passcode.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.ui.theme.ThemeData

@Composable
fun PasscodeBlock(
    modifier: Modifier = Modifier,
    codeState: State<String?>,
    title: String? = null,
    errorState: State<String?>? = null,
    codeLength: Int = 4
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (title != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W600,
                fontSize = 24.sp,
                text = title
            )
        }
        PasscodeDots(
            modifier = Modifier.fillMaxWidth(),
            codeLength = codeLength,
            code = codeState
        )
        Box(modifier = Modifier.heightIn(min = 24.dp)) {
            if (errorState != null) {
                ErrorBlock(errorState)
            }
        }
    }
}

@Composable
private fun ErrorBlock(errorState: State<String?>) {
    val error = errorState.value ?: return
    Text(
        text = error,
        maxLines = 1,
        textAlign = TextAlign.Center,
        color = ThemeData.current.negative
    )
}

@Composable
private fun PasscodeDots(
    modifier: Modifier = Modifier,
    code: State<String?>,
    codeLength: Int,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
    ) {
        val value = code.value.orEmpty()
        repeat(codeLength) { idx -> PasscodeDot(idx <= value.length - 1) }
    }
}

@Composable
private fun PasscodeDot(filled: Boolean) {
    val size = 16.dp
    val color = ThemeData.current.onPrimary
    if (filled) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(color)
        )
    } else {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .border(2.dp, color, CircleShape)
        )
    }
}