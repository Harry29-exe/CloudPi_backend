package com.cloudpi.cloudpi_backend.security;

import com.cloudpi.cloudpi_backend.security.authority.CPAuthority;
import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityPermission;
import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public interface CloudPiRole {

    Collection<CloudPiPermission> getCloudPiPermissions();

    Collection<? extends CPAuthorityPermission> getPermissions();

    String name();

    default CPAuthorityRole getRole() {
        return new CPAuthorityRole(this.name());
    }

    default Collection<CPAuthority> getAuthorities() {
        Collection<CPAuthority> authorities = new ArrayList<>(this.getPermissions());
        authorities.add(this.getRole());
        return authorities;
    }

    static Collection<CPAuthorityPermission> authorityListOf(CloudPiPermission... permissions) {
        return Arrays.stream(permissions)
                .map(CloudPiPermission::getPermission)
                .collect(Collectors.toList());
    }
}
