package com.timecapsule.api.service

import com.timecapsule.api.util.PasswordEncoder
import com.timecapsule.database.entity.Member
import com.timecapsule.database.repository.MemberRepository
import org.springframework.security.authentication.BadCredentialsException
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

    fun getMember(memberId: Long): Member =
        memberRepository.findMemberById(memberId)
            ?: throw NoSuchElementException("${memberId}에 해당하는 member가 없습니다.")

    fun getMember(nickname: String): Member =
        memberRepository.findByNickname(nickname)
            ?: throw NoSuchElementException("${nickname}에 해당하는 member가 없습니다.")

    fun validatePassword(rawPassword: String, encodedPassword: String): Boolean =
        passwordEncoder.matches(rawPassword, encodedPassword)

    fun changePassword(member: Member, newPassword: String): Member =
        memberRepository.save(member.updatePassword(newPassword))

    private fun Member.updatePassword(newPassword: String): Member =
        this.apply {
            password = passwordEncoder.encode(newPassword)
        }
}
