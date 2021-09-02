package com.cloudpi.cloudpi_backend.user.requests

data class PostUserRequest(
    val username: String,
    val nickname: String,
    val password: String
)