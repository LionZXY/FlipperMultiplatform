package com.flipperdevices.core.ui.errors.impl.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flipperdevices.core.ui.theme.FlipperThemeInternal
import com.flipperdevices.faphub.errors.api.FapErrorSize

@Composable
internal fun ComposableFlipperNotConnectedError(
    fapErrorSize: FapErrorSize,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    ComposableBaseError(
        modifier = modifier,
        title = "Flipper Not Connected",
        description = "Connect your Flipper to see the installed apps",
        iconPath = "drawable/ic_flipper_not_connected.xml",
        onRetry = onRetry,
        fapErrorSize = fapErrorSize
    )
}

@Preview()
@Composable
private fun ComposableFlipperNotConnectedErrorPreview() {
    FlipperThemeInternal {
        ComposableFlipperNotConnectedError(onRetry = {}, fapErrorSize = FapErrorSize.FULLSCREEN)
    }
}
