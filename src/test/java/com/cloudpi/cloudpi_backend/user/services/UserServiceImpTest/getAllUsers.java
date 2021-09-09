package com.cloudpi.cloudpi_backend.user.services.UserServiceImpTest;

import com.cloudpi.cloudpi_backend.security.CloudPiAuthentication;
import com.cloudpi.cloudpi_backend.security.permissions.AccountType;
import com.cloudpi.cloudpi_backend.user.UserDTOBuilder;
import com.cloudpi.cloudpi_backend.user.UserEntityBuilder;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.mockito.Mockito.when;

public class getAllUsers extends UserServiceImpTest {

    @Test
    void should_return_all_users() {
        //given
        var loggedUser = UserEntityBuilder.fastBuilder(2L, "Lucy")
                .build();
        Authentication auth = new CloudPiAuthentication(loggedUser.toUserDTO().toCloudPiUser());
        auth.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(auth);
        var usersInDB = List.of(
                UserEntityBuilder.fastBuilder(3L, "nick").build(),
                UserEntityBuilder.fastBuilder(0L, "ROOT")
                    .setAccountType(AccountType.ROOT).build(),
                UserEntityBuilder.fastBuilder(4L, "steve").build(),
                loggedUser
        );
        when(userRepository.findAll()).thenReturn(usersInDB);

        //when
        var users = userService.getAllUsers();

        //then
        assert users.size() == usersInDB.size();
        for(int i = 0; i < users.size(); i++) {
            assert users.get(i).equals(usersInDB.get(i).toUserDTO());
        }
    }

}
