package com.timecapsule.infra.repository;

import com.timecapsule.infra.entity.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleEntityRepository extends JpaRepository<SampleEntity, Long> {
}
