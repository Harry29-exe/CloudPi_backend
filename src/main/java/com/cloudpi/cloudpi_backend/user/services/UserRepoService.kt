package com.cloudpi.cloudpi_backend.user.services

import com.cloudpi.cloudpi_backend.user.dto.UserDto
import java.util.*

interface UserRepoService {

    fun getAllUsers(): List<UserDto>

    fun getUser(username: String): Optional<UserDto>
}