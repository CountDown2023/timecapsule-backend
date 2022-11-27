package com.timecapsule.api.service

import com.timecapsule.api.dto.CreateCapsuleRequest
import com.timecapsule.database.entity.Capsule
import com.timecapsule.database.entity.CapsuleDelivery
import com.timecapsule.database.repository.CapsuleDeliveryRepository
import com.timecapsule.database.repository.CapsuleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import javax.persistence.NonUniqueResultException

@Service
class CapsuleService(
    private val capsuleRepository: CapsuleRepository,
    private val capsuleDeliveryRepository: CapsuleDeliveryRepository,
) {
    fun getCapsuleDays(capsuleId: Long): Long = Duration.between(getCapsule(capsuleId).createdAt, LocalDateTime.now()).toDays()

    fun getCapsule(capsuleId: Long): Capsule = capsuleRepository.findByIdOrNull(capsuleId)
        ?: throw NoSuchElementException()

    fun getCapsuleCreatedBy(memberId: Long): List<Capsule> = capsuleRepository.findByMemberId(memberId)

    fun createCapsule(request: CreateCapsuleRequest, memberId: Long): Capsule {
        if (getCapsuleCreatedBy(memberId).isNotEmpty()) {
            throw NonUniqueResultException()
        }

        return capsuleRepository.save(request.toCapsule(memberId)).also {
            capsuleDeliveryRepository.save(CapsuleDelivery(capsuleId = it.nonNullId, memberId = memberId, count = 0))
        }
    }

    fun getFloatingCapsule(receiverId: Long): Capsule =
        capsuleDeliveryRepository.findTop1ByMemberIdNotOrderByCountDesc(receiverId)
            ?.let {
                it.increaseDeliveryCount()
                capsuleDeliveryRepository.save(it)
                getCapsule(it.nonNullId)
            } ?: throw NoSuchElementException()
}
