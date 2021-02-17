package dev.herod.kmpp

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter

actual fun findFile(searchPath: String, fileName: String): Flow<String> {
    return exec(
        command = "find \"$searchPath\" -type f -name \"$fileName\""
    ).filter { output ->
        output.endsWith(fileName)
    }
}
