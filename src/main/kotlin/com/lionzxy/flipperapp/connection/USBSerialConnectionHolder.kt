package com.lionzxy.flipperapp.connection

import com.fazecast.jSerialComm.SerialPort
import com.flipperdevices.bridge.api.manager.delegates.FlipperConnectionInformationApi
import com.flipperdevices.bridge.api.manager.ktx.state.ConnectionState
import com.flipperdevices.bridge.api.manager.ktx.state.FlipperSupportedState
import com.flipperdevices.bridge.impl.manager.PeripheralResponseReader
import com.flipperdevices.bridge.service.api.FlipperServiceApi
import com.lionzxy.flipperapp.connection.sender.FlipperFetcher
import com.lionzxy.flipperapp.connection.sender.FlipperRequestStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.io.InputStream
import java.nio.charset.Charset
import java.time.Duration
import java.util.concurrent.*


private const val FLIPPER_COM_PORT = "/dev/cu.usbmodemflip_Engonbun1"
private val FLOOD_END_STRING = "\r\n\r\n>: ".toByteArray()
private val COMMAND = "start_rpc_session\r".toByteArray()

private val scope = GlobalScope + Dispatchers.Default

class USBSerialConnectionHolder : FlipperServiceApi, FlipperConnectionInformationApi, Runnable {
    private val serialConnectionThread = Thread(this)

    private val connectionFlow = MutableStateFlow<ConnectionState>(
        ConnectionState.Disconnected(ConnectionState.Disconnected.Reason.UNKNOWN)
    )
    private val deviceNameFlow = MutableStateFlow<String?>(null)
    private val reader = PeripheralResponseReader(scope)
    private val requestStorage = FlipperRequestStorage()
    override val connectionInformationApi = this
    override val requestApi = FlipperRequestApiImpl(
        reader = reader,
        scope = scope,
        requestStorage = requestStorage
    )
    override val flipperVersionApi = FlipperInformationFetcher()


    fun init() {
        //serialConnectionThread.start()
    }

    override fun run() {
        while (!Thread.interrupted()) {
            val scope = CoroutineScope(Dispatchers.Default)
            val serialPort = SerialPort.getCommPort(FLIPPER_COM_PORT)
            try {
                connectionFlow.update { ConnectionState.Connecting }
                serialPort.setComPortParameters(
                    230400, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY
                )
                val portOpened = serialPort.openPort()
                serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
                println("Port is opened: $portOpened")
                if (portOpened) {
                    connectionFlow.update { ConnectionState.Initializing }
                    serialPort.inputStream.use { inputStream ->
                        serialPort.outputStream.use { outputStream ->
                            skipFlood(inputStream, FLOOD_END_STRING.map { it.toUByte() })
                            outputStream.write(COMMAND)
                            outputStream.flush()
                            readUntilAvailable(inputStream)

                            runBlocking {
                                reader.initialize()
                            }
                            val fetcher = FlipperFetcher(requestStorage, outputStream)
                            fetcher.init(scope)
                            connectionFlow.update { ConnectionState.Ready(FlipperSupportedState.READY) }
                            readStreamUntilStop(inputStream, reader)
                            runBlocking {
                                reader.reset()
                            }
                        }
                    }
                }
            } catch (exception: Throwable) {
                exception.printStackTrace()
            } finally {
                serialPort.closePort()
                scope.cancel()
            }
            connectionFlow.update { ConnectionState.Disconnected(ConnectionState.Disconnected.Reason.UNKNOWN) }
            Thread.sleep(1000L)
        }
    }

    override fun isDeviceConnected() = connectionFlow.value.isConnected


    override fun getConnectionStateFlow() = connectionFlow

    override fun getConnectedDeviceName() = deviceNameFlow.value


}

private fun readStreamUntilStop(inputStream: InputStream, reader: PeripheralResponseReader) {
    val buffer = ByteArray(1024)
    var result = 1
    while (result > 0) {
        result = inputStream.read(buffer)
        val readedBytes = buffer.take(result).toByteArray()
        println("Read $result bytes with ${String(readedBytes, Charset.defaultCharset())}")
        reader.onReceiveBytes(readedBytes)
    }
}

private fun readUntilAvailable(inputStream: InputStream) {
    while (!Thread.interrupted()) {
        val timeout: Duration = Duration.ofSeconds(1)
        val executor = Executors.newSingleThreadExecutor()

        val handler: Future<Any> = executor.submit<Any> {
            inputStream.read()
        }

        try {
            handler[timeout.toMillis(), TimeUnit.MILLISECONDS]
        } catch (e: TimeoutException) {
            handler.cancel(true)
            return
        }
    }
}

private fun skipFlood(inputStream: InputStream, floodBytes: List<UByte>) {
    var floodCurrentIndex = 0
    while (!Thread.interrupted()) {
        val byte = inputStream.read()
        if (floodBytes[floodCurrentIndex].toInt() == byte) {
            floodCurrentIndex++
        } else {
            floodCurrentIndex = 0
        }
        if (floodCurrentIndex == floodBytes.size) {
            return
        }
    }
}

fun main() {
    val serialPort = SerialPort.getCommPort(FLIPPER_COM_PORT)
    serialPort.setComPortParameters(
        230400, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY
    )
    serialPort.openPort(1000)
    serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

    try {
        serialPort.inputStream.use { inputStream ->
            val buffer = ByteArray(1024)
            var result = 1
            while (result > 0) {
                result = inputStream.read(buffer)
                println("Read $result bytes")
                println(String(buffer.take(result).toByteArray(), Charset.defaultCharset()))
            }
        }
    } finally {
        serialPort.closePort()
    }

}