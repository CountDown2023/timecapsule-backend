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
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
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
        memberService.signUp(request.nickname, request.password)
        return ResponseEntity.created(URI.create("/api/login")).build()
    }

    @Operation
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK",
            content = [Content(schema = Schema(implementation = ValidateNicknameResponse::class))])
    ])
    @GetMapping("/api/member/validate-nickname")
    fun validateNickname(@RequestParam nickname: String): ResponseEntity<ValidateNicknameResponse> =
        ResponseEntity<ValidateNicknameResponse>(
            ValidateNicknameResponse(
                available = memberService.checkAvailableNickname(nickname)
            ),
            HttpStatus.OK
        )

    @Operation(summary = "로그인 요청")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK",
            content = [Content(schema = Schema(implementation = TokenInfo::class))]),
        ApiResponse(responseCode = "401", description = "패스워드 불일치",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", description = "해당 사용자가 없음",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    ])
    @PostMapping("/api/member/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<TokenInfo> {
        val member = memberService.getMember(request.nickname)
        if (!memberService.validatePassword(request.password, member.password)) {
            throw BadCredentialsException("password가 일치하지 않습니다.")
        }
        return ResponseEntity.ok(jwtAuthenticationProvider.generateTokens(member.nonNullId))
    }

    @Operation(summary = "비밀번호 수정")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "401", description = "패스워드 불일치",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", description = "해당 사용자가 없음",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    ])
    @PutMapping("/api/member/password")
    fun changePassword(
        @RequestHeader(name = "Authorization") accessToken: String,
        @RequestBody request: ChangePasswordRequest,
    ): ResponseEntity<Void> {
        val memberId = jwtAuthenticationProvider.getMemberIdFromToken(accessToken.substring(7))
        val member = memberService.getMember(memberId)

        // old == new 인지는 클라에서 검증해서 보내야할듯
        if (!memberService.validatePassword(request.oldPassword, member.password)) {
            throw BadCredentialsException("password가 일치하지 않습니다.")
        }
        memberService.changePassword(member, request.newPassword)
        return ResponseEntity.ok(null)
    }

    @Operation(summary = "Access Token 재발급 요청")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK",
            content = [Content(schema = Schema(implementation = TokenInfo::class))]),
        ApiResponse(responseCode = "401", description = "Refresh Token 검증 실패",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]),
        ApiResponse(responseCode = "404", description = "해당 사용자가 없음",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))])
    ])
    @PostMapping("/api/member/refresh-access-token")
    fun refreshAccessToken(@RequestBody request: RefreshRequest): ResponseEntity<TokenInfo> {
        jwtAuthenticationProvider.validateToken(request.refreshToken)
        val memberId = jwtAuthenticationProvider.getMemberIdFromToken(request.refreshToken)
        val member = memberService.getMember(memberId)

        return jwtAuthenticationProvider.generateAccessToken(member.nonNullId.toString(), Date())
            .let { ResponseEntity.ok(TokenInfo(accessToken = it, refreshToken = null)) }
    }
}
