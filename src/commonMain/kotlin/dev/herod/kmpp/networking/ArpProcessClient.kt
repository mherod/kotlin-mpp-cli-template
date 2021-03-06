package dev.herod.kmpp.networking

import dev.herod.kmpp.networking.model.ArpEntry
import dev.herod.kx.extractGroup
import dev.herod.kx.runOrNull
import dev.herod.kx.splitOnSpacing
import dev.herod.kmpp.exec
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull

class ArpProcessClient : ArpClient {
    override fun list(): Flow<ArpEntry> = flow {
        emitAll(
            flow = exec("arp -a")
                .mapNotNull { line ->
                    runOrNull {
                        val splits = line.splitOnSpacing()
                        val mac = "at\\s+\\({0}(\\S+)\\){0}".toRegex().extractGroup(line)
                        val address = "\\((\\S+)\\)\\s+at".toRegex().extractGroup(line)
                        val netInterface = "on\\s+(\\S+)".toRegex().extractGroup(line)
                        ArpEntry(
                            name = splits.first(),
                            address = requireNotNull(address),
                            mac = requireNotNull(mac),
                            netInterface = requireNotNull(netInterface)
                        )
                    }
                })
    }
}
