package com.lionzxy.flipperapp.connection

import com.flipperdevices.bridge.api.manager.FlipperRequestApi
import com.flipperdevices.bridge.api.model.FlipperRequest
import com.flipperdevices.bridge.api.model.FlipperSerialSpeed
import com.flipperdevices.bridge.impl.manager.PeripheralResponseReader
import com.flipperdevices.core.log.*
import com.flipperdevices.protobuf.Flipper
import com.flipperdevices.protobuf.copy
import com.lionzxy.flipperapp.connection.sender.FlipperRequestStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

private typealias OnReceiveResponse = suspend (Flipper.Main) -> Unit

class FlipperRequestApiImpl(
    private val scope: CoroutineScope,
    private val reader: PeripheralResponseReader,
    private val requestStorage: FlipperRequestStorage
) : FlipperRequestApi,
    LogTagProvider {
    override val TAG = "FlipperRequestApi"

    // Start from 1 because 0 is default in protobuf
    private var idCounter = AtomicInteger(1)
    private val requestListeners = ConcurrentHashMap<Int, OnReceiveResponse>()
    private val notificationMutableFlow = MutableSharedFlow<Flipper.Main>()

    init {
        subscribeToAnswers(scope)
    }

    override fun notificationFlow(): Flow<Flipper.Main> {
        return notificationMutableFlow
    }

    override fun request(
        command: FlipperRequest
    ): Flow<Flipper.Main> = channelFlow {
        verbose { "Pending commands count: ${requestListeners.size}. Request $command" }
        // Generate unique ID for each command
        val uniqueId = findEmptyId(currentId = command.data.commandId)
        val requestWithId = command.copy(
            data = command.data.copy {
                commandId = uniqueId
            }
        )

        // Add answer listener to listeners
        requestListeners[uniqueId] = {
            send(it)
            if (!it.hasNext) {
                requestListeners.remove(uniqueId)
                close()
            }
        }



        requestStorage.sendRequest(requestWithId)

        awaitClose {
            requestStorage.removeRequest(requestWithId)
            requestListeners.remove(uniqueId)
        }
    }

    override suspend fun request(
        commandFlow: Flow<FlipperRequest>,
        onCancel: suspend (Int) -> Unit
    ): Flipper.Main {
        verbose { "Pending commands count: ${requestListeners.size}. Request command flow" }
        // Generate unique ID for each command
        val uniqueId = findEmptyId()
        // This is dirty way to understand if request is finished correctly with response
        var isFinished = false

        @Suppress("SuspendFunctionOnCoroutineScope")
        val commandAnswerJob = scope.async {
            val result = awaitCommandAnswer(uniqueId)
            isFinished = true
            return@async result
        }

        val flowCollectJob = commandFlow.onEach { request ->
            val requestWithId = request.copy(
                data = request.data.copy {
                    commandId = uniqueId
                }
            )
            requestStorage.sendRequest(requestWithId)
        }.onCompletion {
            if (it != null) {
                error(it) { "Cancel send because flow is failed" }
                commandAnswerJob.cancelAndJoin()
            }
        }.launchIn(scope + Dispatchers.Default)

        return try {
            commandAnswerJob.await()
        } finally {
            withContext(NonCancellable) {
                flowCollectJob.cancelAndJoin()
                commandAnswerJob.cancelAndJoin()
                if (!isFinished) {
                    info { "Requests with flow with id $uniqueId is canceled" }
                    onCancel(uniqueId)
                }
            }
        }
    }

    override suspend fun requestWithoutAnswer(vararg commands: FlipperRequest) {
        requestStorage.sendRequest(*commands)
    }

    private fun findEmptyId(currentId: Int = 0): Int {
        if (currentId != 0 && requestListeners[currentId] == null) {
            return currentId
        }

        var counter: Int
        do {
            counter = idCounter.updateAndGetSafe {
                if (it == Int.MAX_VALUE) {
                    return@updateAndGetSafe 1
                } else {
                    return@updateAndGetSafe it + 1
                }
            }
        } while (requestListeners[counter] != null)
        return counter
    }

    private suspend fun awaitCommandAnswer(
        uniqueId: Int
    ): Flipper.Main = suspendCancellableCoroutine { cont ->
        requestListeners[uniqueId] = {
            requestListeners.remove(uniqueId)
            cont.resume(it) { throwable ->
                error(throwable) { "Error on resume execution of $uniqueId command. Answer is $it" }
            }
        }

        cont.invokeOnCancellation {
            requestStorage.removeIf { it.data.commandId == uniqueId }
            requestListeners.remove(uniqueId)
        }
    }

    private fun subscribeToAnswers(scope: CoroutineScope) {
        scope.launch(Dispatchers.Default) {
            reader.getResponses().collect {
                val listener = requestListeners[it.commandId]
                if (listener == null) {
                    warn { "Receive package without id $it" }
                    notificationMutableFlow.emit(it)
                } else {
                    listener.invoke(it)
                }
            }
        }
    }
}

/**
 * Copy from AtomicInteger#updateAndGet
 */
fun AtomicInteger.updateAndGetSafe(updater: (Int) -> Int): Int {
    var prev: Int
    var next: Int
    do {
        prev = get()
        next = updater(prev)
    } while (!compareAndSet(prev, next))
    return next
}
