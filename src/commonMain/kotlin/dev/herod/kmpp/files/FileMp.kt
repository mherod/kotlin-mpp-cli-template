package dev.herod.kmpp.files

import kotlinx.coroutines.flow.Flow

interface FileMp {
    fun getParent(): FileMp
    fun isDirectory(): Boolean
    fun isFile(): Boolean
    fun listFiles(): Flow<FileMp>
    val absolutePath: String
    fun size(): Long
    fun readLines(): Flow<String>
}

expect fun file(absolutePath: String) : FileMp
