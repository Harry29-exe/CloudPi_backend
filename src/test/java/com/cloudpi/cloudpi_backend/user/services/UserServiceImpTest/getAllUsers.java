package com.cloudpi.cloudpi_backend.user.services.UserServiceImpTest;

import com.cloudpi.cloudpi_backend.exepctions.authorization.AuthenticationRequiredException;
import com.cloudpi.cloudpi_backend.exepctions.authorization.NoRequiredPermissionException;
import com.cloudpi.cloudpi_backend.user.UserEntityBuilder;
import com.cloudpi.cloudpi_backend.user.controllers.AccountType;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class getAllUsers extends UserServiceImpTest {

    private List<UserEntity> usersInDB;

    @BeforeEach
    public void setUsersDB() {
        this.removeAuthentication();
        usersInDB = List.of(
                UserEntityBuilder.fastBuilder(1L, "nick").build(),
                UserEntityBuilder.fastBuilder(2L, "Lucy").build(),
                UserEntityBuilder.fastBuilder(3L, "steve").build(),
                UserEntityBuilder.fastBuilder(0L, "ROOT").build()
                );

        when(userRepository.findAll()).thenReturn(usersInDB);
    }

    @Test
    void should_return_all_users2() {
        //given
        var loggedUser = usersInDB.get(0);
        this.setAuthentication(loggedUser.toUserDTO(), AccountType.USER.getAuthorities());

        //when
        var users = userService.getAllUsers();

        //then
        assert users.size() == usersInDB.size();
        for(int i = 0; i < users.size(); i++) {
            assert users.get(i).equals(usersInDB.get(i).toUserDTO());
        }
    }

    @Test
    void should_return_all_users() {
        //given
        var loggedRootUser = usersInDB.get(3);
        this.setAuthentication(loggedRootUser.toUserDTO(), AccountType.USER.getAuthorities());

        //when
        var users = userService.getAllUsers();

        //then
        assert users.size() == usersInDB.size();
        for(int i = 0; i < users.size(); i++) {
            assert users.get(i).equals(usersInDB.get(i).toUserDTO());
        }
    }

    @Test
    void should_throw_AuthenticationException() {
        //given

        //then
        Assertions.assertThrows(AuthenticationRequiredException.class, () -> {
            //when
            var users = userService.getAllUsers();
        });
    }

    @Test
    void should_throw_NoRequiredPermissionException() {
        //given
        var loggedUser = usersInDB.get(0);
        loggedUser.setLocked(true);
        this.setAuthentication(loggedUser.toUserDTO(), AccountType.USER.getAuthorities());

        //then
        Assertions.assertThrows(NoRequiredPermissionException.class, () -> {
            this.userService.getAllUsers();
        });
    }

}
