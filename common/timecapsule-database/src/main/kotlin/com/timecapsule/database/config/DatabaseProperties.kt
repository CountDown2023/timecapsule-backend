package com.timecapsule.database.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.datasource")
data class DatabaseProperties (
    val username: String,
    val password: String,
    val driverClassName: String,
    val jpaHibernateDdlAuto: String,
    val url: String,
    val maximumPoolSize: Int,
    val connectionTimeout: Long,
)
