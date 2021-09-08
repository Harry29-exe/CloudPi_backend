package com.cloudpi.cloudpi_backend.user.services.UserServiceImpTest;

import com.cloudpi.cloudpi_backend.user.UserTestUtils;
import com.cloudpi.cloudpi_backend.user.dto.UserDTO;
import com.cloudpi.cloudpi_backend.user.entities.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class getUserByUsername extends UserServiceImpTest {

    @Test
    void should_return_optional_with_null() {
        //given
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
        var userEntity = UserTestUtils.createDefaultUserEntity();
        when(userRepository.findByUsername(userEntity.getUsername()))
                .thenReturn(Optional.of(userEntity));

        //when
        var optionalUserDTO = userService.getUser(userEntity.getUsername());

        //then
        assert optionalUserDTO.isPresent();
        assert optionalUserDTO.get().equals(userEntity.toUserDTO());
    }
}
