package dev.herod.kmpp

import kotlinx.cinterop.toKString
import platform.posix.getenv

val debugLogsEnabled: Boolean by lazy(getenv("DEBUG")?.toKString()::toBoolean)

