package com.timecapsule.database.repository

import com.timecapsule.database.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {
    fun findByNickname(nickname: String): Member?
    fun findMemberById(id: Long): Member?
    fun existsByNickname(nickname: String): Boolean
}
