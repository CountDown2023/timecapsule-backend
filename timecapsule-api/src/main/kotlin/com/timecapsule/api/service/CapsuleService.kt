package com.timecapsule.api.service

import com.timecapsule.api.dto.CreateCapsuleRequest
import com.timecapsule.database.entity.Capsule
import com.timecapsule.database.repository.CapsuleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class CapsuleService(
        private val capsuleRepository: CapsuleRepository,
) {
    fun getCapsuleDays(capsuleId: Long): Long = Duration.between(getCapsule(capsuleId).createdAt, LocalDateTime.now()).toDays()

    fun getCapsule(capsuleId: Long): Capsule = capsuleRepository.findByIdOrNull(capsuleId)
        ?: throw NoSuchElementException()

    fun getCapsuleCreatedBy(memberId: Long): List<Capsule> = capsuleRepository.findByMemberId(memberId)

    fun createCapsule(request: CreateCapsuleRequest, memberId: Long): Capsule = capsuleRepository.save(request.toCapsule(memberId))
}
