package core.ui.layout

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.ui.block.ImageBlock
import core.ui.block.Spacer24
import core.ui.block.Spacer4
import core.ui.misc.extension.withPaddingHorizontal16

@Composable
fun OnboardingPageLayout(
    titleRes: Int,
    subtitleRes: Int,
    vector: ImageVector,
    actionLabelRes: Int,
    action: () -> Unit,
    onBack: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null
) {
    FixedTopBarLayout(
        onBack = onBack,
        onClose = onClose,
        modifier = Modifier.withPaddingHorizontal16(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OnboardingPageLayout(
            titleRes = titleRes,
            subtitleRes = subtitleRes,
            actionLabelRes = actionLabelRes,
            vector = vector,
            action = action
        )
    }
}

@Composable
fun OnboardingPageLayout(
    titleRes: Int,
    subtitleRes: Int,
    vector: ImageVector,
    actionLabelRes: Int,
    action: () -> Unit
) {
    Text(text = stringResource(titleRes))
    Spacer4()
    Text(
        modifier = Modifier.withPaddingHorizontal16(),
        text = stringResource(subtitleRes),
        textAlign = TextAlign.Center
    )
    Spacer24()
    ImageBlock(
        modifier = Modifier
            .fillMaxWidth()
            .height(208.dp),
        contentScale = ContentScale.FillHeight,
        model = vector
    )
    Spacer24()
    FilledTonalButton(onClick = action) {
        Text(text = stringResource(actionLabelRes))
    }
}