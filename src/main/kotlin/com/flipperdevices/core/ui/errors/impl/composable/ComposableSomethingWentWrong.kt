package com.flipperdevices.core.ui.errors.impl.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flipperdevices.core.ui.theme.FlipperThemeInternal
import com.flipperdevices.faphub.errors.api.FapErrorSize

@Composable
internal fun ComposableGeneralError(
    fapErrorSize: FapErrorSize,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    ComposableBaseError(
        modifier = modifier,
        title = "Something Went Wrong",
        description = "¯\\_(ツ)_/¯\nTry again later",
        iconPath = "drawable/ic_general_error.xml",
        onRetry = onRetry,
        fapErrorSize = fapErrorSize
    )
}

@Preview(
)
@Composable
private fun ComposableGeneralErrorPreview() {
    FlipperThemeInternal {
        ComposableGeneralError(onRetry = {}, fapErrorSize = FapErrorSize.FULLSCREEN)
    }
}
