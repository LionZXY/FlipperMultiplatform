package com.flipperdevices.core.ui.ktx

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.flipperdevices.core.ui.ktx.placeholder.PlaceholderHighlight
import com.flipperdevices.core.ui.ktx.placeholder.placeholder
import com.flipperdevices.core.ui.ktx.placeholder.shimmer
import com.flipperdevices.core.ui.theme.LocalPallet

fun Modifier.placeholderConnecting(shape: Int = 4) = composed {
    this.then(
        placeholder(
            visible = true,
            shape = RoundedCornerShape(shape.dp),
            color = LocalPallet.current.placeholder.copy(alpha = 0.2f),
            highlight = PlaceholderHighlight.shimmer(
                highlightColor = LocalPallet.current.placeholder
            )
        )
    )
}

fun Modifier.placeholderByLocalProvider(
    defaultWidth: Dp? = null,
    defaultHeight: Dp? = null,
    shape: Int = 4
) = composed {
    if (LocalPlaceholderEnable.current) {
        var modifier = this
        if (defaultWidth != null) {
            modifier = modifier.then(Modifier.width(defaultWidth))
        }
        if (defaultHeight != null) {
            modifier = modifier.then(Modifier.height(defaultHeight))
        }
        modifier.placeholderConnecting(shape)
    } else {
        this
    }
}

val LocalPlaceholderEnable = compositionLocalOf { false }
