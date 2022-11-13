package com.timecapsule.api.dto

sealed class MemberResponse(
    val nickname: String,
)

data class ValidateNicknameResponse(
    val available: Boolean,
)
