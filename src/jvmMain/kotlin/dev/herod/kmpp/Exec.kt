package dev.herod.kmpp

import kotlinx.coroutines.flow.Flow

actual fun exec(command: String): Flow<String> {
//    println(command)
    return Runtime.getRuntime().exec(command).mergedInputStreamFlow()
}
