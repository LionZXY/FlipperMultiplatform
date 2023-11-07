package com.flipperdevices.faphub.installation.button.impl

import com.flipperdevices.faphub.installation.button.api.FapInstallationUIApi
import com.flipperdevices.faphub.installation.button.impl.api.FapInstallationUIApiImpl
import com.flipperdevices.faphub.installation.button.impl.helper.OpenFapHelper
import com.flipperdevices.faphub.installation.button.impl.helper.OpenFapHelperImpl
import com.flipperdevices.faphub.installation.button.impl.viewmodel.FapStatusViewModel
import com.flipperdevices.faphub.installation.button.impl.viewmodel.OpenFapViewModel
import com.flipperdevices.faphub.installation.queue.api.FapInstallationQueueApi
import com.flipperdevices.faphub.installation.queue.impl.api.FapInstallationQueueApiImpl
import com.flipperdevices.faphub.installation.queue.impl.api.FapQueueRunner
import com.flipperdevices.faphub.installation.queue.impl.executor.FapActionExecutor
import com.flipperdevices.faphub.installation.queue.impl.executor.InstallationActionExecutor
import com.flipperdevices.faphub.installation.queue.impl.executor.UpdateActionExecutor
import com.flipperdevices.faphub.installation.queue.impl.executor.actions.FapActionUpload
import com.flipperdevices.faphub.installation.queue.impl.executor.actions.FapIconDownloader
import com.flipperdevices.faphub.installation.stateprovider.api.api.FapInstallationStateManager
import com.flipperdevices.faphub.installation.stateprovider.impl.api.FapInstallationStateManagerImpl
import com.flipperdevices.faphub.utils.FapHubTmpFolderProvider
import org.koin.dsl.module

fun buttonKoin() = module {
    single<FapInstallationUIApi> { FapInstallationUIApiImpl() }
    single<FapStatusViewModel> { FapStatusViewModel(get(), get()) }
    single<FapInstallationStateManager> { FapInstallationStateManagerImpl(get(), get(), get()) }
    single<FapInstallationQueueApi> { FapInstallationQueueApiImpl(get()) }
    single<FapQueueRunner> { FapQueueRunner(get()) }
    single { FapActionExecutor(get(), get(), get()) }
    single { InstallationActionExecutor(get(), get(), get(), get(), get()) }
    single { UpdateActionExecutor(get(), get(), get(), get(), get()) }
    single { FapActionUpload(get(), get()) }
    single { FapHubTmpFolderProvider(get()) }
    single { FapIconDownloader(get()) }
    single { OpenFapViewModel(get()) }
    single { OpenFapHelperImpl(get()) }
}