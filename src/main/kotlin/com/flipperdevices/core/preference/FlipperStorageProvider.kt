package com.flipperdevices.core.preference

import java.io.File

object FlipperStorageProvider {
    private const val KEYS_DIR = "keysfiles/"
    internal const val DATASTORE_FILENAME_SETTINGS = "settings.pb"
    internal const val DATASTORE_FILENAME_PAIR_SETTINGS = "pair_settings.pb"

    fun getTemporaryFile(): File {
        var index = 0
        var file: File
        do {
            file = File("tmp", "temporaryfile-$index")
            index++
        } while (file.exists())
        file.parentFile?.mkdirs()
        file.createNewFile()
        return file
    }

    suspend fun <T> useTemporaryFile(block: suspend (File) -> T): T {
        val file = getTemporaryFile()

        val result = try {
            block(file)
        } finally {
            file.delete()
        }
        return result
    }

    suspend fun <T> useTemporaryFolder(block: suspend (File) -> T): T {
        var index = 0
        var folder: File
        do {
            folder = File("tmp", "temporaryfolder-$index")
            index++
        } while (folder.exists())
        folder.mkdirs()

        val result = try {
            block(folder)
        } finally {
            folder.deleteRecursively()
        }
        return result
    }

    fun getKeyFolder(): File {
        return File("tmp", KEYS_DIR)
    }
}
