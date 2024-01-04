package core.ui.app.dialog

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import core.ui.app.theme.AppTheme
import core.ui.block.RoundedIconBlock
import core.ui.block.Spacer16
import core.ui.block.Spacer24
import core.ui.block.Spacer8
import core.ui.block.TextBlock
import core.ui.misc.extension.withCornerRadius
import kotlinx.coroutines.delay

@Composable
fun DialogLayout(
    bgColor: Color = AppTheme.color.dialogPrimary,
    title: String? = null,
    message: String,
    icon: Any? = null,
    actions: (@Composable RowScope.() -> Unit)? = null
) {
    DialogLayout {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .wrapContentSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer24()
            val visible = remember { mutableStateOf(false) }
            if (icon != null) {
                val delay = AppTheme.anim.delayShort
                LaunchedEffect(message) {
                    delay(delay)
                    visible.value = true
                }
                Box(modifier = Modifier.size(72.dp)) {
                    androidx.compose.animation.AnimatedVisibility(
                        visible = visible.value,
                        enter = scaleIn(animationSpec = AppTheme.anim.springTaDam()) + fadeIn(),
                        exit = scaleOut() + fadeOut(),
                        content = {
                            RoundedIconBlock(
                                model = icon,
                                tint = AppTheme.color.textPrimary,
                                size = 72.dp
                            )
                        }
                    )
                }
                Spacer16()
            }
            if (title != null) {
                TextBlock(
                    text = title.trimIndent(),
                    color = AppTheme.color.textPrimary,
                    style = AppTheme.typography.titleLarge,
                    maxLines = Int.MAX_VALUE
                )
                Spacer8()
            }
            TextBlock(
                text = message.trimIndent(),
                color = AppTheme.color.textPrimary,
                style = AppTheme.typography.bodyLarge,
                maxLines = Int.MAX_VALUE
            )
            Spacer(modifier = Modifier.height(80.dp))
        }
        if (actions != null) {
            Row(
                modifier = Modifier
                    .background(bgColor.copy(alpha = 0.8f))
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                actions(this)
            }
        }
    }
}

@Composable
fun DialogLayout(
    modifier: Modifier = Modifier,
    heightRatio: Float = 0.55f,
    bgColor: Color = AppTheme.color.dialogPrimary,
    content: @Composable BoxScope.() -> Unit
) {
    AppTheme {
        val height = LocalConfiguration.current.screenHeightDp.times(heightRatio).dp
        Box(
            modifier = modifier
                .padding(horizontal = 24.dp)
                .withCornerRadius(28.dp)
                .background(bgColor)
                .heightIn(max = height)
                .fillMaxWidth()
        ) {
            content(this)
        }
    }
}

@Composable
fun ConfirmDialogLayout(
    title: String? = null,
    message: String,
    confirmTitleRes: Int,
    confirmAction: () -> Unit,
    cancelTitleRes: Int,
    cancelAction: () -> Unit,
) {
    DialogLayout(
        title = title,
        message = message
    ) {
        DialogButton(text = stringResource(cancelTitleRes), onClick = cancelAction)
        ErrorDialogButton(text = stringResource(confirmTitleRes), onClick = confirmAction)
    }
}

@Composable
fun DialogButton(
    text: String,
    color: Color = AppTheme.color.textPrimary,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick
    ) {
        Text(
            text = text,
            color = color,
            style = AppTheme.typography.labelLarge
        )
    }
}

@Composable
fun ErrorDialogButton(
    text: String,
    onClick: () -> Unit
) {
    DialogButton(
        text = text,
        onClick = onClick,
        color = AppTheme.color.negative
    )
}

fun NavGraphBuilder.dialogLayout(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    dialog(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        dialogProperties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        content = content
    )
}