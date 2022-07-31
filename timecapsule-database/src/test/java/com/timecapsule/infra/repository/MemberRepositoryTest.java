package com.timecapsule.infra.repository;

import static org.assertj.core.api.Assertions.assertThat;
import com.timecapsule.infra.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void findById() {
        // given
        var entity = Member.of("뀨뀨찬찬", "123123", "email@naver.com");

        // when
        var saved = memberRepository.save(entity);

        // then
        assertThat(saved.getId()).isNotNull();
        var found = memberRepository.findById(saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.get().getId()).isNotNull();
        assertThat(found.get().getId()).isEqualTo(saved.getId());

        assertThat(found.get().getNickName()).isEqualTo(saved.getNickName());
    }
}
