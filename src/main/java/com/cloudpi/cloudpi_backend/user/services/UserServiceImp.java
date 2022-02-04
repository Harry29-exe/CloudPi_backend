package com.cloudpi.cloudpi_backend.user.services;

import com.cloudpi.cloudpi_backend.authorities.dto.AuthorityDTO;
import com.cloudpi.cloudpi_backend.authorities.services.AuthorityManagementService;
import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.InvalidUserData;
import com.cloudpi.cloudpi_backend.exepctions.user.endpoint.NoSuchUserException;
import com.cloudpi.cloudpi_backend.files.filesystem.services.VFilesystemService;
import com.cloudpi.cloudpi_backend.security.authority_system.AuthorityModelsAggregator;
import com.cloudpi.cloudpi_backend.user.domain.entities.UserDetailsEntity;
import com.cloudpi.cloudpi_backend.user.domain.entities.UserEntity;
import com.cloudpi.cloudpi_backend.user.domain.repositories.UserDetailsRepository;
import com.cloudpi.cloudpi_backend.user.domain.repositories.UserRepository;
import com.cloudpi.cloudpi_backend.user.dto.UserPublicIdDTO;
import com.cloudpi.cloudpi_backend.user.dto.UserWithDetailsDTO;
import com.cloudpi.cloudpi_backend.user.services.dto.CreateUser;
import com.cloudpi.cloudpi_backend.user.services.dto.UpdateUser;
import com.cloudpi.cloudpi_backend.utils.EntityReference;
import com.cloudpi.cloudpi_backend.utils.RepoService;
import com.cloudpi.cloudpi_backend.utils.UserValidator;
import com.google.common.collect.ImmutableList;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService, RepoService<UserEntity, Long> {
    private final UserRepository userRepository;
    private final UserDetailsRepository detailsRepository;
    private final AuthorityManagementService authorityService;
    private final PasswordEncoder passwordEncoder;
    private final VFilesystemService VFilesystemService;
    private final UserValidator userValidator;

    public UserServiceImp(UserRepository userRepository,
                          UserDetailsRepository detailsRepository,
                          AuthorityManagementService authorityService,
                          PasswordEncoder passwordEncoder,
                          VFilesystemService VFilesystemService,
                          UserValidator userValidator) {
        this.userRepository = userRepository;
        this.detailsRepository = detailsRepository;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
        this.VFilesystemService = VFilesystemService;
        this.userValidator = userValidator;
    }


    @Override
    public ImmutableList<UserPublicIdDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserEntity::toUserPublicIdDTO)
                .collect(ImmutableList.toImmutableList());
    }

    @Override
    @Transactional
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
    public List<AuthorityDTO> createUserWithDefaultAuthorities(CreateUser user) {
        if (!userValidator.validateUsername(user.getUsername())) {
            throw new InvalidUserData("Invalid username. Make sure it only consists of letters and digits.");
        } else if (!userValidator.validateNickname(user.getNickname())) {
            throw new InvalidUserData("Invalid nickname. Make sure it only consists of letters, digits and spacebars.");
        } else if (!userValidator.validatePassword(user.getNonEncodedPassword())) {
            throw new InvalidUserData("Invalid password. Make sure it only consists of letters, digits and some of special characters");
        }

        var userEntity = new UserEntity(
                user.getUsername(),
                passwordEncoder.encode(user.getNonEncodedPassword()),
                new UserDetailsEntity(user.getNickname(),
                        user.getAccountType(),
                        user.getEmail(),
                        null),
                null, null);
        userRepository.save(userEntity);
        VFilesystemService.createVirtualFilesystem(userEntity.getId());

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
    public void updateUser(String username, UpdateUser userDetails) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        if (userDetails.getEmail() != null) {
            user.getUserDetails().setEmail(userDetails.getEmail());
        }
        if (userDetails.getPathToProfilePicture() != null) {
            user.getUserDetails().setPathToProfilePicture(
                    userDetails.getPathToProfilePicture());
        }
        if (userDetails.getUsername() != null) {
            user.getUserDetails().setNickname(userDetails.getUsername());
        }
        if (userDetails.getAccountType() != null) {
            user.getUserDetails().setAccountType(userDetails.getAccountType());
        }

        userRepository.save(user);
    }

    @Override
    public void lockUser(String username) {
        var userEntity = userRepository
                .findByUsername(username)
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        userEntity.setLocked(true);
        userRepository.save(userEntity);
    }

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void deleteUser(String username) {
        var entity = userRepository.findByUsername(username)
                .orElseThrow(NoSuchUserException::notFoundByUsername);

        userRepository.delete(entity);
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