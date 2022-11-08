package com.timecapsule.api.controller.advice

import com.timecapsule.api.dto.InvalidParamErrorResponse
import com.timecapsule.model.exception.ExceptionCode
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.net.http.HttpRequest

private val log = KotlinLogging.logger {}

@ControllerAdvice
class TimecapsuleApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(
        ex: MethodArgumentNotValidException,
        request: HttpRequest
    ): ResponseEntity<InvalidParamErrorResponse> {
        log.error("invalid parameters. uri: ${request.uri()}", ex)

        return ex.bindingResult.fieldErrors
            .map { it.field }
            .let {
                InvalidParamErrorResponse(
                    message = ex.message,
                    code = ExceptionCode.INVALID_REQUEST.name,
                    status = HttpStatus.BAD_REQUEST.value(),
                    invalidParams = it
                )
            }.let { ResponseEntity<InvalidParamErrorResponse>(it, HttpStatus.BAD_REQUEST) }
    }
}
