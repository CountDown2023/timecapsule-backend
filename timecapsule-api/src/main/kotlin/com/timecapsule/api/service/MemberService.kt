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

    fun signUp(nickname: String, password: String, email: String): Member =
        memberRepository.save(
            Member(nickname = nickname, password = passwordEncoder.encode(password), email = email)
        )

    fun getMember(nickname: String): Member =
        memberRepository.findByNickname(nickname) ?: throw NoSuchElementException("${nickname}에 해당하는 user가 없습니다.")
}
