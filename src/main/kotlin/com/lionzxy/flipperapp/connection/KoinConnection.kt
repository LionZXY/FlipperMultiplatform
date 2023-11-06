package com.lionzxy.flipperapp.connection

import com.flipperdevices.bridge.service.api.FlipperServiceApi
import org.koin.dsl.module

fun koinConnection() = module {
    single<FlipperServiceApi> { USBSerialConnectionHolder() }
}