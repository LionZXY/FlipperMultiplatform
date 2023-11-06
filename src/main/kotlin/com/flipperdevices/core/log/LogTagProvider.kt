package com.flipperdevices.core.log

import kotlin.math.log

@Suppress("PropertyName")
interface LogTagProvider {
    @Suppress("VariableNaming")
    val TAG: String
}

inline fun LogTagProvider.error(logMessage: () -> String) {
    println("$TAG: ${logMessage()}")
}

inline fun LogTagProvider.error(error: Throwable?, logMessage: () -> String) {
    error?.printStackTrace()
    println("$TAG: ${logMessage()}")
}

inline fun LogTagProvider.info(logMessage: () -> String) {
    println("$TAG: ${logMessage()}")
}

inline fun LogTagProvider.verbose(logMessage: () -> String) {
    println("$TAG: ${logMessage()}")
}

inline fun LogTagProvider.warn(logMessage: () -> String) {
    println("$TAG: ${logMessage()}")
}

inline fun LogTagProvider.debug(logMessage: () -> String) {
    println("$TAG: ${logMessage()}")
}

inline fun LogTagProvider.wtf(logMessage: () -> String) {
    println("$TAG: ${logMessage()}")
}
