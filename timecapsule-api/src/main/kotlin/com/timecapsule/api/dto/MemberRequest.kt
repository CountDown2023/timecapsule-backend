package com.timecapsule.api.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
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

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class RefreshRequest(
    val refreshToken: String,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String,
)
