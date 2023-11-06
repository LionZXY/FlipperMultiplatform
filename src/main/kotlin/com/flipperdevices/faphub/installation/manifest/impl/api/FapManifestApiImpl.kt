package com.flipperdevices.faphub.installation.manifest.impl.api

import com.flipperdevices.bridge.service.api.provider.FlipperServiceProvider
import com.flipperdevices.core.ktx.jre.withLock
import com.flipperdevices.core.log.LogTagProvider
import com.flipperdevices.core.log.error
import com.flipperdevices.core.log.info
import com.flipperdevices.faphub.dao.api.FapVersionApi
import com.flipperdevices.faphub.installation.manifest.api.FapManifestApi
import com.flipperdevices.faphub.installation.manifest.impl.utils.FapManifestCacheLoader
import com.flipperdevices.faphub.installation.manifest.impl.utils.FapManifestDeleter
import com.flipperdevices.faphub.installation.manifest.impl.utils.FapManifestUploader
import com.flipperdevices.faphub.installation.manifest.impl.utils.FapManifestsLoader
import com.flipperdevices.faphub.installation.manifest.model.FapManifestEnrichedItem
import com.flipperdevices.faphub.installation.manifest.model.FapManifestState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
class FapManifestApiImpl(
    private val loader: FapManifestsLoader,
    private val manifestUploader: FapManifestUploader,
    private val manifestDeleter: FapManifestDeleter,
    private val flipperServiceProvider: FlipperServiceProvider,
    fapManifestCacheLoader: FapManifestCacheLoader,
    fapVersionApi: FapVersionApi,
) : FapManifestApi, LogTagProvider {
    override val TAG = "FapManifestApi"

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val mutex = Mutex()

    private val enrichedHelper = FapManifestEnrichedHelper(
        scope = scope,
        versionApi = fapVersionApi,
        cacheLoader = fapManifestCacheLoader
    )

    init {
        scope.launch(Dispatchers.Default) {
            flipperServiceProvider
                .getServiceApi()
                .connectionInformationApi
                .getConnectionStateFlow()
                .collectLatest {
                    invalidate()
                }
        }
    }

    override fun getManifestFlow(): StateFlow<FapManifestState> {
        return enrichedHelper.getManifestState()
    }

    override suspend fun add(
        pathToFap: String,
        fapManifestItem: FapManifestEnrichedItem
    ) = withLock(mutex, "add") {
        manifestUploader.save(pathToFap, fapManifestItem.fapManifestItem)
        enrichedHelper.onAdd(fapManifestItem)
    }

    override suspend fun remove(
        applicationUid: String
    ) = withLock(mutex, "remove") {
        val toRemoveManifests = enrichedHelper.onDelete(applicationUid)
        info { "On $applicationUid toRemoveManifests is $toRemoveManifests" }
        toRemoveManifests.forEach {
            manifestDeleter.delete(it)
        }
    }

    override fun invalidateAsync() {
        scope.launch(Dispatchers.Default) { invalidate() }
    }

    private suspend fun invalidate() = withLock(mutex, "invalidate") {
        runCatching {
            enrichedHelper.onLoadFresh()
            loader.load()
        }.onFailure {
            error(it) { "Failed load manifests" }
            enrichedHelper.onError(it)
        }.onSuccess {
            enrichedHelper.onUpdateManifests(it)
        }
    }
}
