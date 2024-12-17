package com.example.washingbackend

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CycleServiceTest {

    private val cycleService = CycleService()

    @Test
    fun `test startCycle creates a new cycle`() {
        val userId = "user1"
        val deviceId = "device1"

        val cycleId = cycleService.startCycle(userId, deviceId)
        assertNotNull(cycleId)

        val cycle = cycleService.getCycle(cycleId)
        assertEquals(userId, cycle.userId)
        assertEquals(deviceId, cycle.deviceId)
        assertEquals("IN_PROGRESS", cycle.status)
    }

    @Test
    fun `test getCycle throws exception for invalid ID`() {
        val invalidId = "nonexistent"
        assertThrows(RuntimeException::class.java) {
            cycleService.getCycle(invalidId)
        }
    }
}