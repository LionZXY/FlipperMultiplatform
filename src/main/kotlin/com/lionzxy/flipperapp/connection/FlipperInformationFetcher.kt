package com.lionzxy.flipperapp.connection

import com.flipperdevices.bridge.api.manager.FlipperRequestApi
import com.flipperdevices.bridge.api.manager.delegates.FlipperConnectionInformationApi
import com.flipperdevices.bridge.api.manager.service.FlipperVersionApi
import com.flipperdevices.bridge.rpcinfo.impl.FlipperRpcInformationApiImpl
import com.flipperdevices.core.data.SemVer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

class FlipperInformationFetcher(
) : FlipperVersionApi {

    override fun getVersionInformationFlow(): StateFlow<SemVer?> {
        return MutableStateFlow(SemVer(0, 20))
    }

    override suspend fun isSupported(version: SemVer, timeout: Long) = true
}