package com.example.washingbackend

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CycleControllerTest(@Autowired val webTestClient: WebTestClient) {

    @Test
    fun `test startCycle endpoint`() {
        webTestClient.post().uri("/api/cycles/start?userId=user1&deviceId=device1")
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .value { response ->
                assert(response.contains("Cycle started with ID"))
            }
    }

    @Test
    fun `test getCycle endpoint`() {
        val response = webTestClient.post().uri("/api/cycles/start?userId=user1&deviceId=device1")
            .exchange()
            .expectStatus().isOk
            .returnResult(String::class.java).responseBody.blockFirst()

        val cycleId = response?.split("ID: ")?.last()?.trim()

        webTestClient.get().uri("/api/cycles/$cycleId")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.userId").isEqualTo("user1")
            .jsonPath("$.deviceId").isEqualTo("device1")
            .jsonPath("$.status").isEqualTo("IN_PROGRESS")
    }
}