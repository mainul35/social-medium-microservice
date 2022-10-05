package com.mainul35.auth.repositories;

import com.mainul35.auth.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface RoleRepository extends JpaRepository<RoleEntity, String> {

    Optional<RoleEntity> findById(String id);

    Optional<RoleEntity> findByRole(String role);
}
