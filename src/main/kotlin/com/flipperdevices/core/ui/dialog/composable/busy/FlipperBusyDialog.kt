package com.flipperdevices.core.ui.dialog.composable.busy

import androidx.compose.runtime.Composable
import com.flipperdevices.core.ui.dialog.composable.FlipperDialog

@Composable
fun ComposableFlipperBusy(
    onDismiss: () -> Unit,
    goToRemote: () -> Unit
) {
    FlipperDialog(
        imagePath = "drawable/pic_flipper_is_busy.xml",
        titleText = "Flipper is Busy",
        text = "Exit the current app on Flipper to use this feature",
        buttonText = "Go to Remote Control",
        onDismissRequest = onDismiss,
        onClickButton = {
            onDismiss()
            goToRemote()
        }
    )
}
