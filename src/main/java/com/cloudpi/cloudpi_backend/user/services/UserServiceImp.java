package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.authorities.pojo.AuthorityType;
import com.cloudpi.cloudpi_backend.authorities.services.AuthorityManagementService;
import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.security.authority_system.AuthorityModelsAggregator;
import com.cloudpi.cloudpi_backend.security.authority_system.PermissionModel;
import com.cloudpi.cloudpi_backend.security.authority_system.RoleModel;
import com.cloudpi.cloudpi_backend.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserDeleteEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserDetailsEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import com.cloudpi.cloudpi_backend.user.repositories.UserDetailsRepository;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.cloudpi.cloudpi_backend.user.requests.PostUserRequest;
import com.google.common.collect.ImmutableList;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository repository;
    private final UserDetailsRepository detailsRepository;
    private final AuthorityManagementService authorityService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository repository, UserDetailsRepository detailsRepository,
                          AuthorityManagementService authorityService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.detailsRepository = detailsRepository;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
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
    public List<AuthorityDTO> createUserWithDefaultAuthorities(PostUserRequest user) {
        var userEntity = new UserEntity(
                null, user.getUsername(), passwordEncoder.encode(user.getPassword()),
                false, user.getAccountType(), new UserDetailsEntity(user.getNickname(), user.getEmail(), null),
                null, null, null, null, null
        );
        userEntity.getUserDetails().setUser(userEntity);
        repository.save(userEntity);

        var defaultRoles = AuthorityModelsAggregator.getDefaultAuthorities(user.getAccountType());
        List<AuthorityDTO> authoritiesThatCouldNotBeGiven = new ArrayList<>();
        for (var authority : defaultRoles) {
            try {
                authorityService.giveUserAuthority(user.getNickname(), authority);
            } catch (AccessDeniedException ex) {
                authoritiesThatCouldNotBeGiven.add(authority);
            }
        }

        return authoritiesThatCouldNotBeGiven;
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
    public void deleteUser(String nickname) {
        repository.deleteByUserDetails_Nickname(nickname);
    }

    @Override
    public void scheduleUserDeleting(String nickname) {
        var userToBeDeleted = repository
                .findByUserDetails_Nickname(nickname)
                .orElseThrow(NoSuchUserException::notFoundByNickname);

        userToBeDeleted.setUserDeleteSchedule(
                new UserDeleteEntity()
        );

        repository.save(userToBeDeleted);
    }
}