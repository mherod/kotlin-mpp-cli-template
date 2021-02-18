package dev.herod.kmpp.files

import dev.herod.kmpp.exec
import dev.herod.kmpp.runBlocking
import dev.herod.kx.flow.any
import dev.herod.kx.splitOnSpacing
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

actual fun file(absolutePath: String): FileMp = FileMpPosix(absolutePath = absolutePath)

@OptIn(FlowPreview::class)
data class FileMpPosix(
    override val absolutePath: String,
) : FileMp {

    override fun getParent(): FileMp = when {
        isFile() -> file(absolutePath = absolutePath.substringBeforeLast("/"))
        isDirectory() -> file(absolutePath = absolutePath.substringBeforeLast("/"))
        else -> error("can't determine parent")
    }

    override fun isDirectory(): Boolean = runBlocking {
        exec("stat -x $absolutePath").any { "FileType: Directory" in it }
    }

    override fun isFile(): Boolean = runBlocking {
        exec("stat -x $absolutePath").any { "FileType: Regular File" in it }
    }

    override fun listFiles(): Flow<FileMp> = when {
        isFile() -> flowOf(this)
        isDirectory() -> exec(command = "ls \"$absolutePath\"").map { file("$absolutePath/$it") }
        else -> emptyFlow()
    }

    override fun size(): Long = runBlocking {
        exec("ls -l $absolutePath")
            .single()
            .splitOnSpacing()[4]
            .toLong()
    }

    override fun readLines(): Flow<String> {
        // using cat is temporary
        return exec("cat $absolutePath")
            .flatMapConcat { s ->
                s.split("\n".toRegex()).asFlow()
            }
    }
}
