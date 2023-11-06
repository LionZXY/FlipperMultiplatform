package com.flipperdevices.core.ui.errors.impl.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flipperdevices.core.ui.theme.FlipperThemeInternal
import com.flipperdevices.faphub.errors.api.FapErrorSize

@Composable
internal fun ComposableWrongRequestError(
    fapErrorSize: FapErrorSize,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    ComposableBaseError(
        modifier = modifier,
        title = "Server Request Error",
        description = "Update app to the latest version",
        iconPath = "drawable/ic_update_app.xml",
        onRetry = onRetry,
        fapErrorSize = fapErrorSize
    )
}

@Preview(
)
@Composable
private fun ComposableWrongRequestErrorPreview() {
    FlipperThemeInternal {
        ComposableWrongRequestError(onRetry = {}, fapErrorSize = FapErrorSize.FULLSCREEN)
    }
}
