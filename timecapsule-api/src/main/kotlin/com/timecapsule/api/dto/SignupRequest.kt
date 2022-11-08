package com.timecapsule.api.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

data class SignupRequest(
    @NotNull
    val username: String,
    @NotNull
    val password: String,
    @Email(message = "email 형식이어야 합니다.")
    @NotNull
    val email: String,
)
