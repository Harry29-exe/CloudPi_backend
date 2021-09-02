package com.cloudpi.cloudpi_backend.user.services

import com.cloudpi.cloudpi_backend.user.dto.UserDto
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserRepoServiceImp : UserRepoService {

    override fun getAllUsers(): List<UserDto> {
        TODO("Not yet implemented")
    }

    override fun getUser(username: String): Optional<UserDto> {
        TODO("Not yet implemented")
    }
}