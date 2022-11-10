package com.timecapsule.api.controller

import com.timecapsule.api.dto.SignupRequest
import com.timecapsule.api.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/api/sign-up")
    fun signUp(@Validated @RequestBody request: SignupRequest): ResponseEntity<Void> {
        memberService.signUp(request.nickname, request.password, request.email)
        return ResponseEntity.created(URI.create("/api/login")).build()
    }
}
