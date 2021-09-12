package com.cloudpi.cloudpi_backend.user.services.UserServiceImpTest;

import com.cloudpi.cloudpi_backend.authorization.dto.AccountType;
import com.cloudpi.cloudpi_backend.user.UserEntityBuilder;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class updateUserDetails extends UserServiceImpTest {
    protected AtomicReference<UserEntity> userEntityInDB;

    @BeforeEach
    void setUpRepository() {
        when(userRepository.findByUsername(any(String.class)))
                .thenReturn(Optional.of(userEntityInDB.get()));
        when(userRepository.save(any(UserEntity.class)))
                .then( invocation -> {
                    UserEntity inEntity = invocation.getArgument(0);
                    userEntityInDB.set(inEntity);
                    return inEntity;
                });
    }

    @Test
    void should_modify_userEntity() {
        //given
        userEntityInDB = new AtomicReference<>(UserEntityBuilder
                        .fastBuilder(1L, "Natale").build());
        var modifiedUser = userEntityInDB.get().toUserDTO();
        modifiedUser.setEmail("Natale@new.com");

        //when
        userService.updateUserDetails(modifiedUser);

        //then
        assert userEntityInDB.get().equals(modifiedUser.toUserEntity());
    }

    @Test
    void should_throw_IllegalArgumentException() {
        //given
        userEntityInDB = new AtomicReference<>(UserEntityBuilder
                .fastBuilder(0L, "ROOT")
                .setAccountType(AccountType.ROOT).build());

        var modifiedRootEntity = userEntityInDB.get().toUserDTO();
        modifiedRootEntity.setUsername("we");

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            //when
            userService.updateUserDetails(modifiedRootEntity);
        });
    }
}
