package com.flipperdevices.faphub.dao.network

import com.flipperdevices.faphub.dao.api.FapDownloadApi
import com.flipperdevices.faphub.dao.api.FapNetworkApi
import com.flipperdevices.faphub.dao.api.FapReportApi
import com.flipperdevices.faphub.dao.api.FapVersionApi
import com.flipperdevices.faphub.dao.network.api.FapDownloadApiImpl
import com.flipperdevices.faphub.dao.network.api.FapNetworkApiImpl
import com.flipperdevices.faphub.dao.network.api.FapReportApiImpl
import com.flipperdevices.faphub.dao.network.api.FapVersionApiImpl
import com.flipperdevices.faphub.dao.network.helper.FapApplicationReceiveHelper
import org.koin.dsl.module

fun koinDao() = module {
    single { FapApplicationReceiveHelper(get()) }
    single<FapDownloadApi> { FapDownloadApiImpl(get()) }
    single<FapNetworkApi> { FapNetworkApiImpl(get(), get(), get()) }
    single<FapReportApi> { FapReportApiImpl(get()) }
    single<FapVersionApi> { FapVersionApiImpl(get()) }
}