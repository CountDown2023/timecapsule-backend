package com.timecapsule.api.service

import com.timecapsule.database.entity.RefreshToken
import com.timecapsule.database.repository.RefreshTokenRepository
import org.springframework.stereotype.Service

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    fun getRefreshToken(userId: Long): RefreshToken? = refreshTokenRepository.findByUserId(userId)

    fun updateToken(entity: RefreshToken, newTokenString: String): RefreshToken =
        entity.apply {
            token = newTokenString
        }.let {
            refreshTokenRepository.save(it)
        }

}
