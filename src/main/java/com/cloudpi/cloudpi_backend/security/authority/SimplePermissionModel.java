package com.cloudpi.cloudpi_backend.security.authority;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;

public class SimplePermissionModel implements PermissionModel {
    private final String permissionName;
    private final ImmutableCollection<GrantedAuthority> mayBeGivenBy;
    private final ImmutableCollection<String> haveItByDefault;

    public SimplePermissionModel(String permissionName, ImmutableSortedSet<String> mayBeGivenBy, ImmutableCollection<String> haveItByDefault) {
        this.permissionName = permissionName;
        this.mayBeGivenBy = mayBeGivenBy.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(ImmutableSet.toImmutableSet());
        this.haveItByDefault = haveItByDefault;
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
    public ImmutableCollection<String> getAccountsThatHaveItByDefault() {
        return null;
    }

    @Override
    public String getAuthorityName() {
        return null;
    }
}
