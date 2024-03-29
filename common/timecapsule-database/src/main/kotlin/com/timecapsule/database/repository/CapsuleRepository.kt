package com.timecapsule.database.repository

import com.timecapsule.database.entity.Capsule
import org.springframework.data.jpa.repository.JpaRepository

interface CapsuleRepository : JpaRepository<Capsule, Long> {
    fun findByMemberId(memberId: Long): List<Capsule>
}
