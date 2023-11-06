package com.flipperdevices.core.ui.errors.impl.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flipperdevices.core.ui.theme.FlipperThemeInternal
import com.flipperdevices.faphub.errors.api.FapErrorSize

@Composable
internal fun ComposableNoNetworkError(
    fapErrorSize: FapErrorSize,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    ComposableBaseError(
        modifier = modifier,
        title = "No Internet Connection",
        description = "Turn on mobile data or Wi-Fi",
        iconPath = "drawable/ic_no_internet.xml",
        darkIconPath = "drawable/ic_no_internet_dark.xml",
        onRetry = onRetry,
        fapErrorSize = fapErrorSize
    )
}

@Preview()
@Composable
private fun ComposableNoNetworkErrorPreview() {
    FlipperThemeInternal {
        ComposableNoNetworkError(onRetry = {}, fapErrorSize = FapErrorSize.FULLSCREEN)
    }
}
