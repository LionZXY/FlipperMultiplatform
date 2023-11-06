package com.flipperdevices.faphub.utils

import com.flipperdevices.bridge.rpc.api.FlipperStorageApi

private const val FLIPPER_TMP_FOLDER_PATH = "/ext/.tmp/android"

class FapHubTmpFolderProvider(
    private val flipperStorageApi: FlipperStorageApi
) {
    suspend fun provideTmpFolder(): String {
        flipperStorageApi.mkdirs(FLIPPER_TMP_FOLDER_PATH)
        return FLIPPER_TMP_FOLDER_PATH
    }
}
