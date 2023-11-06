package com.flipperdevices.core.ui.ktx.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.KamelImageBox
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@Composable
fun FlipperAsyncImage(
    url: String,
    contentDescription: String?,
    onLoading: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    filterQuality: FilterQuality = FilterQuality.None,
    contentScale: ContentScale = ContentScale.FillBounds,
    colorFilter: ColorFilter? = null,
    cacheKey: String? = url,
    onError: (() -> Unit)? = null,
    forceFormat: DataSourceFormat? = null,
) {
    val painterResource: Resource<Painter> = flipperAsyncPainterResource(
        url,
        filterQuality = filterQuality,
        dataSourceFormat = forceFormat
    ) {
        // CoroutineContext to be used while loading the image.
        coroutineContext = Job() + Dispatchers.IO

    }
    val onSuccess: @Composable (BoxScope.(Painter) -> Unit) = { painter ->
        onLoading(false)
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            colorFilter = colorFilter,
        )
    }
    KamelImageBox(
        resource = painterResource,
        modifier = modifier,
        contentAlignment = Alignment.Center,
        onLoading = { onLoading(true) },
        onFailure = {
            it.printStackTrace()
            onLoading(false)
            onError?.invoke()
        },
        onSuccess = {
            onLoading(false)
            onSuccess(it)
        },
    )
}

