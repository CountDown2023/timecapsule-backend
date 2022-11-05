package com.timecapsule.api.service

import com.timecapsule.database.entity.Member
import com.timecapsule.database.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun signUp(nickName: String, password: String, email: String): Member =
        memberRepository.save(
            Member(nickName = nickName, password = password, email = email)
        )

}
