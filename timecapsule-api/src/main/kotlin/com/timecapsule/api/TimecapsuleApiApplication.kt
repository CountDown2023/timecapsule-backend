package com.timecapsule.api

import com.timecapsule.database.config.DatabaseConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@Import(DatabaseConfig::class)
@SpringBootApplication
class TimecapsuleApiApplication

fun main(args: Array<String>) {
    runApplication<TimecapsuleApiApplication>(*args)
}
