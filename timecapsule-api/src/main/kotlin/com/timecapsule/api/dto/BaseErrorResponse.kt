package com.timecapsule.api.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

sealed class BaseErrorResponse(
    open val message: String,
    open val code: String,
    open val status: Int,
)

data class ErrorResponse(
    override val message: String,
    override val code: String,
    override val status: Int,
): BaseErrorResponse(message, code, status)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class InvalidParamErrorResponse(
    val invalidParams: List<String>,
    override val message: String,
    override val code: String,
    override val status: Int,
): BaseErrorResponse(message, code, status)
