package com.timecapsule.api.service

import com.timecapsule.database.entity.RefreshToken
import com.timecapsule.database.repository.RefreshTokenRepository
import org.springframework.stereotype.Service

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
) {

    fun getRefreshToken(memberId: Long): RefreshToken? = refreshTokenRepository.findByMemberId(memberId)

    fun updateToken(entity: RefreshToken, newTokenString: String) {
        entity.updateToken(newTokenString).also {
            refreshTokenRepository.save(it)
        }
    }

    private fun RefreshToken.updateToken(newToken: String): RefreshToken {
        this.token = newToken
        return this
    }
}
