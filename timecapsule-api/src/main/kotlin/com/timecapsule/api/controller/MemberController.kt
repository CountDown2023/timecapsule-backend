package com.timecapsule.api.controller

import com.timecapsule.api.dto.LoginRequest
import com.timecapsule.api.dto.RefreshRequest
import com.timecapsule.api.dto.SignupRequest
import com.timecapsule.api.dto.TokenInfo
import com.timecapsule.api.service.JwtAuthenticationProvider
import com.timecapsule.api.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
class MemberController(
    private val memberService: MemberService,
    private val jwtAuthenticationProvider: JwtAuthenticationProvider,
) {

    @PostMapping("/api/sign-up")
    fun signUp(@Validated @RequestBody request: SignupRequest): ResponseEntity<Void> {
        memberService.signUp(request.nickname, request.password, request.email)
        return ResponseEntity.created(URI.create("/api/login")).build()
    }

    @PostMapping("/api/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<TokenInfo> {
        val member = memberService.getMember(request.nickname)
        memberService.validatePassword(request.password, member.password)
        return ResponseEntity.ok(jwtAuthenticationProvider.generateTokens(member.nonNullId))
    }

    @PostMapping("/api/refresh")
    fun refreshAccessToken(@RequestBody request: RefreshRequest): ResponseEntity<TokenInfo> {
        jwtAuthenticationProvider.validateToken(request.refreshToken)
        val member = memberService.getMember(request.nickname)

        return jwtAuthenticationProvider.generateAccessToken(member.nonNullId.toString(), Date())
            .let { ResponseEntity.ok(TokenInfo(accessToken = it, refreshToken = null)) }
    }
}
