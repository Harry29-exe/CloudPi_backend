package com.cloudpi.cloudpi_backend.user.services.UserServiceImpTest;

import com.cloudpi.cloudpi_backend.security.CloudPiAuthentication;
import com.cloudpi.cloudpi_backend.security.permissions.AccountType;
import com.cloudpi.cloudpi_backend.user.UserEntityBuilder;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.when;

public class updateUserDetails extends UserServiceImpTest {

    @Test
    void should_modify_userEntity() {
        //given
        AtomicReference<UserEntity> userEntity = new AtomicReference<>(
                UserEntityBuilder.fastBuilder(1L, "Natale").build());
        var modifiedUser = userEntity.get().toUserDTO();
        modifiedUser.setEmail("Natale@new.com");

        when(userRepository.findByUsername("Natale"))
                .thenReturn(Optional.of(userEntity.get()));
        when(userRepository.save(any(UserEntity.class)))
                .then( invocation -> {
                    UserEntity inEntity = invocation.getArgument(0);
                    userEntity.set(inEntity);
                    return inEntity;
                });

        var auth = new CloudPiAuthentication(userEntity.get().toUserDTO().toCloudPiUser());
        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);

        //when
        userService.updateUserDetails(modifiedUser);

        //then
        assert userEntity.get().equals(modifiedUser.toUserEntity());
    }

    @Test
    void should_throw_IllegalArgumentException() {
        //given
        AtomicReference<UserEntity> rootEntity = new AtomicReference<>(UserEntityBuilder
                .fastBuilder(0L, "ROOT")
                .setAccountType(AccountType.ROOT).build());
        var modifiedRootEntity = rootEntity.get().toUserDTO();
        modifiedRootEntity.setUsername("we");

        when(userRepository.findByUsername("ROOt"))
                .thenReturn(Optional.of(rootEntity.get()));
        when(userRepository.save(any(UserEntity.class)))
                .then(invocation -> {
                    rootEntity.set(invocation.getArgument(0));
                    return invocation.getArgument(0);
                });

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //when
            userService.updateUserDetails(modifiedRootEntity);
        });
    }
}
