package com.timecapsule.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TimecapsuleApiApplication

fun main(args: Array<String>) {
    runApplication<TimecapsuleApiApplication>(*args)
}
