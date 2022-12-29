package com.timecapsule.api.controller.advice

import com.timecapsule.api.dto.ErrorResponse
import com.timecapsule.api.dto.InvalidParamErrorResponse
import com.timecapsule.api.exception.JwtAuthenticationException
import com.timecapsule.model.exception.ExceptionCode
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.persistence.NonUniqueResultException
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
    ): ResponseEntity<ErrorResponse> {
        log.error("duplicate entry. uri: ${request.requestURI}", ex)

        return ResponseEntity<ErrorResponse>(
            ErrorResponse(
                message = ex.message ?: ExceptionCode.DUPLICATED_ENTITY.message,
                code = ExceptionCode.DUPLICATED_ENTITY.name,
                status = HttpStatus.BAD_REQUEST.value(),
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handle(
        ex: NoSuchElementException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.error("entry not found. uri: ${request.requestURI}", ex)

        return ResponseEntity<ErrorResponse>(
            ErrorResponse(
                message = ex.message ?: ExceptionCode.ENTRY_NOT_FOUND.message,
                code = ExceptionCode.ENTRY_NOT_FOUND.name,
                status = HttpStatus.NOT_FOUND.value(),
            ),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(JwtAuthenticationException::class)
    fun handle(
        ex: JwtAuthenticationException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.error("jwt authentication error. uri: ${request.requestURI}", ex)

        return ResponseEntity<ErrorResponse>(
            ErrorResponse(
                message = ex.msg,
                code = ExceptionCode.AUTHENTICATION_FAILED.name,
                status = HttpStatus.UNAUTHORIZED.value()
            ),
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handle(
        ex: BadCredentialsException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.error("로그인 실패, uri: ${request.requestURI}", ex)

        return ResponseEntity<ErrorResponse>(
            ErrorResponse(
                message = ex.message ?: ExceptionCode.AUTHENTICATION_FAILED.message,
                code = ExceptionCode.AUTHENTICATION_FAILED.name,
                status = HttpStatus.UNAUTHORIZED.value()
            ),
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(NonUniqueResultException::class)
    fun handle(
        ex: NonUniqueResultException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        log.error("중복된 데이터가 존재합니다. uri: ${request.requestURI}", ex)

        return ResponseEntity<ErrorResponse>(
            ErrorResponse(
                message = ex.message ?: ExceptionCode.DUPLICATED_ENTITY.message,
                code = ExceptionCode.DUPLICATED_ENTITY.name,
                status = HttpStatus.CONFLICT.value(),
            ),
            HttpStatus.CONFLICT
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handle(
        ex: java.lang.IllegalArgumentException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        log.error("잘못된 요청입니다. uri: ${request.requestURI}", ex)

        return ResponseEntity<ErrorResponse>(
            ErrorResponse(
                message = ex.message ?: ExceptionCode.INVALID_REQUEST.message,
                code = ExceptionCode.INVALID_REQUEST.message,
                status = HttpStatus.BAD_REQUEST.value(),
            ),
            HttpStatus.BAD_REQUEST
        )
    }

}
