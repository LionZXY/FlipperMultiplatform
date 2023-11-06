package com.flipperdevices.faphub.installation.button.impl.composable.buttons

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flipperdevices.core.ui.theme.LocalPallet
import com.flipperdevices.faphub.installation.button.api.FapButtonSize
import com.flipperdevices.faphub.installation.button.impl.composable.elements.ComposableFlipperButton

@Composable
internal fun ComposableFapOpenButton(
    fapButtonSize: FapButtonSize,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ComposableFlipperButton(
        modifier = modifier,
        text = "OPEN",
        color = LocalPallet.current.accentSecond,
        fapButtonSize = fapButtonSize,
        onClick = onClick
    )
}
