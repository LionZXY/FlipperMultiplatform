package com.flipperdevices.faphub.target

import com.flipperdevices.faphub.target.api.FlipperTargetProviderApi
import com.flipperdevices.faphub.target.impl.api.FlipperTargetProviderApiImpl
import com.flipperdevices.faphub.target.impl.utils.FlipperSdkFetcher
import org.koin.dsl.module

fun flipperTargetKoin() = module {
    single<FlipperTargetProviderApi> {
        FlipperTargetProviderApiImpl(
            get(), get()
        )
    }
    single { FlipperSdkFetcher() }
}