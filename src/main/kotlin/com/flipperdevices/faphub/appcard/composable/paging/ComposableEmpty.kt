package com.flipperdevices.faphub.appcard.composable.paging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.flipperdevices.core.ui.theme.LocalPallet
import com.flipperdevices.core.ui.theme.LocalTypography

@Composable
fun ComposableEmpty(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    Text(
        text = "No Apps Yet",
        style = LocalTypography.current.bodyM14,
        color = LocalPallet.current.text100,
        textAlign = TextAlign.Center
    )
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = "This category is empty or there are no apps for your Flipper firmware",
        style = LocalTypography.current.bodyM14,
        color = LocalPallet.current.text40,
        textAlign = TextAlign.Center
    )
}
