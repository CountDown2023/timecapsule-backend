package com.timecapsule.model.exception

enum class ExceptionCode(
    val message: String,
) {
    INVALID_REQUEST("잘못된 요청입니다"),
    DUPLICATED_USER("중복된 닉네임 혹은 이메일이 존재합니다."),
}
