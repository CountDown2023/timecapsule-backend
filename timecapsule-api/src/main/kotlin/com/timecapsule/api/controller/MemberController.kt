package com.timecapsule.api.controller

import com.timecapsule.api.dto.*
import com.timecapsule.api.service.JwtAuthenticationProvider
import com.timecapsule.api.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@Tag(name = "Member Controller")
@RestController
class MemberController(
    private val memberService: MemberService,
    private val jwtAuthenticationProvider: JwtAuthenticationProvider,
) {

    @Operation(summary = "회원가입 요청")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "400", description = "BAD REQUEST (INVALID)",
            content = [Content(schema = Schema(implementation = InvalidParamErrorResponse::class))]),
        ApiResponse(responseCode = "400", description = "BAD REQUEST (DUPLICATE)",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    ])
    @PostMapping("/api/member/sign-up")
    fun signUp(@Validated @RequestBody request: SignupRequest): ResponseEntity<Void> {
        memberService.signUp(request.nickname, request.password, request.email)
        return ResponseEntity.created(URI.create("/api/login")).build()
    }

    @Operation(summary = "로그인 요청")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK",
            content = [Content(schema = Schema(implementation = TokenInfo::class))]),
    ])
    @PostMapping("/api/member/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<TokenInfo> {
        val member = memberService.getMember(request.nickname)
        return ResponseEntity.ok(jwtAuthenticationProvider.generateTokens(member.nonNullId))
    }

    @Operation(summary = "Refresh Token 재발급 요청")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK",
            content = [Content(schema = Schema(implementation = TokenInfo::class))]),
    ])
    @PostMapping("/api/member/refresh")
    fun refreshAccessToken(@RequestBody request: RefreshRequest): ResponseEntity<TokenInfo> {
        jwtAuthenticationProvider.validateToken(request.refreshToken)
        val member = memberService.getMember(request.nickname)

        return jwtAuthenticationProvider.generateAccessToken(member.nonNullId.toString(), Date())
            .let { ResponseEntity.ok(TokenInfo(accessToken = it, refreshToken = null)) }
    }
}
