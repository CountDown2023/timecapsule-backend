package com.timecapsule.infra.repository;

import com.timecapsule.infra.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
