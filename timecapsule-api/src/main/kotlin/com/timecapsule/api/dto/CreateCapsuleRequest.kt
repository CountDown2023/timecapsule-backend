package com.timecapsule.api.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.timecapsule.database.entity.Capsule


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateCapsuleRequest(
    val name: String,
    val bottleChoice: Int,
    val bottleColor: Int,
    val letterPaper: Int,
    val letterLine: Int,
    val content: String,
    val goals: List<String>,
) {
    fun toCapsule(memberId: Long): Capsule =
        Capsule(memberId = memberId, name = name, bottleColor = bottleColor, bottleChoice = bottleChoice,
            letterLine = letterLine, letterPaper = letterPaper, content = content, goals = goals)
}
