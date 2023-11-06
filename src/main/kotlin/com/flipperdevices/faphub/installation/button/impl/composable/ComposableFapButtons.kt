package com.flipperdevices.faphub.installation.button.impl.composable

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.flipperdevices.core.ui.theme.FlipperThemeInternal
import com.flipperdevices.core.ui.theme.LocalPallet
import com.flipperdevices.faphub.installation.button.api.FapButtonSize
import com.flipperdevices.faphub.installation.button.impl.composable.dialogs.ComposableFlipperNotConnectedDialog
import com.flipperdevices.faphub.installation.button.impl.composable.elements.ComposableFlipperButton
import com.flipperdevices.faphub.installation.button.impl.composable.elements.ComposableInProgressFapButton

@Composable
fun ComposableFapInstallButton(
    fapButtonSize: FapButtonSize,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ComposableFlipperButton(
        modifier = modifier,
        text = "INSTALL",
        color = LocalPallet.current.accent,
        fapButtonSize = fapButtonSize,
        onClick = onClick
    )
}

@Composable
fun ComposableFlipperNotConnectedButton(
    fapButtonSize: FapButtonSize,
    onOpenDeviceTab: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    ComposableFlipperButton(
        modifier = modifier,
        text = "INSTALL",
        color = LocalPallet.current.accent,
        fapButtonSize = fapButtonSize,
        onClick = {
            showDialog = true
        }
    )
    if (showDialog) {
        ComposableFlipperNotConnectedDialog(
            onDismiss = {
                showDialog = false
            },
            onOpenDeviceTab = onOpenDeviceTab
        )
    }
}

@Composable
fun ComposableFapNoInstallButton(
    fapButtonSize: FapButtonSize,
    modifier: Modifier = Modifier
) {
    ComposableFlipperButton(
        modifier = modifier,
        text = "INSTALL",
        color = LocalPallet.current.text20,
        fapButtonSize = fapButtonSize
    )
}

@Composable
fun ComposableFapInstalledButton(
    fapButtonSize: FapButtonSize,
    modifier: Modifier = Modifier
) {
    ComposableFlipperButton(
        modifier = modifier,
        text = "INSTALLED",
        color = LocalPallet.current.text20,
        fapButtonSize = fapButtonSize
    )
}

@Composable
fun ComposableFapUpdateButton(
    fapButtonSize: FapButtonSize,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ComposableFlipperButton(
        modifier = modifier,
        text = "UPDATE",
        color = LocalPallet.current.updateProgressGreen,
        fapButtonSize = fapButtonSize,
        onClick = onClick
    )
}

@Composable
fun ComposableFapInstallingButton(
    percent: Float,
    fapButtonSize: FapButtonSize,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    ComposableInProgressFapButton(
        fapButtonModifier = modifier,
        percent = percent,
        color = LocalPallet.current.accent,
        fapButtonSize = fapButtonSize,
        onCancel = onCancel
    )
}

@Composable
fun ComposableFapUpdatingButton(
    percent: Float,
    fapButtonSize: FapButtonSize,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    ComposableInProgressFapButton(
        fapButtonModifier = modifier,
        percent = percent,
        color = LocalPallet.current.updateProgressGreen,
        fapButtonSize = fapButtonSize,
        onCancel = onCancel
    )
}

@Composable
fun ComposableFapCancelingButton(
    fapButtonSize: FapButtonSize,
    modifier: Modifier = Modifier
) {
    ComposableFlipperButton(
        modifier = modifier,
        text = "Canceling",
        color = LocalPallet.current.updateProgressGreen,
        fapButtonSize = fapButtonSize
    )
}

@Preview
@Composable
private fun ComposableFapInstallButtonPreview() {
    FlipperThemeInternal {
        Column {
            ComposableFapInstallButton(fapButtonSize = FapButtonSize.LARGE, onClick = {})
            ComposableFapInstalledButton(fapButtonSize = FapButtonSize.LARGE)
            ComposableFapUpdateButton(fapButtonSize = FapButtonSize.LARGE, onClick = {})
            ComposableFapInstallingButton(
                percent = 0.5f,
                fapButtonSize = FapButtonSize.LARGE,
                onCancel = {}
            )
            ComposableFapUpdatingButton(
                percent = 0.5f,
                fapButtonSize = FapButtonSize.LARGE,
                onCancel = {}
            )
        }
    }
}
