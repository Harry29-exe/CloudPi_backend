package com.cloudpi.cloudpi_backend.user.services.UserServiceImpTest;

import com.cloudpi.cloudpi_backend.user.UserDTOBuilder;
import com.cloudpi.cloudpi_backend.user.UserEntityBuilder;
import com.cloudpi.cloudpi_backend.user.UserTestUtils;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class getUserByUsername extends UserServiceImpTest {


    @Test
    void should_return_optional_with_null() {
        //given
        var loggedUser = UserDTOBuilder
                .fastBuilder(1L, "Lucy")
                .build();
        this.setAuthentication(loggedUser);
        var username = "steve";
        when(userRepository.findByUsername("steve"))
                .thenReturn(Optional.empty());

        //when
        Optional<UserDTO> userDTO = userService.getUser(username);

        //then
        assert userDTO.isEmpty();
    }

    @Test
    void should_return_UserDTO() {
        //given
        var loggedUser = UserDTOBuilder
                .fastBuilder(1L, "Lucy")
                .build();
        this.setAuthentication(loggedUser);
        var userEntity = UserEntityBuilder.builder().build();
        when(userRepository.findByUsername(userEntity.getUsername()))
                .thenReturn(Optional.of(userEntity));

        //when
        var optionalUserDTO = userService.getUser(userEntity.getUsername());

        //then
        assert optionalUserDTO.isPresent();
        assert optionalUserDTO.get().equals(userEntity.toUserDTO());
    }

    @Test
    void should_throw_AuthenticationRequiredException() {
        //given
        var userEntity = UserEntityBuilder
                .fastBuilder(1L, "Steve").build();
        when(userRepository.findByUsername(userEntity.getUsername()))
                .thenReturn(Optional.of(userEntity));

        //then
        Assertions.assertThrows(ArithmeticException.class, () -> {
            //when
            this.userService.getUser(userEntity.getUsername());
        });
    }
}
