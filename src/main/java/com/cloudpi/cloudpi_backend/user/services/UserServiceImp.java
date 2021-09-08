package com.cloudpi.cloudpi_backend.user.services;
;
import com.cloudpi.cloudpi_backend.security.permissions.CloudPiPermission;
import com.cloudpi.cloudpi_backend.security.permissions.CloudPiRole;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.google.common.collect.ImmutableList;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    private UserRepository repository;

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
    public void giveUserPermission(UserDTO userDTO, CloudPiPermission permission) {

    }

    @Override
    public void giveUserPermission(Long userId, CloudPiPermission permission) {

    }

    @Override
    public void removeUserPermission(UserDTO userDTO, CloudPiPermission permission) {

    }

    @Override
    public void removeUserPermission(Long userID, CloudPiPermission permission) {

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