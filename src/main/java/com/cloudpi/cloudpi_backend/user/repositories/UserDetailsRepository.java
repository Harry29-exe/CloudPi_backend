package com.cloudpi.cloudpi_backend.user.repositories;

import com.cloudpi.cloudpi_backend.user.entities.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long> {

    Optional<UserDetailsEntity> findByUser_Username(String username);

    Optional<UserDetailsEntity> findByNickname(String nickname);

    Optional<UserDetailsEntity> findByEmail(String email);

}
