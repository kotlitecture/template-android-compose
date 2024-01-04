package core.ui.layout.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import core.ui.R
import core.ui.block.SmallTextSecondary

fun LazyListScope.noDataItem(color: Color = Color.Unspecified) {
    lazyItem(contentType = "noDataItem") {
        SmallTextSecondary(
            modifier = Modifier
                .fillMaxWidth()
                .background(color),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.app_list_no_data).uppercase()
        )
    }
}