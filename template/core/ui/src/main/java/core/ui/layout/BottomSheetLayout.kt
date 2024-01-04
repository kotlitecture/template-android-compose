package core.ui.layout

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.holix.android.bottomsheetdialog.compose.NavigationBarProperties
import core.ui.app.theme.AppTheme
import core.ui.block.Spacer24
import core.ui.block.SpacerNavigationBar
import core.ui.misc.extension.withCornerRadius

@Composable
fun BottomSheetLayout(
    blocked: Boolean = false,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    BottomSheetDialog(
        onDismissRequest = { if (!blocked) onDismissRequest() },
        properties = BottomSheetDialogProperties(
            enableEdgeToEdge = true,
            dismissOnBackPress = !blocked,
            behaviorProperties = BottomSheetBehaviorProperties(
                isFitToContents = !blocked,
                isHideable = !blocked
            ),
            navigationBarProperties = NavigationBarProperties(
                darkIcons = !AppTheme.color.isDark,
                navigationBarContrastEnforced = false
            )
        ),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .let {
                        if (blocked) {
                            it.fillMaxHeight()
                        } else {
                            it
                        }
                    }
                    .clip(
                        RoundedCornerShape(
                            topStart = AppTheme.size.cornerMd,
                            topEnd = AppTheme.size.cornerMd
                        )
                    )
                    .background(AppTheme.color.surfaceBottomSheet)
                    .imePadding()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val blockedScale: Float
                    val blockedColor: Color
                    if (blocked) {
                        val blockedTransition = rememberInfiniteTransition(label = "")
                        blockedScale = blockedTransition.animateFloat(
                            label = "",
                            initialValue = 1f,
                            targetValue = 2.5f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(1000),
                                repeatMode = RepeatMode.Reverse
                            )
                        ).value
                        blockedColor = blockedTransition.animateColor(
                            label = "",
                            initialValue = AppTheme.color.textSecondary,
                            targetValue = AppTheme.color.negative,
                            animationSpec = infiniteRepeatable(
                                animation = tween(1000),
                                repeatMode = RepeatMode.Reverse
                            )
                        ).value
                    } else {
                        blockedScale = 1f
                        blockedColor = AppTheme.color.textSecondary
                    }
                    Box(
                        modifier = Modifier
                            .size(width = 32.dp, height = 4.dp)
                            .scale(scaleX = blockedScale, scaleY = 1f)
                            .withCornerRadius(4.dp)
                            .background(blockedColor)
                    )
                }
                content()
                Spacer24()
                SpacerNavigationBar()
            }
        }
    )
}