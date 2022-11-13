package com.timecapsule.api.controller

import com.timecapsule.api.dto.*
import com.timecapsule.api.service.CapsuleService
import com.timecapsule.api.service.JwtAuthenticationProvider
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Capsule Controller")
@RestController
class CapsuleController(
    private val capsuleService: CapsuleService,
    private val jwtAuthenticationProvider: JwtAuthenticationProvider,
) {

    @Operation(summary = "캡슐 여행 정보")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK",
            content = [Content(schema = Schema(implementation = CapsuleDaysResponse::class))]),
        ApiResponse(responseCode = "404", description = "해당 캡슐을 찾을 수 없음",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    ])
    @GetMapping("/api/capsule/{capsuleId}/days")
    fun getCapsuleDays(@PathVariable capsuleId: Long): CapsuleDaysResponse = CapsuleDaysResponse(capsuleId = capsuleId, days = capsuleService.getCapsuleDays(capsuleId))

    @Operation(summary = "나의 캡슐 정보")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK",
            content = [Content(array = ArraySchema(schema = Schema(implementation = CapsuleResponse::class)))]),
    ])
    @GetMapping("/api/capsule/my")
    fun getMyCapsule(
        @RequestHeader(name = "Authorization") accessToken: String,
    ): List<CapsuleResponse> {
        val memberId = jwtAuthenticationProvider.getMemberIdFromToken(accessToken.substring(7))
        return capsuleService.getCapsuleCreatedBy(memberId).map { CapsuleResponse.of(it) }
    }

    @Operation(summary = "나의 캡슐 정보")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK",
            content = [Content(schema = Schema(implementation = CapsuleResponse::class))]),
    ])
    @PostMapping("/api/capsule")
    fun createCapsule(
        @RequestHeader(name = "Authorization") accessToken: String,
        @RequestBody request: CreateCapsuleRequest,
    ): CapsuleResponse {
        val memberId = jwtAuthenticationProvider.getMemberIdFromToken(accessToken.substring(7))
        return CapsuleResponse.of(capsuleService.createCapsule(request, memberId))
    }
}
