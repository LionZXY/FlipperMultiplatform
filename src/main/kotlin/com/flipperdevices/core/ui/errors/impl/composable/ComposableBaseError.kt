package com.flipperdevices.core.ui.errors.impl.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.flipperdevices.core.ui.theme.LocalPallet
import com.flipperdevices.core.ui.theme.LocalTypography
import com.flipperdevices.faphub.errors.api.FapErrorSize

@Composable
internal fun ComposableBaseError(
    title: String,
    description: String,
    fapErrorSize: FapErrorSize,
    iconPath: String,
    modifier: Modifier = Modifier,
    darkIconPath: String = iconPath,
    onRetry: (() -> Unit)? = null
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    Image(
        painter = painterResource(
            if (MaterialTheme.colors.isLight) {
                iconPath
            } else {
                darkIconPath
            }
        ),
        contentDescription = title
    )

    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = title,
        style = LocalTypography.current.bodyM14.copy(
            fontSize = fapErrorSize.textSize
        ),
        color = LocalPallet.current.text100,
        textAlign = TextAlign.Center
    )
    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = description,
        style = LocalTypography.current.bodyR14.copy(
            fontSize = fapErrorSize.textSize
        ),
        color = LocalPallet.current.text40,
        textAlign = TextAlign.Center
    )

    if (onRetry != null) {
        Text(
            modifier = Modifier
                .padding(top = 12.dp)
                .clickable(onClick = onRetry),
            text = "Retry",
            style = LocalTypography.current.bodyM14,
            color = LocalPallet.current.accentSecond,
            textAlign = TextAlign.Center
        )
    }
}
