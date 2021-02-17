package dev.herod.kmpp

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.io.File

actual fun findFile(searchPath: String, fileName: String): Flow<String> {
    return File(searchPath)
        .walkTopDown()
        .filter { it.isFile && it.name == fileName }
        .map { it.absolutePath }
        .asFlow()
}
