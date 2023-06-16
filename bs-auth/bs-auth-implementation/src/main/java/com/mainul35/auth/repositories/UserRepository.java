package com.mainul35.auth.repositories;

import com.mainul35.auth.models.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @EntityGraph(attributePaths = "roles")
        // Handles lazy loading
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByJwtToken(String jwtToken);
}
