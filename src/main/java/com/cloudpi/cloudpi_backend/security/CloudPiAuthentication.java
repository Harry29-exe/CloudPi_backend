package com.cloudpi.cloudpi_backend.security;

import com.cloudpi.cloudpi_backend.security.permissions.AccountType;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CloudPiAuthentication implements Authentication {
    private final String username;
    private final String password;
    private final Collection<GrantedAuthority> authorities;
    private Boolean isAuthenticated = false;

    public CloudPiAuthentication(CloudPIUser userDetails) {
        this.username = userDetails.getUsername();
        this.password = userDetails.getPassword();
        this.authorities = userDetails.getAuthorities();
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
