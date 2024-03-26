package app.ui.container.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.ui.component.Spacer16
import app.ui.component.SpacerNavigationBar
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties
import com.holix.android.bottomsheetdialog.compose.NavigationBarProperties

/**
 * Data class representing the appearance properties of a bottom sheet.
 *
 * @property backgroundColor Background color of the bottom sheet.
 * @property dragHandlerColor Color of the drag handler.
 * @property fullscreen Flag indicating whether the bottom sheet should be displayed in fullscreen mode.
 * @property cornerRadius Corner radius of the bottom sheet.
 */
data class BottomSheetAppearance(
    val backgroundColor: Color = Color.Unspecified,
    val dragHandlerColor: Color = Color.Gray,
    val fullscreen: Boolean = false,
    val cornerRadius: Dp = 16.dp
) {
    companion object {
        @Composable
        fun default(
            backgroundColor: Color = Color.Unspecified,
            dragHandlerColor: Color = Color.Gray,
            fullscreen: Boolean = false,
            cornerRadius: Dp = 16.dp
        ): BottomSheetAppearance {
            return remember(backgroundColor, dragHandlerColor, fullscreen, cornerRadius) {
                BottomSheetAppearance(
                    dragHandlerColor = dragHandlerColor,
                    backgroundColor = backgroundColor,
                    cornerRadius = cornerRadius,
                    fullscreen = fullscreen,
                )
            }
        }
    }
}

/**
 * Composable function to display a bottom sheet layout.
 *
 * @param modifier The modifier for this composable.
 * @param appearance The appearance properties of the bottom sheet.
 * @param onDismissRequest Callback for dismissing the bottom sheet.
 * @param content The content of the bottom sheet.
 */
@Composable
fun BottomSheetLayout(
    modifier: Modifier = Modifier,
    appearance: BottomSheetAppearance = BottomSheetAppearance.default(),
    onDismissRequest: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    val state = if (appearance.fullscreen) BottomSheetBehaviorProperties.State.Expanded else BottomSheetBehaviorProperties.State.Collapsed
    BottomSheetDialog(
        onDismissRequest = onDismissRequest,
        properties = BottomSheetDialogProperties(
            enableEdgeToEdge = true,
            dismissOnBackPress = true,
            behaviorProperties = BottomSheetBehaviorProperties(
                isFitToContents = !appearance.fullscreen,
                skipCollapsed = appearance.fullscreen,
                state = state,
            ),
            navigationBarProperties = NavigationBarProperties(
                darkIcons = true,
                navigationBarContrastEnforced = false
            )
        ),
        content = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = appearance.cornerRadius,
                            topEnd = appearance.cornerRadius
                        )
                    )
                    .background(appearance.backgroundColor)
                    .imePadding()
            ) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.CenterHorizontally)
                        .height(4.dp)
                        .width(40.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(appearance.dragHandlerColor),
                )
                content()
                Spacer16()
                SpacerNavigationBar()
            }
        }
    )
}

