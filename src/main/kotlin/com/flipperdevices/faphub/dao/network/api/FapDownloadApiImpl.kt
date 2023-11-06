package com.flipperdevices.faphub.dao.network.api

import com.flipperdevices.core.log.LogTagProvider
import com.flipperdevices.core.log.info
import com.flipperdevices.core.preference.FlipperStorageProvider
import com.flipperdevices.core.progress.ProgressListener
import com.flipperdevices.core.progress.ProgressWrapperTracker
import com.flipperdevices.faphub.dao.api.FapDownloadApi
import com.flipperdevices.faphub.dao.network.ktorfit.api.KtorfitBundleApi
import com.flipperdevices.faphub.target.model.FlipperTarget
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.contentLength
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes
import java.io.File

class FapDownloadApiImpl(
    private val bundleApi: KtorfitBundleApi
) : FapDownloadApi, LogTagProvider {
    override val TAG = "FapDownloadApi"

    override suspend fun downloadBundle(
        target: FlipperTarget.Received,
        versionUid: String,
        listener: ProgressListener?
    ): File {
        info { "Start download bundle for $versionUid and $target" }

        val file = FlipperStorageProvider.getTemporaryFile()

        bundleApi
            .downloadBundle(versionUid, target.target, target.sdk.toString())
            .execute { response ->
                response.saveToFile(file, listener?.let { ProgressWrapperTracker(it) })
            }

        info { "Complete download for $versionUid" }

        return file
    }
}

private suspend fun HttpResponse.saveToFile(
    file: File,
    listener: ProgressWrapperTracker?
) {
    val channel: ByteReadChannel = body()
    val totalBytes = contentLength()
    while (!channel.isClosedForRead) {
        val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
        while (!packet.isEmpty) {
            val bytes = packet.readBytes()
            file.appendBytes(bytes)
            if (totalBytes != null && totalBytes > 0) {
                listener?.report(file.length(), totalBytes)
            }
        }
    }
}
