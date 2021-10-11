package com.cloudpi.cloudpi_backend.security.authority;

import com.google.common.collect.ImmutableCollection;
import org.springframework.security.core.GrantedAuthority;

public interface RoleModel extends AuthorityModel {

    ImmutableCollection<PermissionModel> getPermissionModels();

    ImmutableCollection<GrantedAuthority> getPermissions();

    GrantedAuthority getRole();

    ImmutableCollection<GrantedAuthority> getAuthorities();

}
