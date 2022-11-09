package com.timecapsule.api.controller

import com.timecapsule.api.dto.CapsuleDaysResponse
import com.timecapsule.api.service.CapsuleService
import org.springframework.web.bind.annotation.*

@RestController
class CapsuleController(
    private val capsuleService: CapsuleService,
) {

    @GetMapping("/api/capsule/{capsuleId}")
    fun getCapsuleDays(@PathVariable capsuleId: Long): CapsuleDaysResponse {
        return CapsuleDaysResponse(capsuleId = capsuleId, days = capsuleService.getCapsuleDays(capsuleId))
    }

}
