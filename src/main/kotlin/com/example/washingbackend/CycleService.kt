package com.example.washingbackend

import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import java.util.UUID

data class Cycle(
    val id: String, 
    val userId: String, 
    val deviceId: String, 
    var status: String
)

@Service
class CycleService(val switchService: SwitchService) {
    private val cycles = ConcurrentHashMap<String, Cycle>()
    private val wallets = ConcurrentHashMap<String, Wallet>()

    fun startCycle(userId: String, device: Device, tariff: Tariff): String {
        val wallet = wallets[userId] ?: throw RuntimeException("Wallet not found")

        if (wallet.balance < tariff.price)
            throw RuntimeException("Insufficient balance")

        val cycleId = UUID.randomUUID().toString()
        wallet.balance -= tariff.price

        val newCycle = Cycle(
            id = cycleId,
            startedAt = Instant.now(),
            status = "in-progress",
            userId = userId,
            deviceId = device.id,
            invoiceLines = listOf(InvoiceLine("Washing cycle", tariff.price, tariff.currency))
        )

        cycles[cycleId] = newCycle
        switchService.startSwitch(device.switchId).subscribe()

        return cycleId
    }

    fun getCycleStatus(cycleId: String): Cycle? {
        return cycles[cycleId]
    }
}
