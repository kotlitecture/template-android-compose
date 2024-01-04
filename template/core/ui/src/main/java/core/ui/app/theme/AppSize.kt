package core.ui.app.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppSize(
    val cornerLg: Dp = 24.dp,
    val cornerMd: Dp = 16.dp,
    val cornerSm: Dp = 8.dp,
    val cornerXs: Dp = 4.dp,

    val navigationBarScrollOffset:Int = 460,
    val navigationBarHeight:Dp = 120.dp,
    val navigationBarSize:Dp = 64.dp,
    val actionButtonSize: Dp = 40.dp,
    val actionPanelSize: Dp = 32.dp,
    val buttonSizeMd: Dp = 56.dp,

    val paddingMd: Dp = 16.dp,
    val paddingSm: Dp = 8.dp,
    val paddingXs: Dp = 4.dp,

    val iconXxs: Dp = 8.dp,
    val iconXs: Dp = 12.dp,
    val iconSm: Dp = 16.dp,
    val iconMd: Dp = 24.dp,
    val iconLg: Dp = 40.dp,
    val iconXl: Dp = 48.dp,
    val iconXxl: Dp = 56.dp,

    val shadowMd: Dp = 16.dp,

    val toolbarHeight: Dp = 56.dp
)
