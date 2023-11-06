package com.lionzxy.flipperapp.connection

import com.flipperdevices.bridge.api.manager.FlipperRequestApi
import com.flipperdevices.bridge.api.manager.delegates.FlipperConnectionInformationApi
import com.flipperdevices.bridge.api.manager.service.FlipperVersionApi
import com.flipperdevices.core.data.SemVer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

class FlipperInformationFetcher(
    private val connectionInformationApi: FlipperConnectionInformationApi,
    private val requestApi: FlipperRequestApi,
    private val scope: CoroutineScope
) : FlipperVersionApi {
    override fun getVersionInformationFlow(): StateFlow<SemVer?> {
        TODO("Not yet implemented")
    }

    override suspend fun isSupported(version: SemVer, timeout: Long): Boolean {
        TODO("Not yet implemented")
    }
}