package com.coconut.ubo.repository.user;

import com.coconut.ubo.domain.user.College;
import com.coconut.ubo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByLoginId(String loginId);
}
