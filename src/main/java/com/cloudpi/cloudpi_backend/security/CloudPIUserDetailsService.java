package com.cloudpi.cloudpi_backend.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CloudPIUserDetailsService implements UserDetailsService {

    @Override
    public CloudPIUser loadUserByUsername(String username) {
        throw new UsernameNotFoundException("");
    }
}