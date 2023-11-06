package com.flipperdevices.bridge.dao

import com.flipperdevices.bridge.dao.api.FapHubHideItemApi
import com.flipperdevices.bridge.dao.impl.FapHubHideItemApiImpl
import org.koin.dsl.module

fun hideKoin() = module {
    single<FapHubHideItemApi> { FapHubHideItemApiImpl() }
}