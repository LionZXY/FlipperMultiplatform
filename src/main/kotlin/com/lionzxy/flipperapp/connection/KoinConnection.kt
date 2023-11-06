package com.lionzxy.flipperapp.connection

import com.flipperdevices.bridge.service.api.FlipperServiceApi
import com.flipperdevices.bridge.service.api.provider.FlipperServiceProvider
import com.flipperdevices.bridge.service.api.provider.FlipperServiceProviderImpl
import org.koin.dsl.module

fun koinConnection() = module {
    single<FlipperServiceApi> { USBSerialConnectionHolder() }
    single<FlipperServiceProvider> { FlipperServiceProviderImpl(get()) }
}