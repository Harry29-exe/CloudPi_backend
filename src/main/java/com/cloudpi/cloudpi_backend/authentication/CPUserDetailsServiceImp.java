package com.cloudpi.cloudpi_backend.authentication;

import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.security.authority_system.AuthorityModelsAggregator;
import com.cloudpi.cloudpi_backend.user.domain.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.domain.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class CPUserDetailsServiceImp implements CPUserDetailsService {
    private final UserRepository userRepository;

    public CPUserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User loadUserByUsername(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(NoSuchUserException::notFoundByLogin);
        var userAuthorities = this.getUsersAuthorities(user);

        return new User(user.getUsername(), user.getPassword(), true, true,
                true, !user.getLocked(), userAuthorities);
    }

    @Override
    public String findUsernameByLogin(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(NoSuchUserException::notFoundByLogin)
                .getUsername();
    }

    private Set<GrantedAuthority> getUsersAuthorities(UserEntity userEntity) {
        Set<GrantedAuthority> authorities = new HashSet<>();

        AuthorityModelsAggregator
                .getRolesByRoleEntities(userEntity.getRoles())
                .forEach(roleModel ->
                        authorities.addAll(roleModel.getAuthorities()));

        AuthorityModelsAggregator
                .getPermissionsByPermissionEntities(userEntity.getPermissions())
                .forEach(permissionModel ->
                        authorities.add(permissionModel.getAuthority()));

        return authorities;
    }
}