package com.flipperdevices.faphub.target

import com.flipperdevices.faphub.target.api.FlipperTargetProviderApi
import org.koin.dsl.module

fun flipperTargetKoin() = module {
    single<FlipperTargetProviderApi> { FlipperTargetProviderApiNoop() }
}