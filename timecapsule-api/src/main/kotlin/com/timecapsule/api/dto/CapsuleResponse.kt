package com.timecapsule.api.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.timecapsule.database.entity.Capsule

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class CapsuleResponse(
    val capsuleId: Long,
    val memberId: Long,
    val name: String,
    val bottleChoice: Int,
    val bottleColor: Int,
    val letterPaper: Int,
    val letterLine: Int,
    val content: String,
    val goals: List<String>
) {

    companion object {
        fun of(capsule: Capsule): CapsuleResponse = CapsuleResponse(
            capsuleId = capsule.nonNullId,
            memberId = capsule.memberId,
            name = capsule.name,
            bottleChoice = capsule.bottleChoice,
            bottleColor = capsule.bottleColor,
            letterPaper = capsule.letterPaper,
            letterLine = capsule.letterLine,
            content = capsule.content,
            goals = capsule.goals
        )
    }
}
