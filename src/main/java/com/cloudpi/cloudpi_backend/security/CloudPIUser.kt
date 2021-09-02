package com.cloudpi.cloudpi_backend.security

import com.cloudpi.cloudpi_backend.user.entities.UserEntity
import com.cloudpi.cloudpi_backend.user.entities.UserPermissionEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CloudPIUser(
    private val username: String,
    private val password: String,
    private val locked: Boolean,
    userType: AccountType,
    permissions: List<GrantedAuthority>,
) : UserDetails {
    private val permissions: MutableList<GrantedAuthority>

    constructor(userEntity: UserEntity) :
            this(userEntity.username, userEntity.password, userEntity.locked,
                userEntity.accountType,
                userEntity.permissions.map { p: UserPermissionEntity ->
                    SimpleGrantedAuthority(p.permission)
                }
            )

    init {
        this.permissions = ArrayList(permissions.size + 1)
        this.permissions.addAll(permissions)
        this.permissions.add(SimpleGrantedAuthority(userType.name))
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return permissions
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return false
    }

    override fun isAccountNonLocked(): Boolean {
        return !locked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return false
    }

    override fun isEnabled(): Boolean {
        return true
    }

}