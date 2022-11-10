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

    fun generateTokens(userId: Long): TokenInfo {
        val now = Date()
        val accessToken = generateAccessToken(userId.toString(), now)
        val refreshToken = generateRefreshToken(userId.toString(), now)

        return TokenInfo(accessToken, refreshToken)
    }

    fun generateAccessToken(userId: String, now: Date): String =
        Jwts.builder()
            .setClaims(Jwts.claims().setSubject(userId))
            .setId(userId)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + ACCESS_TOKEN_VALID_TIME))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()

    fun generateRefreshToken(userId:String, now: Date): String =
        Jwts.builder()
            .setId(userId)
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

    fun renewRefreshToken(userPk: Long): TokenInfo? =
        refreshTokenService.getRefreshToken(userPk)?.let { token ->
            try {
                token.let {
                    validateToken(it.token.substring(7))
                    TokenInfo(accessToken = null, refreshToken = it.token)
                }
            } catch (ex: ExpiredJwtException) {
                token.let {
                    refreshTokenService.updateToken(it, generateRefreshToken(userPk.toString(), Date()))
                    TokenInfo(accessToken = null, refreshToken = it.token)
                }
            } catch (ex: Exception) {
                log.error("refreshToken 재발급 중 에러 발생", ex)
                throw ex
            }
        }

    // token으로 사용자 id 조회
    fun getUserIdFromToken(token: String): Long = getClaimFromToken(token, Claims::getId).toLong()

    // 가져온 claims를 resolve
    private fun <T> getClaimFromToken(token: String, claimsResolver: (Claims) -> T): T = claimsResolver(getAllClaimsFromToken(token))

    // token의 claims를 조회
    private fun getAllClaimsFromToken(token: String): Claims =
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body


    companion object {
        private const val ACCESS_TOKEN_VALID_TIME: Long = 1000 * 60 * 5 // 5분
        private const val REFRESH_TOKEN_VALID_TIME: Long = 1000 * 60 * 60 * 10 // 10시간
    }

}
