package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository repository;
    private final UserMapper userMapper;

    public UserServiceImp(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }


    @Override
    public ImmutableList<UserPublicIdDTO> getAllUsers() {
        return repository.findAll().stream()
                .map(UserEntity::toUserPublicIdDTO)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public Optional<UserWithDetailsDTO> getUserDetails(String nickname) {
        return repository.findByNickname(nickname)
                .map(UserEntity::toUserWithDetailsDTO);
    }

    @Override
    public void updateUserDetails(UserWithDetailsDTO userWithDetailsDTO) {

    }

    @Override
    public void lockUser(UserWithDetailsDTO userWithDetailsDTO) {

    }

    @Override
    public void lockUser(Long userId) {

    }

    @Override
    public void removeUser(Long userId) {

    }

    @Override
    public void schedule_remove_user(Long userId) {

    }
}