package com.timecapsule.database.repository

import com.timecapsule.database.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long>
