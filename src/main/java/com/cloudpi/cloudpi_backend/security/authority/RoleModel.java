package com.cloudpi.cloudpi_backend.security.authority;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public interface RoleModel extends AuthorityModel {

    ImmutableCollection<PermissionModel> getPermissionModels();

    ImmutableCollection<GrantedAuthority> getPermissions();

    GrantedAuthority getRole();

    ImmutableCollection<GrantedAuthority> getAuthorities();

}
