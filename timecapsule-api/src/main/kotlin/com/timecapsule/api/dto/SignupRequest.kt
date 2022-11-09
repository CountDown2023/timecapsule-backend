package com.timecapsule.api.dto

import javax.validation.constraints.Email

data class SignupRequest(
    val nickname: String,
    val password: String,
    @field:Email
    val email: String,
)
