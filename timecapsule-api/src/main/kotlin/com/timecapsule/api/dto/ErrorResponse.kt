package com.timecapsule.api.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

sealed class ErrorResponse(
    open val message: String,
    open val code: String,
    open val status: Int,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class InvalidParamErrorResponse(
    val invalidParams: List<String>,
    override val message: String,
    override val code: String,
    override val status: Int,
): ErrorResponse(message, code, status)

data class DuplicateEntryErrorResponse(
    override val message: String,
    override val code: String,
    override val status: Int,
): ErrorResponse(message, code, status)
