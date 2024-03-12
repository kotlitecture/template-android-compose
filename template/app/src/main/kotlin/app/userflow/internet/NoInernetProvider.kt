package app.userflow.internet

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
import core.ui.provideViewModel

@Composable
fun NoInternetProvider(
    textRes: Int = R.string.internet_error,
    textColor: Color = Color.White,
    backgroundColor: Color = Color.Red.copy(alpha = 0.92f)
) {
    val viewModel: NoInternetViewModel = provideViewModel(activityScope = true)
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