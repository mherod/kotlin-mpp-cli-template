package dev.herod.kmpp

import kotlinx.coroutines.flow.Flow

expect fun findFile(searchPath: String, fileName: String): Flow<String>
