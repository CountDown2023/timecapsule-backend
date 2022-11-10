package com.timecapsule.api.filter

import com.timecapsule.api.exception.JwtAuthenticationException
import com.timecapsule.api.service.JwtAuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(
    private val jwtAuthenticationProvider: JwtAuthenticationProvider,
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val accessToken = request.getHeader(AUTHORIZATION_HEADER)?.substring(7) ?: throw JwtAuthenticationException(msg= "NO AUTHORIZATION HEADER")

        if (jwtAuthenticationProvider.validateToken(accessToken)) {
            jwtAuthenticationProvider.getUserIdFromToken(accessToken).let {
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(it, "")
                jwtAuthenticationProvider.renewRefreshToken(it)
            }
        }

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return EXCLUDE_URL.contains(request.servletPath)
    }

    companion object {
        private val EXCLUDE_URL: Set<String> = setOf("/api/ping", "/api/login", "/api/sign-up")
        private val AUTHORIZATION_HEADER: String = "Authorization"
    }
}
