package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.authorities.services.AuthorityManagementService;
import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.security.authority_system.AuthorityModelsAggregator;
import com.cloudpi.cloudpi_backend.user.dto.UserDetailsDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserDeleteEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import com.cloudpi.cloudpi_backend.user.repositories.UserDetailsRepository;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.google.common.collect.ImmutableList;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cloudpi.cloudpi_backend.user.enpoints.UserAPIAuthorities.GET_DETAILS;

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
    public Optional<UserWithDetailsDTO> getUserDetails(String username) {
        return repository.findByUsername(username)
                .map(UserEntity::toUserWithDetailsDTO);
    }

    @Override
    public List<AuthorityDTO> createUserWithDefaultAuthorities(UserWithDetailsDTO user, String nonEncodedPassword) {
        var userEntity = user.toUserEntity();
        userEntity.setPassword(passwordEncoder.encode(nonEncodedPassword));
        userEntity.getUserDetails().setUser(userEntity);
        repository.save(userEntity);

        var defaultRoles = AuthorityModelsAggregator.getDefaultAuthorities(user.getAccountType().name());
        List<AuthorityDTO> authoritiesThatCouldNotBeGiven = new ArrayList<>();
        for (var authority : defaultRoles) {
            try {
                authorityService.giveUserAuthority(user.getUsername(), authority);
            } catch (AccessDeniedException ex) {
                authoritiesThatCouldNotBeGiven.add(authority);
            }
        }

        return authoritiesThatCouldNotBeGiven;
    }

    @Override
    public void updateUserDetails(String username, UserDetailsDTO userDetails) {
        var user = repository.findByUsername(username)
                .orElseThrow(NoSuchUserException::notFoundByUsername);
        UserMapper.INSTANCE.updateUserEntity(user.getUserDetails(), userDetails);

        repository.save(user);
    }

    @Override
    public void lockUser(UserPublicIdDTO user) {
        var userEntity = repository
                .findByUsername(user.getUsername())
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        userEntity.setLocked(true);
        repository.save(userEntity);
    }

    @Override
    public void lockUser(String username) {
        var userEntity = repository
                .findByUsername(username)
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        userEntity.setLocked(true);
        repository.save(userEntity);
    }

    @Override
    public void deleteUser(String username) {
        repository.deleteByUsername(username);
    }

    @Override
    public void scheduleUserDeleting(String username) {
        var userToBeDeleted = repository
                .findByUsername(username)
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        userToBeDeleted.setUserDeleteSchedule(
                new UserDeleteEntity()
        );

        repository.save(userToBeDeleted);
    }
}