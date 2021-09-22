package com.cloudpi.cloudpi_backend.user.controllers;

import com.cloudpi.cloudpi_backend.security.CloudPiPermission;
import com.cloudpi.cloudpi_backend.security.CloudPiRole;
import com.cloudpi.cloudpi_backend.security.Roles;
import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityPermission;

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

    private final String value;
    private final Collection<? extends CPAuthorityPermission> permissions;
    private final Collection<CloudPiPermission> cloudPiPermissions;

    AccountType(String value, CloudPiPermission... permissions) {
        this.value = value;
        this.cloudPiPermissions = List.of(permissions);
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

    public Collection<CloudPiPermission> getCloudPiPermissions() {
        return cloudPiPermissions;
    }

    //    @Override
//    public CPAuthorityRole getRoleAuthority() {
//        return new CPAuthorityRole(value);
//    }
}
