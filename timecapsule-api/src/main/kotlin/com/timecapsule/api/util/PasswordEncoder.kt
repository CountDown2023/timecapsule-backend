package com.timecapsule.api.util

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoder {

    fun encode(str: String): String = encoder.encode(str)

    fun matches(raw: String, encoded: String): Boolean = encoder.matches(raw, encoded)

    companion object {
        private val encoder = BCryptPasswordEncoder()
    }
}
