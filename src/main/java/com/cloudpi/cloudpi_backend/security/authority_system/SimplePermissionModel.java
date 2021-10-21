package com.cloudpi.cloudpi_backend.security.authority_system;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SimplePermissionModel implements PermissionModel {
    private final String permissionName;
    private final GrantedAuthority grantedAuthority;
    private final ImmutableCollection<GrantedAuthority> mayBeGivenBy;
    private final ImmutableCollection<String> haveItByDefault;

    public SimplePermissionModel(String permissionName, ImmutableSortedSet<String> mayBeGivenBy, ImmutableCollection<String> haveItByDefault) {
        this.permissionName = permissionName;
        this.mayBeGivenBy = mayBeGivenBy.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(ImmutableSet.toImmutableSet());
        this.haveItByDefault = haveItByDefault;
        this.grantedAuthority = new SimpleGrantedAuthority(permissionName);
    }

    @Override
    public GrantedAuthority getAuthority() {
        return this.grantedAuthority;
    }

    @Override
    public ImmutableCollection<GrantedAuthority> mayBeGivenBy() {
        return mayBeGivenBy;
    }

    @Override
    public ImmutableCollection<String> getAccountsThatHaveItByDefault() {
        return haveItByDefault;
    }

    @Override
    public String getAuthorityName() {
        return permissionName;
    }

    @Override
    public int compareTo(PermissionModel o) {
        return this.permissionName.compareTo(o.getAuthorityName());
    }
}
