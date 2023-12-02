package com.coconut.ubo.repository.user;

import com.coconut.ubo.domain.user.College;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollegeRepository extends JpaRepository<College, String> {
    Optional<College> findByName(String name);
}
