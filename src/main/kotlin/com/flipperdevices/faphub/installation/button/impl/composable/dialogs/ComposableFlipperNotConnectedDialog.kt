package com.flipperdevices.faphub.installation.button.impl.composable.dialogs

import androidx.compose.runtime.Composable
import com.flipperdevices.core.ui.dialog.composable.FlipperDialog

@Composable
fun ComposableFlipperNotConnectedDialog(
    onDismiss: () -> Unit,
    onOpenDeviceTab: () -> Unit
) {
    FlipperDialog(
        titleText = "Flipper is Not Connected",
        text = "Connect your Flipper Zero to install this app",
        buttonText = "Go to Connecting",
        onClickButton = {
            onDismiss()
            onOpenDeviceTab()
        },
        imagePath = "drawable/ic_flipper_upload_failed.xml",
        onDismissRequest = onDismiss
    )
}
