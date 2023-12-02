package com.coconut.ubo.repository.user;

import com.coconut.ubo.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepositroy extends JpaRepository<Likes, Long> {
}
