package com.cloudpi.cloudpi_backend.user.domain.repositories;

import com.cloudpi.cloudpi_backend.user.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public
interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUserDetails_Nickname(String nickname);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("""
            DELETE UserEntity u
            WHERE u.id = :id
            """)
    void deleteUserById(Long id);

}