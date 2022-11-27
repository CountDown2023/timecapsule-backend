package com.timecapsule.api.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class SwaggerRedirectController {

    @GetMapping("/api/usage")
    fun getUsage(): ResponseEntity<Void> {
        val headers = HttpHeaders().apply {
            location = URI.create("/swagger-ui/index.html")
        }
        return ResponseEntity<Void>(headers, HttpStatus.MOVED_PERMANENTLY)
    }
}
