package com.cloudpi.cloudpi_backend.security.authority;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

public class SimplePermissionModel implements PermissionModel {
    private final String permissionName;
    private final ImmutableCollection<GrantedAuthority> mayBeGivenBy;

    public SimplePermissionModel(String permissionName, Collection<String> mayBeGivenBy) {
        this.permissionName = permissionName;
        this.mayBeGivenBy = mayBeGivenBy.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(ImmutableSet.toImmutableSet());
    }

    public SimplePermissionModel(String permissionName, String... mayBeGivenBy) {
        this.permissionName = permissionName;
        this.mayBeGivenBy = Arrays.stream(mayBeGivenBy)
                .map(SimpleGrantedAuthority::new)
                .collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public GrantedAuthority getAuthority() {
        return null;
    }

    @Override
    public ImmutableCollection<GrantedAuthority> mayBeGivenBy() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
