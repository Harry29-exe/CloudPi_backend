package com.cloudpi.cloudpi_backend.authorization.dto;

import com.cloudpi.cloudpi_backend.authorization.pojo.CPAuthorityFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public interface CloudPiRole {

    Collection<? extends CPAuthorityPermission> getPermissions();

    String name();

    default CPAuthorityRole getRole() {
        return new CPAuthorityRole(this.name());
    }

    default Collection<CPAuthority> getAuthorities() {
        return CPAuthorityFactory.getRoleAuthorities(this.name());
    }

    static Collection<CPAuthorityPermission> authorityListOf(CloudPiPermission... permissions) {
        return Arrays.stream(permissions)
                .map(CloudPiPermission::getPermission)
                .collect(Collectors.toList());
    }
}
