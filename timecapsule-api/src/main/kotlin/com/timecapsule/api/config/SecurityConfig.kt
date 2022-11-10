package com.timecapsule.api.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        // 우선 disabled
        http.csrf().disable()
            .authorizeHttpRequests { auth ->
                auth.antMatchers("/api/ping", "/api/login", "/api/sign-up").permitAll()
                    .anyRequest().authenticated()
            }.build()

}
