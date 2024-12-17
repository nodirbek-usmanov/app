package com.example.washingbackend

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.UUID

data class Cycle(val id: String, val userId: String, val deviceId: String, var status: String)

@Service
class CycleService {
    private val cycles = ConcurrentHashMap<String, Cycle>()

    fun startCycle(userId: String, deviceId: String): String {
        val cycleId = UUID.randomUUID().toString()
        val cycle = Cycle(cycleId, userId, deviceId, "IN_PROGRESS")
        cycles[cycleId] = cycle
        return cycleId
    }

    fun getCycle(id: String): Cycle {
        return cycles[id] ?: throw RuntimeException("Cycle not found")
    }
}