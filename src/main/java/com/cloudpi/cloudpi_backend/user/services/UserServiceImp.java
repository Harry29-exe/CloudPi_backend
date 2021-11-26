package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.authorities.services.AuthorityManagementService;
import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.InvalidUserData;
import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.files.filesystem.services.VirtualDriveService;
import com.cloudpi.cloudpi_backend.security.authority_system.AuthorityModelsAggregator;
import com.cloudpi.cloudpi_backend.user.dto.UpdateUserVal;
import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserDeleteEntity;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.mappers.UserMapper;
import com.cloudpi.cloudpi_backend.user.repositories.UserDetailsRepository;
import com.cloudpi.cloudpi_backend.user.repositories.UserRepository;
import com.cloudpi.cloudpi_backend.utils.EntityReference;
import com.cloudpi.cloudpi_backend.utils.RepoService;
import com.cloudpi.cloudpi_backend.utils.UserValidator;
import com.google.common.collect.ImmutableList;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService, RepoService<UserEntity, Long> {
    private final UserRepository userRepository;
    private final UserDetailsRepository detailsRepository;
    private final AuthorityManagementService authorityService;
    private final PasswordEncoder passwordEncoder;
    private final VirtualDriveService virtualDriveService;
    private final UserValidator userValidator;

    public UserServiceImp(UserRepository userRepository,
                          UserDetailsRepository detailsRepository,
                          AuthorityManagementService authorityService,
                          PasswordEncoder passwordEncoder,
                          VirtualDriveService virtualDriveService,
                          UserValidator userValidator) {
        this.userRepository = userRepository;
        this.detailsRepository = detailsRepository;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
        this.virtualDriveService = virtualDriveService;
        this.userValidator = userValidator;
    }


    @Override
    public ImmutableList<UserPublicIdDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserEntity::toUserPublicIdDTO)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    public List<UserWithDetailsDTO> getAllUsersWithDetails() {
        return userRepository.findAll()
                .stream()
                .map(UserEntity::toUserWithDetailsDTO)
                .toList();

    }

    @Override
    public Optional<UserWithDetailsDTO> getUserDetails(String username) {
        var user = userRepository.findByUsername(username);
        return user.map(UserEntity::toUserWithDetailsDTO);
    }

    @Override
    @Transactional
    public List<AuthorityDTO> createUserWithDefaultAuthorities(UserWithDetailsDTO user, String nonEncodedPassword) {
        if(!userValidator.validateUsername(user.getLogin())) {
            throw new InvalidUserData("Invalid username. Make sure it only consists of letters and digits.");
        } else if(!userValidator.validateNickname(user.getUsername())) {
            throw new InvalidUserData("Invalid nickname. Make sure it only consists of letters, digits and spacebars.");
        }

        var userEntity = new UserEntity(user.getLogin(),
                user.getUsername(),
                passwordEncoder.encode(nonEncodedPassword),
                user.getAccountType(),
                user.getUserDetails().toEntity(),
                null, null);
        userRepository.save(userEntity);
        virtualDriveService.createVirtualDriveAndRootDir(userEntity.getId());

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
    public void updateUser(String username, UpdateUserVal userDetails) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(NoSuchUserException::notFoundByUsername);
        UserMapper.INSTANCE.updateUserEntity(user, userDetails);

        userRepository.save(user);
    }

    @Override
    public void lockUser(UserPublicIdDTO user) {
        var userEntity = userRepository
                .findByUsername(user.getUsername())
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        userEntity.setLocked(true);
        userRepository.save(userEntity);
    }

    @Override
    public void lockUser(String username) {
        var userEntity = userRepository
                .findByUsername(username)
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        userEntity.setLocked(true);
        userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void scheduleUserDeleting(String username) {
        var userToBeDeleted = userRepository
                .findByUsername(username)
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        userToBeDeleted.setUserDeleteSchedule(
                new UserDeleteEntity()
        );

        userRepository.save(userToBeDeleted);
    }

    @Override
    public EntityReference<UserEntity> getReference(Long entityId) {
        return EntityReference.of(userRepository.getById(entityId));
    }
}