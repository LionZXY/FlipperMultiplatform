package com.flipperdevices.core.ui.errors.impl.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flipperdevices.core.ui.theme.FlipperThemeInternal
import com.flipperdevices.faphub.errors.api.FapErrorSize

@Composable
internal fun ComposableNoServerError(
    fapErrorSize: FapErrorSize,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    ComposableBaseError(
        modifier = modifier,
        title = "No Server Connection",
        description = "Can't browse apps",
        iconPath = "drawable/ic_no_server.xml",
        onRetry = onRetry,
        fapErrorSize = fapErrorSize
    )
}

@Preview()
@Composable
private fun ComposableNoNetworkErrorPreview() {
    FlipperThemeInternal {
        ComposableNoServerError(onRetry = {}, fapErrorSize = FapErrorSize.FULLSCREEN)
    }
}
