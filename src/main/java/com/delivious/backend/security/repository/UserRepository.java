package com.delivious.backend.security.repository;

import java.util.Optional;
import com.delivious.backend.security.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Long> {
  Optional<UserInfo> findByEmail(String email);
}
