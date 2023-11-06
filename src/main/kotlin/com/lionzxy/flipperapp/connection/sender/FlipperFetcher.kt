package com.lionzxy.flipperapp.connection.sender

import kotlinx.coroutines.*
import org.jetbrains.skia.OutputStream

class FlipperFetcher(
    private val flipperRequestStorage: FlipperRequestStorage,
    private val outputStream: OutputStream
) {
     fun init(scope: CoroutineScope) {
        scope.launch(Dispatchers.Default) {
            initLoop(this)
        }
    }

    private suspend fun initLoop(scope: CoroutineScope) {
        while (scope.isActive) {
            val request = flipperRequestStorage.getNextRequest(1000L)
            if (request != null) {
                withContext(Dispatchers.IO) {
                    outputStream.write(request.data.toByteArray())
                }
                request.onSendCallback?.onSendCallback()
            }
        }
    }

}