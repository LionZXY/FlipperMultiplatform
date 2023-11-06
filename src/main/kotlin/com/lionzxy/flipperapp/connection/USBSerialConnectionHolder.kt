package com.lionzxy.flipperapp.connection

import com.fazecast.jSerialComm.SerialPort
import com.flipperdevices.bridge.api.manager.FlipperRequestApi
import com.flipperdevices.bridge.api.manager.delegates.FlipperConnectionInformationApi
import com.flipperdevices.bridge.api.manager.ktx.state.ConnectionState
import com.flipperdevices.bridge.api.manager.service.FlipperVersionApi
import com.flipperdevices.bridge.impl.manager.PeripheralResponseReader
import com.flipperdevices.bridge.impl.utils.ByteEndlessInputStream
import com.flipperdevices.bridge.service.api.FlipperServiceApi
import com.flipperdevices.protobuf.Flipper
import com.lionzxy.flipperapp.connection.sender.FlipperFetcher
import com.lionzxy.flipperapp.connection.sender.FlipperRequestStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.io.InputStream
import java.lang.Runnable
import java.nio.charset.Charset

private const val FLIPPER_COM_PORT = "/dev/cu.usbmodemflip_Engonbun1"
private val FLOOD_END_STRING = "\r\n\r\n>: ".toByteArray().map { it.toUByte() }

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
        serialConnectionThread.start()
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
                            skipFlood(inputStream)
                            runBlocking {
                                reader.reset()
                                requestStorage.removeIf { true }
                            }

                            outputStream.write("start_rpc_session\r".toByteArray())
                            val fetcher = FlipperFetcher(requestStorage, outputStream)
                            fetcher.init(scope)
                            connectionFlow.update { ConnectionState.RetrievingInformation }
                            readStreamUntilStop(inputStream, reader)
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
        println("Read $result bytes")
        reader.onReceiveBytes(buffer.take(result).toByteArray())
    }
}

private fun skipFlood(inputStream: InputStream) {
    var floodCurrentIndex = 0
    while (!Thread.interrupted()) {
        val byte = inputStream.read()
        if (FLOOD_END_STRING[floodCurrentIndex].toInt() == byte) {
            floodCurrentIndex++
        } else {
            floodCurrentIndex = 0
        }
        if (floodCurrentIndex == FLOOD_END_STRING.size) {
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