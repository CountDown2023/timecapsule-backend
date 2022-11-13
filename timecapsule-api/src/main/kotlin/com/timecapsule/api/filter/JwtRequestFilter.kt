package com.timecapsule.api.filter

import com.timecapsule.api.service.JwtAuthenticationProvider
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(
    private val jwtAuthenticationProvider: JwtAuthenticationProvider,
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val accessToken = request.getHeader(AUTHORIZATION_HEADER)?.substring(7)
            ?: return response.sendError(HttpStatus.UNAUTHORIZED.value(), "NO AUTHORIZATION HEADER")

        if (jwtAuthenticationProvider.validateToken(accessToken)) {
            jwtAuthenticationProvider.getMemberIdFromToken(accessToken).let {
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(it, "")
                jwtAuthenticationProvider.renewRefreshToken(it)
            }
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val pathMatcher = AntPathMatcher()
        return EXCLUDE_URL.contains(request.servletPath)
            || SWAGGER_PATH_PATTERNS.any { pathMatcher.match(it, request.servletPath) }
    }

    companion object {
        private val EXCLUDE_URL: Set<String> = setOf("/api/ping", "/api/member/login", "/api/member/sign-up")
        private val SWAGGER_PATH_PATTERNS: Set<String> = setOf("/swagger-ui/**", "/v3/api-docs/**")
        private const val AUTHORIZATION_HEADER: String = "Authorization"
    }
}
