package com.delivious.backend.domain.users.repository;


<<<<<<< Updated upstream

import com.delivious.backend.domain.users.entity.UserEntity;
=======
import com.delivious.backend.domain.users.entity.UserEntity;

>>>>>>> Stashed changes
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<UserEntity> findOneWithAuthoritiesByUsername(String username);
}
