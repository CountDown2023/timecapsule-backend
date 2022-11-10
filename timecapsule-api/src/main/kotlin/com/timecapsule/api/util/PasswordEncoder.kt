package com.timecapsule.api.util

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoder {

    fun encode(str: String): String = encoder.encode(str)

    companion object {
        private val encoder = BCryptPasswordEncoder()
    }
}
