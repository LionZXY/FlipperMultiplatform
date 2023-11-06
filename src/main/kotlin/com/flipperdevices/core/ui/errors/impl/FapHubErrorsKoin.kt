package com.flipperdevices.core.ui.errors.impl

import com.flipperdevices.core.ui.errors.impl.api.FapHubComposableErrorsRendererImpl
import com.flipperdevices.faphub.errors.api.FapHubComposableErrorsRenderer
import org.koin.dsl.module

fun fapHubKoin() = module {
    single<FapHubComposableErrorsRenderer> { FapHubComposableErrorsRendererImpl() }
}