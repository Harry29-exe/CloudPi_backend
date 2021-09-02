package com.cloudpi.cloudpi_backend.user.repositories

import com.cloudpi.cloudpi_backend.user.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, String> {

    fun findByUsername(username: String): Optional<UserEntity>

//    fun findByEmail(email: String): Optional<UserEntity>
}