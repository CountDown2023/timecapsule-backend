package com.timecapsule.api.service

import com.timecapsule.api.dto.TokenInfo
import com.timecapsule.api.service.RefreshTokenService
import io.jsonwebtoken.*
import mu.KotlinLogging
import org.springframework.stereotype.Component

import org.springframework.beans.factory.annotation.Value
import java.util.*
import javax.annotation.PostConstruct
import io.jsonwebtoken.Jwts

import io.jsonwebtoken.Claims

private val log = KotlinLogging.logger {}

@Component
class JwtAuthenticationProvider(
    private val refreshTokenService: RefreshTokenService,
    @Value("\${spring.jwt.secret}")
    private var secretKey: String,
) {

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    // token으로 사용자 id 조회
    fun getUserIdFromToken(token: String): Long = getClaimFromToken(token, Claims::getId).toLong()

    // 가져온 claims를 resolve
    fun <T> getClaimFromToken(token: String, claimsResolver: (Claims) -> T): T = claimsResolver(getAllClaimsFromToken(token))

    // token의 claims를 조회
    private fun getAllClaimsFromToken(token: String): Claims =
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body


    fun validateToken(token: String): Boolean {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
        return true
    }

    fun renewRefreshToken(userPk: Long): TokenInfo? =
        refreshTokenService.getRefreshToken(userPk)?.let { token ->
            try {
                token.let {
                    validateToken(it.token.substring(7))
                    TokenInfo(accessToken = null, refreshToken = it.token)
                }
            } catch (ex: ExpiredJwtException) {
                token.let {
                    val newToken = refreshTokenService.updateToken(it, generateRefreshToken(userPk.toString(), Date()))
                    TokenInfo(accessToken = null, refreshToken = newToken.token)
                }
            } catch (ex: Exception) {
                log.error("refreshToken 재발급 중 에러 발생", ex)
                throw ex
            }
        }

    private fun generateAccessToken(userId: String, now: Date): String =
        Jwts.builder()
            .setClaims(Jwts.claims().setSubject(userId))
            .setId(userId)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + ACCESS_TOKEN_VALID_TIME))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()

    private fun generateRefreshToken(userId:String, now: Date): String =
        Jwts.builder()
            .setId(userId)
            .setExpiration(Date(now.time + REFRESH_TOKEN_VALID_TIME))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()

    companion object {
        private const val ACCESS_TOKEN_VALID_TIME: Long = 1000 * 60 * 5 // 5분
        private const val REFRESH_TOKEN_VALID_TIME: Long = 1000 * 60 * 60 * 10 // 10시간
    }

}
