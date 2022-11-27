package com.timecapsule.api.dto

import javax.validation.constraints.Email

sealed class MemberRequest(
    open val nickname: String,
)

data class SignupRequest(
    override val nickname: String,
    val password: String,
    @field:Email
    val email: String,
): MemberRequest(nickname)

data class LoginRequest(
    override val nickname: String,
    val password: String,
): MemberRequest(nickname)

data class RefreshRequest(
    val refreshToken: String,
)
