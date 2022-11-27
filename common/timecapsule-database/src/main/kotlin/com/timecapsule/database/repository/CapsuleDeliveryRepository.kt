package com.timecapsule.database.repository

import com.timecapsule.database.entity.CapsuleDelivery
import org.springframework.data.jpa.repository.JpaRepository

interface CapsuleDeliveryRepository : JpaRepository<CapsuleDelivery, Long> {
    fun findTop1ByMemberIdNotOrderByCountDesc(memberId: Long): CapsuleDelivery?
}
