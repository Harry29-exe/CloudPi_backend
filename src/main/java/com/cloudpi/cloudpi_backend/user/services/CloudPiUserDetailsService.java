package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.authorization.entities.AuthorityPermissionEntity;
import com.cloudpi.cloudpi_backend.authorization.entities.AuthorityRoleEntity;
import com.cloudpi.cloudpi_backend.security.dto.CloudPIUser;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.google.common.collect.ImmutableList;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CloudPiUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CloudPiUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CloudPIUser loadUserByUsername(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user with such username"));
        var userAuthorities = this.getUsersAuthorities(user);

        return new CloudPIUser(user.getUsername(), user.getPassword(), user.getLocked(), ImmutableList.copyOf(userAuthorities));
    }

    public Set<GrantedAuthority> getUsersAuthorities(UserEntity userEntity) {
        //TODO
        return null;
    }
}