package com.flipperdevices.faphub.installation.manifest

import com.flipperdevices.bridge.rpc.api.FlipperStorageApi
import com.flipperdevices.bridge.rpc.impl.api.FlipperStorageApiImpl
import com.flipperdevices.faphub.installation.manifest.api.FapManifestApi
import com.flipperdevices.faphub.installation.manifest.impl.api.FapManifestApiImpl
import com.flipperdevices.faphub.installation.manifest.impl.utils.*
import com.flipperdevices.faphub.installation.manifest.model.FapManifestItem
import com.flipperdevices.faphub.utils.FapHubTmpFolderProvider
import org.koin.dsl.module

fun manifestKoin() = module {
    single<FapManifestApi> { FapManifestApiImpl(get(), get(), get(), get(), get(), get()) }
    single { FapHubTmpFolderProvider(get()) }
    single { FapManifestAtomicMover(get(), get()) }
    single { FapManifestCacheLoader(get(), get()) }
    single { FapManifestDeleter(get()) }
    single { FapManifestParser() }
    single { FapManifestsLoader(get(), get(), get()) }
    single { FapManifestUploader(get(), get(), get(), get()) }
    single<FlipperStorageApi>() { FlipperStorageApiImpl(get()) }
}