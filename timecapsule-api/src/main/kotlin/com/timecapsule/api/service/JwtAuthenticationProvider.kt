package com.timecapsule.api.service

import com.timecapsule.api.dto.TokenInfo
import com.timecapsule.api.exception.JwtAuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

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

    fun generateTokens(memberId: Long): TokenInfo {
        val now = Date()
        val accessToken = generateAccessToken(memberId.toString(), now)
        val refreshToken = generateRefreshToken(memberId.toString(), now)

        return TokenInfo(accessToken, refreshToken)
    }

    fun generateAccessToken(memberId: String, now: Date): String =
        PREFIX + Jwts.builder()
            .setClaims(Jwts.claims().setSubject(memberId))
            .setId(memberId)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + ACCESS_TOKEN_VALID_TIME))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()

    fun generateRefreshToken(memberId:String, now: Date): String =
        Jwts.builder()
            .setId(memberId)
            .setExpiration(Date(now.time + REFRESH_TOKEN_VALID_TIME))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return true
        } catch (ex: Exception) {
            log.error("token validation 실패")
            throw JwtAuthenticationException("token validation 실패")
        }
    }

    fun renewRefreshToken(memberId: Long): TokenInfo? =
        refreshTokenService.getRefreshToken(memberId)?.let { token ->
            try {
                token.let {
                    validateToken(it.token.substring(7))
                    TokenInfo(accessToken = null, refreshToken = it.token)
                }
            } catch (ex: ExpiredJwtException) {
                token.let {
                    refreshTokenService.updateToken(it, generateRefreshToken(memberId.toString(), Date()))
                    TokenInfo(accessToken = null, refreshToken = it.token)
                }
            } catch (ex: Exception) {
                log.error("refreshToken 재발급 중 에러 발생", ex)
                throw ex
            }
        }

    // token으로 사용자 id 조회
    fun getMemberIdFromToken(token: String): Long = getClaimFromToken(token, Claims::getId).toLong()

    // 가져온 claims를 resolve
    private fun <T> getClaimFromToken(token: String, claimsResolver: (Claims) -> T): T = claimsResolver(getAllClaimsFromToken(token))

    // token의 claims를 조회
    private fun getAllClaimsFromToken(token: String): Claims =
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body

    companion object {
        private const val ACCESS_TOKEN_VALID_TIME: Long = 1000 * 60 * 5 // 5분
        private const val REFRESH_TOKEN_VALID_TIME: Long = 1000 * 60 * 60 * 10 // 10시간
        private const val PREFIX: String = "Bearer "
    }

}
