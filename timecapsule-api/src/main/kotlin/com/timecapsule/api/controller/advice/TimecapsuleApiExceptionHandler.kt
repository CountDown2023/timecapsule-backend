package com.timecapsule.api.controller.advice

import com.timecapsule.api.dto.DuplicateEntryErrorResponse
import com.timecapsule.api.dto.InvalidParamErrorResponse
import com.timecapsule.model.exception.ExceptionCode
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest

private val log = KotlinLogging.logger {}

@ControllerAdvice
class TimecapsuleApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<InvalidParamErrorResponse> {
        log.error("invalid request ${request.requestURI}", ex)

        return ResponseEntity<InvalidParamErrorResponse>(
            InvalidParamErrorResponse(
                invalidParams = ex.bindingResult.fieldErrors.map { it.field },
                message = ex.bindingResult.fieldErrors.first().defaultMessage ?: ExceptionCode.INVALID_REQUEST.message,
                code = ExceptionCode.INVALID_REQUEST.name,
                status = HttpStatus.BAD_REQUEST.value()
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handle(
        ex: DataIntegrityViolationException,
        request: HttpServletRequest
    ): ResponseEntity<DuplicateEntryErrorResponse> {
        log.error("duplicate entry. uri: ${request.requestURI}", ex)

        return ResponseEntity<DuplicateEntryErrorResponse>(
            DuplicateEntryErrorResponse(
                message = ExceptionCode.DUPLICATED_USER.message,
                code = ExceptionCode.DUPLICATED_USER.name,
                status = HttpStatus.BAD_REQUEST.value(),
            ),
            HttpStatus.BAD_REQUEST
        )
    }
}
