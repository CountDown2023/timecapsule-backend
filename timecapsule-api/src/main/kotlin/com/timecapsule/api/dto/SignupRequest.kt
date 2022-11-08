package com.timecapsule.api.dto

data class SignupRequest(
    val username: String,
    val password: String,
    val email: String,
)
