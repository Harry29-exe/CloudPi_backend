package com.cloudpi.cloudpi_backend.security.dto;

import com.cloudpi.cloudpi_backend.user.controllers.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CloudPiAuthentication implements Authentication {
    private final String username;
    private final String password;
    private final ImmutableCollection<? extends GrantedAuthority> authorities;
    private Boolean isAuthenticated = false;

    public CloudPiAuthentication(String username,
                                 String password,
                                 Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        if(authorities instanceof ImmutableCollection) {
            this.authorities = (ImmutableCollection<? extends GrantedAuthority>) authorities;
        } else {
            this.authorities = ImmutableSet.copyOf(authorities);
        }
    }

    public CloudPiAuthentication(UserDTO user, Collection<? extends GrantedAuthority> authorities) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        if(authorities instanceof ImmutableCollection) {
            this.authorities = (ImmutableCollection<? extends GrantedAuthority>) authorities;
        } else {
            this.authorities = ImmutableSet.copyOf(authorities);
        }
    }

    public CloudPiAuthentication(CloudPIUser user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        if(user.getAuthorities() instanceof ImmutableCollection) {
            this.authorities = (ImmutableCollection<? extends GrantedAuthority>) user.getAuthorities();
        } else {
            this.authorities = ImmutableSet.copyOf(user.getAuthorities());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return username;
    }

    @Data
    @AllArgsConstructor
    private static class CloudPiAuthenticationDetails {
        private AccountType userType;
    }
}
