package com.example.washingbackend

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cycles")
class CycleController(val cycleService: CycleService) {

    @PostMapping("/start")
    fun startCycle(
        @RequestParam userId: String,
        @RequestParam deviceId: String
    ): ResponseEntity<String> {
        val cycleId = cycleService.startCycle(userId, deviceId)
        return ResponseEntity.ok("Cycle started with ID: $cycleId")
    }

    @GetMapping("/{id}")
    fun getCycle(@PathVariable id: String): ResponseEntity<Cycle> {
        return ResponseEntity.ok(cycleService.getCycle(id))
    }
}