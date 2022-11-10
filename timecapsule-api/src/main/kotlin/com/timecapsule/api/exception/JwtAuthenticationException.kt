package com.timecapsule.api.exception

class JwtAuthenticationException(
    val msg: String,
    override val cause: Throwable? = null,
): RuntimeException(msg, cause)
