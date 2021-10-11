package com.cloudpi.cloudpi_backend.user.dto;

import com.google.common.collect.ImmutableCollection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CPUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final Boolean locked;
    private final ImmutableCollection<GrantedAuthority> permissions;

    public CPUserDetails(String username, String password, Boolean locked, ImmutableCollection<GrantedAuthority> permissions) {
        this.username = username;
        this.password = password;
        this.locked = locked;
        this.permissions = permissions;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return permissions;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}