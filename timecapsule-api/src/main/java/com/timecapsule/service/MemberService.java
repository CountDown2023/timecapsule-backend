package com.timecapsule.service;

import com.timecapsule.infra.entity.Member;
import com.timecapsule.infra.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member signUp(String nickName, String password) {
        return memberRepository.save(
            Member.of(
                nickName,
                passwordEncoder.encode(password)
            )
        );
    }
}
