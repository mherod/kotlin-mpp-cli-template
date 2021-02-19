package dev.herod.kx.flow

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.native.concurrent.AtomicLong

fun loop(): Flow<Long> = flow {
    val currentCoroutineContext = currentCoroutineContext()
    val count = AtomicLong(0L)
    while (currentCoroutineContext.isActive) {
        count.increment()
        emit(count.value)
    }
}
