package com.timecapsule.api.controller

import com.timecapsule.api.dto.CapsuleDaysResponse
import com.timecapsule.api.dto.ErrorResponse
import com.timecapsule.api.service.CapsuleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Capsule Controller")
@RestController
class CapsuleController(
    private val capsuleService: CapsuleService,
) {

    @Operation(summary = "캡슐 여행 정보")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK",
            content = [Content(schema = Schema(implementation = CapsuleDaysResponse::class))]),
        ApiResponse(responseCode = "404", description = "해당 캡슐을 찾을 수 없음",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    ])
    @GetMapping("/api/capsule/{capsuleId}")
    fun getCapsuleDays(@PathVariable capsuleId: Long): CapsuleDaysResponse = CapsuleDaysResponse(capsuleId = capsuleId, days = capsuleService.getCapsuleDays(capsuleId))

}
