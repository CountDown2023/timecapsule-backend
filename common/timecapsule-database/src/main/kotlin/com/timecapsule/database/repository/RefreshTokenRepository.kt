package com.timecapsule.database.repository

import com.timecapsule.database.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository: JpaRepository<RefreshToken, Long> {
    fun findByUserId(userId: Long): RefreshToken?
}
