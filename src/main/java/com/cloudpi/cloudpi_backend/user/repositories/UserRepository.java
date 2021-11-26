package com.cloudpi.cloudpi_backend.user.repositories;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public
interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUserDetails_Nickname(String nickname);

    void deleteByUsername(String username);
}