package dev.herod.kmpp

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import platform.posix.fgets
import platform.posix.pclose
import platform.posix.popen

actual fun exec(command: String): Flow<String> = flow {
    if (debugLogsEnabled) println(">> $command")
    popen(command, "r")?.let { pointer ->
        memScoped {
            val readBufferLength = 128
            val buffer = allocArray<ByteVar>(readBufferLength)
            var line = fgets(buffer, readBufferLength, pointer)?.toKString()?.trim()
            while (line != null) {
                if (debugLogsEnabled) println("<< $line")
                emit(line)
                line = fgets(buffer, readBufferLength, pointer)?.toKString()?.trim()
            }
        }
        pclose(pointer)
    }
}
