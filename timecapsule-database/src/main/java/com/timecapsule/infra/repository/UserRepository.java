package com.timecapsule.infra.repository;

import com.timecapsule.infra.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
