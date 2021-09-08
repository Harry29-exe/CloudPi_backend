package com.cloudpi.cloudpi_backend.user.repositories;

import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public
interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

//    fun findByEmail(email: String): Optional<UserEntity>
}