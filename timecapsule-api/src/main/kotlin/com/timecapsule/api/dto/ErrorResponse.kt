package com.timecapsule.api.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

sealed class ErrorResponse(
    private val message: String,
    private val code: String,
    private val status: Int,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class InvalidParamErrorResponse(
    private val invalidParams: List<String>,
    private val message: String,
    private val code: String,
    private val status: Int,
): ErrorResponse(message, code, status)
