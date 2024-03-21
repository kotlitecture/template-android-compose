package app.userflow.internet.no

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.R
import app.provideHiltViewModel

/**
 * Composable function responsible for displaying UI elements indicating no internet connection.
 * Utilizes the NoInternetViewModel to observe and react to changes in internet connectivity status.
 *
 * @param textRes The resource ID of the text to be displayed indicating no internet connection.
 * @param textColor The color of the text indicating no internet connection.
 * @param backgroundColor The background color of the UI element indicating no internet connection.
 */
@Composable
fun NoInternetProvider(
    textRes: Int = R.string.internet_error,
    textColor: Color = Color.White,
    backgroundColor: Color = Color.Red.copy(alpha = 0.92f)
) {
    val viewModel: NoInternetViewModel = provideHiltViewModel(activityScoped = true)
    AnimatedVisibility(
        visible = !viewModel.isOnlineStore.asStateValueNotNull(),
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(textRes),
                color = textColor
            )
        }
    }
}