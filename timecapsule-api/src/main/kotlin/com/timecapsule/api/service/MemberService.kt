package com.timecapsule.api.service

import com.timecapsule.api.util.PasswordEncoder
import com.timecapsule.database.entity.Member
import com.timecapsule.database.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun signUp(nickName: String, password: String, email: String): Member =
        memberRepository.save(
            Member(nickName = nickName, password = passwordEncoder.encode(password), email = email)
        )

}
