package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.security.authority.CPAuthorityPermission;
import com.cloudpi.cloudpi_backend.authorization.dto.CloudPiRole;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository repository;

    public UserServiceImp(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public ImmutableList<UserDTO> getAllUsers() {
        return repository.findAll().stream()
                .map(UserEntity::toUserDTO)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Optional<UserDTO> getUser(String username) {
        var entity = repository.findByUsername(username);
        return entity.map(UserEntity::toUserDTO);
    }

    @Override
    public void updateUserDetails(UserDTO userDTO) {

    }

    @Override
    public void giveUserRole(UserDTO userDTO, CloudPiRole role) {

    }

    @Override
    public void giveUserRole(Long userId, CloudPiRole role) {

    }

    @Override
    public void removeUserRole(UserDTO userDTO, CloudPiRole role) {

    }

    @Override
    public void removeUserRole(Long userID, CloudPiRole role) {

    }

    @Override
    public void giveUserPermission(UserDTO userDTO, CPAuthorityPermission permission) {

    }

    @Override
    public void giveUserPermission(Long userId, CPAuthorityPermission permission) {

    }

    @Override
    public void removeUserPermission(UserDTO userDTO, CPAuthorityPermission permission) {

    }

    @Override
    public void removeUserPermission(Long userID, CPAuthorityPermission permission) {

    }

    @Override
    public void lockUser(UserDTO userDTO) {

    }

    @Override
    public void lockUser(Long userId) {

    }

    @Override
    public void removeUser(UserDTO user) {

    }

    @Override
    public void removeUser(Long userId) {

    }
}