package com.cloudpi.cloudpi_backend.security.authority;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSortedSet;
import org.springframework.security.core.GrantedAuthority;

public class SimpleRoleModel implements RoleModel {
    private final String roleName;
    private final ImmutableCollection<PermissionModel> rolePermissions;
    private final ImmutableCollection<String> mayBeGivenBy;

    public SimpleRoleModel(String roleName, ImmutableCollection<PermissionModel> rolePermissions, ImmutableCollection<String> mayBeGivenBy) {
        this.roleName = roleName;
        this.rolePermissions = rolePermissions;
        this.mayBeGivenBy = mayBeGivenBy;
    }

    @Override
    public ImmutableCollection<PermissionModel> getPermissionModels() {
        return null;
    }

    @Override
    public ImmutableCollection<GrantedAuthority> getPermissions() {
        return null;
    }

    @Override
    public GrantedAuthority getRole() {
        return null;
    }

    @Override
    public ImmutableCollection<GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getAuthorityName() {
        return null;
    }

    @Override
    public ImmutableCollection<GrantedAuthority> mayBeGivenBy() {
        return null;
    }
}
