package com.cloudpi.cloudpi_backend.user.responses

import com.cloudpi.cloudpi_backend.security.AccountType

data class GetUsersResponse(
    val username: String,
    val nickname: String,
    val locked: String,
    val userRole: AccountType,
    val permissions: List<String>
)
