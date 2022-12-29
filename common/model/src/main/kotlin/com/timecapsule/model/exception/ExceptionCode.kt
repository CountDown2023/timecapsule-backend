package com.timecapsule.model.exception

enum class ExceptionCode(
    val message: String,
) {
    INVALID_REQUEST("잘못된 요청입니다"),
    DUPLICATED_ENTITY("중복된 데이터가 존재합니다."),
    AUTHENTICATION_FAILED("인증에 실패했습니다."),
    ENTRY_NOT_FOUND("조회하는 항목이 존재하지 않습니다."),

}
