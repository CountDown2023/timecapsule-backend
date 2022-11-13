package com.timecapsule.api.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun memberApi(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("member-apis")
            .pathsToMatch("/api/member/**")
            .build()

    @Bean
    fun capsuleApi(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("capsule-apis")
            .pathsToMatch("/api/capsule/**")
            .build()

    @Bean
    fun springShopOpenAPI(): OpenAPI {
        val info = Info().title("TimeCapsule API")
            .description("TimeCapsule 프로젝트 API 명세서입니다.")
            .version("v0.0.1")
        val jwtSchemeName = "jwtAuth"
        val securityRequirement = SecurityRequirement().addList(jwtSchemeName)
        val components = Components().addSecuritySchemes(jwtSchemeName, SecurityScheme()
            .name(jwtSchemeName)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT"))

        return OpenAPI().info(info)
            .addSecurityItem(securityRequirement)
            .components(components)
    }

}
