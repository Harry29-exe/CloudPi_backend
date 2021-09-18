package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.authorization.dto.CloudPiPermission;
import com.cloudpi.cloudpi_backend.authorization.dto.CloudPiRole;
import com.cloudpi.cloudpi_backend.configuration.authorization.Roles;
import com.cloudpi.cloudpi_backend.authorization.dto.CPAuthorityPermission;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Roles
public enum AccountType implements CloudPiRole {
    USER("ROLE_USER",
            UserAPIAuthorities.DELETE,
            UserAPIAuthorities.DELETE_SELF,
            UserAPIAuthorities.GET),
    ROOT("ROLE_ROOT",
            UserAPIAuthorities.CREATE,
            UserAPIAuthorities.DELETE,
            UserAPIAuthorities.LOCK,
            UserAPIAuthorities.GET
            ),
    WORKER("ROLE_WORKER");

    public final String value;
    public final Collection<? extends CPAuthorityPermission> permissions;

    AccountType(String value, CloudPiPermission... permissions) {
        this.value = value;
        this.permissions = CloudPiRole.authorityListOf(permissions);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Collection<? extends CPAuthorityPermission> getPermissions() {
        List<CPAuthorityPermission> permissions = new ArrayList<>(this.permissions);
        permissions.add(new CPAuthorityPermission(this.value));
        return permissions;
    }


//    @Override
//    public CPAuthorityRole getRoleAuthority() {
//        return new CPAuthorityRole(value);
//    }
}
