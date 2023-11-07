package com.lionzxy.flipperapp.connection.sender

import com.flipperdevices.core.log.LogTagProvider
import com.flipperdevices.core.log.info
import kotlinx.coroutines.*
import org.jetbrains.skia.OutputStream

class FlipperFetcher(
    private val flipperRequestStorage: FlipperRequestStorage,
    private val outputStream: OutputStream
) : LogTagProvider {
    override val TAG = "FlipperFetcher"
    fun init(scope: CoroutineScope) {
        scope.launch(Dispatchers.Default) {
            initLoop(this)
        }
    }

    private suspend fun initLoop(scope: CoroutineScope) {
        while (scope.isActive) {
            val request = flipperRequestStorage.getNextRequest(1000L)
            if (request != null) {
                info { "Find request $request" }
                withContext(Dispatchers.IO) {
                    outputStream.write(request.data.toByteArray())
                }
                request.onSendCallback?.onSendCallback()
            }
        }
    }

}