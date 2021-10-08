package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.authorization.entities.RoleEntity;
import com.cloudpi.cloudpi_backend.security.authority.AuthorityModelsAggregator;
import com.cloudpi.cloudpi_backend.security.dto.CloudPIUser;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.google.common.collect.ImmutableList;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class CPUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CPUserDetailsService(UserRepository userRepository) {
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
        Set<GrantedAuthority> authorities = new HashSet<>();

        AuthorityModelsAggregator
                .getRolesByRoleEntities(userEntity.getRoles())
                .forEach(roleModel ->
                        authorities.addAll(roleModel.getAuthorities()));

        AuthorityModelsAggregator
                .getPermissionsByPermissionEntities(userEntity.getPermissions())
                .forEach(permissionModel ->
                        authorities.add(permissionModel.getAuthority()));

        authorities.addAll(
                AuthorityModelsAggregator
                    .getRoleModelByRoleName(userEntity.getAccountType().getRoleName())
                    .getAuthorities()
        );

        return authorities;
    }
}