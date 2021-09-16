package com.cloudpi.cloudpi_backend.authorization.dto;

import com.cloudpi.cloudpi_backend.authorization.pojo.CPAuthorityFactory;

import java.util.ArrayList;
import java.util.Collection;

public interface CloudPiRole {

    Collection<? extends CPAuthorityPermission> getPermissions();

    String name();

    default CPAuthorityRole getRole() {
        return new CPAuthorityRole(this.name());
    }

    default Collection<CPAuthority> getAuthorities() {
        return CPAuthorityFactory.getRoleAuthorities(this.name());
    }
}
