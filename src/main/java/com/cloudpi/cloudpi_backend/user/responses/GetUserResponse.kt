package com.cloudpi.cloudpi_backend.user.responses

import com.cloudpi.cloudpi_backend.security.AccountType

data class GetUserResponse(
    val username: String,
    val nickname: String,
    val userType: AccountType,
    val permissions: String
)