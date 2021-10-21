package com.cloudpi.cloudpi_backend.security.authority_system;

import com.google.common.collect.ImmutableCollection;
import org.springframework.security.core.GrantedAuthority;

public interface RoleModel extends AuthorityModel, Comparable<RoleModel> {

    ImmutableCollection<PermissionModel> getPermissionModels();

    ImmutableCollection<GrantedAuthority> getRolesPermissions();

    GrantedAuthority getRole();

    ImmutableCollection<GrantedAuthority> getAuthorities();

}
