package com.cloudpi.cloudpi_backend.user.services.UserServiceImpTest;

import com.cloudpi.cloudpi_backend.user.controllers.AccountType;
import com.cloudpi.cloudpi_backend.user.UserEntityBuilder;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class updateUserDetails extends UserServiceImpTest {
    protected AtomicReference<UserEntity> userEntityInDB = new AtomicReference<>();

    @Configuration
    @ComponentScan("com.cloudpi.cloudpi_backend.*")
    public static class SpringConfig {

    }

    @BeforeEach
    void setUpRepository() {
        when(userRepository.findByUsername(any(String.class)))
                .then((a) -> Optional.of(userEntityInDB.get()));
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
        userEntityInDB.set(UserEntityBuilder
                        .fastBuilder(1L, "Natale").build());
        var modifiedUser = userEntityInDB.get().toUserDTO();
        modifiedUser.setEmail("Natale@new.com");
        setAuthentication(userEntityInDB.get().toUserDTO(), AccountType.USER.getAuthorities());

        //when
        userService.updateUserDetails(modifiedUser);

        //then
        assert userEntityInDB.get().equals(modifiedUser.toUserEntity());
    }

    @Test
    void should_throw_IllegalArgumentException() {
        //given
        userEntityInDB.set(UserEntityBuilder
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

    @Test
    @WithMockUser
    void should_throw_AuthorizationException() {
        //given
        userEntityInDB.set(UserEntityBuilder
                .fastBuilder(1L, "user1")
                .setAccountType(AccountType.USER).build());

        var modifiedRootEntity = userEntityInDB.get().toUserDTO();
        modifiedRootEntity.setUsername("we");

        Assertions.assertThrows(Exception.class, () -> {
            //when
            userService.updateUserDetails(modifiedRootEntity);
        });
        assert false;
    }
}
