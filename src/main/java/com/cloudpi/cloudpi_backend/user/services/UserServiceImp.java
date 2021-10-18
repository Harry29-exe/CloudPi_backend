package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserDeleteEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import com.cloudpi.cloudpi_backend.user.repositories.UserDetailsRepository;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository repository;
    private final UserDetailsRepository detailsRepository;

    public UserServiceImp(UserRepository repository, UserDetailsRepository detailsRepository) {
        this.repository = repository;
        this.detailsRepository = detailsRepository;
    }


    @Override
    public ImmutableList<UserPublicIdDTO> getAllUsers() {
        return repository.findAll().stream()
                .map(UserEntity::toUserPublicIdDTO)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<UserWithDetailsDTO> getAllUsersWithDetails() {
        return repository.findAll()
                .stream()
                .map(UserEntity::toUserWithDetailsDTO)
                .toList();

    }

    @Override
    public Optional<UserWithDetailsDTO> getUserDetails(String nickname) {
        return repository.findByUserDetails_Nickname(nickname)
                .map(UserEntity::toUserWithDetailsDTO);
    }

    @Override
    public void createUser(UserWithDetailsDTO user) {
        var userEntity = user.toUserEntity();
        userEntity.getUserDetails().setUser(userEntity);

        repository.save(userEntity);
    }

    @Override
    public void updateUserDetails(String nickname, UserDetailsDTO userDetails) {
        var user = repository.findByUserDetails_Nickname(nickname)
                .orElseThrow(NoSuchUserException::notFoundByNickname);
        UserMapper.INSTANCE.updateUserEntity(user.getUserDetails(), userDetails);

        repository.save(user);
    }

    @Override
    public void lockUser(UserPublicIdDTO user) {
        var userEntity = repository
                .findByUserDetails_Nickname(user.getNickname())
                .orElseThrow(NoSuchUserException::notFoundByNickname);

        userEntity.setLocked(true);
        repository.save(userEntity);
    }

    @Override
    public void lockUser(String nickname) {
        var userEntity = repository
                .findByUserDetails_Nickname(nickname)
                .orElseThrow(NoSuchUserException::notFoundByNickname);

        userEntity.setLocked(true);
        repository.save(userEntity);
    }

    @Override
    public void removeUser(String nickname) {
        repository.deleteByUserDetails_Nickname(nickname);
    }

    @Override
    public void schedule_remove_user(String nickname) {
        var userToBeDeleted = repository
                .findByUserDetails_Nickname(nickname)
                .orElseThrow(NoSuchUserException::notFoundByNickname);

        userToBeDeleted.setUserDeleteSchedule(
                new UserDeleteEntity()
        );

        repository.save(userToBeDeleted);
    }
}