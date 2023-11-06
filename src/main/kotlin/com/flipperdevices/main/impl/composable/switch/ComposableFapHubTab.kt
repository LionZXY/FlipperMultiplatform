package com.flipperdevices.main.impl.composable.switch

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.flipperdevices.core.ui.ktx.clickableRipple
import com.flipperdevices.core.ui.theme.FlipperThemeInternal
import com.flipperdevices.core.ui.theme.LocalPallet
import com.flipperdevices.core.ui.theme.LocalTypography
import com.flipperdevices.main.impl.model.FapHubTabEnum

@Composable
fun ComposableFapHubTab(
    hubTabEnum: FapHubTabEnum,
    onSelectFapHubTabEnum: (FapHubTabEnum) -> Unit,
    notificationCount: Int,
    modifier: Modifier = Modifier
) {
    val iconId = when (hubTabEnum) {
        FapHubTabEnum.APPS -> "drawable/ic_apps.xml"
        FapHubTabEnum.INSTALLED -> "drawable/ic_installed.xml"
    }
    val text = when (hubTabEnum) {
        FapHubTabEnum.APPS -> "All Apps"
        FapHubTabEnum.INSTALLED -> "Installed"
    }

    ComposableFapHubTabInternal(
        modifier = modifier
            .clickableRipple { onSelectFapHubTabEnum(hubTabEnum) },
        iconId = iconId,
        title = text,
        notificationCount = notificationCount
    )
}

@Composable
private fun ComposableFapHubTabInternal(
    iconId: String,
    title: String,
    notificationCount: Int,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
) {
    Box(
        contentAlignment = Alignment.TopEnd
    ) {
        Icon(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp, start = 12.dp, end = 8.dp)
                .size(18.dp),
            painter = painterResource(iconId),
            contentDescription = title,
            tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        )
        if (notificationCount > 0) {
            ComposableIndicationDot(notificationCount)
        }
    }
    Text(
        modifier = Modifier.padding(
            top = 8.dp,
            bottom = 8.dp,
            end = 12.dp
        ),
        text = title,
        color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
        style = LocalTypography.current.subtitleM12
    )
}

@Composable
private fun ComposableIndicationDot(
    notificationCount: Int
) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(CircleShape)
            .background(LocalPallet.current.fapHubIndicationColor)
            .padding(1.dp)
            .clip(CircleShape)
            .background(LocalPallet.current.updateProgressGreen),
        contentAlignment = Alignment.Center
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = notificationCount.toString(),
            color = LocalPallet.current.fapHubIndicationColor,
            style = LocalTypography.current.notificationB8
        )
    }
}

@Preview
@Composable
private fun ComposableFapHubTabInternalPreview() {
    FlipperThemeInternal {
        Row {
            ComposableFapHubTabInternal(
                "drawable/ic_apps.xml",
                "All Apps",
                notificationCount = 0
            )
            ComposableFapHubTabInternal(
                "drawable/ic_installed.xml",
                "Installed",
                notificationCount = 4
            )
        }
    }
}
