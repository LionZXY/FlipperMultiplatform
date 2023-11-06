package com.flipperdevices.core.ui.ktx.image

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import io.kamel.core.*
import io.kamel.core.config.ResourceConfigBuilder
import io.kamel.image.PainterFailure
import io.kamel.image.config.LocalKamelConfig
import io.kamel.image.getDataSourceEnding

enum class DataSourceFormat {
    SVG,
    XML,
    BITMAP
}

@Composable
public inline fun <I : Any> flipperAsyncPainterResource(
    data: I,
    key: Any? = data,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
    dataSourceFormat: DataSourceFormat? = null,
    noinline onLoadingPainter: @Composable (Float) -> Result<Painter> = { Result.failure(PainterFailure()) },
    noinline onFailurePainter: @Composable (Throwable) -> Result<Painter> = { Result.failure(PainterFailure()) },
    crossinline block: ResourceConfigBuilder.() -> Unit = {},
): Resource<Painter> {

    val kamelConfig = LocalKamelConfig.current
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()
    val resourceConfig = remember(key, density) {
        ResourceConfigBuilder(scope.coroutineContext)
            .apply { this.density = density }
            .apply(block)
            .build()
    }
    val dataSourceFormatInternal = dataSourceFormat ?: getDataSourceFormat(data)


    val cachedResource = remember(key) {
        when (dataSourceFormatInternal) {
            DataSourceFormat.SVG -> kamelConfig.loadCachedResourceOrNull(data, kamelConfig.svgCache)
            DataSourceFormat.XML -> kamelConfig.loadCachedResourceOrNull(data, kamelConfig.imageVectorCache)
            DataSourceFormat.BITMAP -> kamelConfig.loadCachedResourceOrNull(data, kamelConfig.imageBitmapCache)
        }
    }

    val painterResource by remember(key, resourceConfig) {
        when (dataSourceFormatInternal) {
            DataSourceFormat.SVG -> kamelConfig.loadSvgResource(data, resourceConfig)
            DataSourceFormat.XML -> kamelConfig.loadImageVectorResource(data, resourceConfig)
            DataSourceFormat.BITMAP -> kamelConfig.loadImageBitmapResource(data, resourceConfig)
        }
    }.collectAsState(cachedResource ?: Resource.Loading(0F), resourceConfig.coroutineContext)

    val painterResourceWithFallbacks = when (painterResource) {
        is Resource.Loading -> {
            val resource = painterResource as Resource.Loading
            onLoadingPainter(resource.progress)
                .mapCatching { painter -> Resource.Success(painter) }
                .getOrDefault(painterResource)
        }

        is Resource.Success -> painterResource
        is Resource.Failure -> {
            val resource = painterResource as Resource.Failure
            onFailurePainter(resource.exception)
                .mapCatching { painter -> Resource.Success(painter) }
                .getOrDefault(painterResource)
        }
    }

    return painterResourceWithFallbacks.map { value ->
        when (value) {
            is ImageVector -> rememberVectorPainter(value)
            is ImageBitmap -> remember(value) {
                BitmapPainter(value, filterQuality = filterQuality)
            }

            else -> remember(value) { value as Painter }
        }
    }
}

fun <I : Any> getDataSourceFormat(data: I): DataSourceFormat {
    return when (getDataSourceEnding(data)) {
        "svg" -> DataSourceFormat.SVG
        "xml" -> DataSourceFormat.XML
        else -> DataSourceFormat.BITMAP
    }
}