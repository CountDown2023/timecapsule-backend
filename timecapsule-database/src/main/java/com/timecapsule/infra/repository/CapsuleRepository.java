package com.timecapsule.infra.repository;

import com.timecapsule.infra.entity.Capsule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapsuleRepository extends JpaRepository<Capsule, Long> {

}
